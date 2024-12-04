pipeline {
    agent { label 'App' }

    environment {
        APP_ARCHIVE_NAME = 'app' 
        APP_MODULE_NAME = 'android-template' // NEEDS TO CHANGE
        CHANGELOG_CMD = 'git log --date=format:"%Y-%m-%d" --pretty="format: * %s% b (%an, %cd)" | head -n 10 > commit-changelog.txt'
        DISCORD_WEBHOOK_URL = "https://discord.com/api/webhooks/1301160382307766292/kROxjtgZ-XVOibckTMri2fy5-nNOEjzjPLbT9jEpr_R0UH9JG0ZXb2XzUsYGE0d3yk6I" // NEEDS TO BE CHANGED
        JENKINS_CREDENTIALS_ID = "jenkins-master-key"
        SSH_KEY_FILE = '/var/lib/jenkins/.ssh/id_rsa'
        TEST_RESULT_PATH = 'app/src/TestResults/'
        TRX_FILE_PATH = 'app/src/TestResults/'
        TRX_TO_XML_PATH = 'app/src/TestResults/'
        JENKINS_SERVER = 'http://139.162.132.174:8080/'
    }

    options {
        disableConcurrentBuilds() 
    }

    stages {
        
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout Code') {
            steps {
                script {
                    git credentialsId: 'jenkins-master-key', url: 'git@github.com:Brahim-Mahfoudhi/android.git', branch:'main'
                    echo 'Gather GitHub info!'
                    def gitInfo = sh(script: 'git show -s HEAD --pretty=format:"%an%n%ae%n%s%n%H%n%h" 2>/dev/null', returnStdout: true).trim().split("\n")
                    env.GIT_AUTHOR_NAME = gitInfo[0]
                    env.GIT_AUTHOR_EMAIL = gitInfo[1]
                    env.GIT_COMMIT_MESSAGE = gitInfo[2]
                    env.GIT_COMMIT = gitInfo[3]
                    env.GIT_BRANCH = gitInfo[4]
                }
            }
        }
        
        stage("Build Application") {
            stages {
                stage("Generate License Report") {
                    steps {
                        sh '/opt/gradle/bin/gradle createLicenseReport'
                    }
                    post {
                        always {
                            archiveArtifacts "**/build/licenses/*.html"
                        }
                    }
                }
                stage("Build and Bundle") {
                    steps {
                        sh './gradlew clean assembleRelease bundleRelease'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/bundle/release/${APP_ARCHIVE_NAME}-release.aab"
                        }
                    }
                }
                stage("Run Tests") {
                    steps {
                        sh './gradlew testReleaseUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint Check") {
                    steps {
                        sh './gradlew lintRelease'
                    }
                    post {
                        always {
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Static Analysis with Detekt") {
                    steps {
                        sh './gradlew downloadDetektConfig detektRelease'
                    }
                    post {
                        always {
                            archiveArtifacts '*/build/reports/detekt/*.html'
                        }
                    }
                }
            }
        }

        stage("Publish to Play Store") {
            steps {
                sh './gradlew publishReleaseBundle --artifact-dir app/build/outputs/bundle/release'
            }
        }
    }

    post {
        success {
            echo 'Build and deployment completed successfully!'
            archiveArtifacts artifacts: '**/*.dll', fingerprint: true
            archiveArtifacts artifacts: "${TRX_FILE_PATH}", fingerprint: true
            script {
                sendDiscordNotification("Build Success")
            }
        }
        failure {
            echo 'Build or deployment has failed.'
            script {
                sendDiscordNotification("Build Failed")
            }
        }
        always {
            echo 'Build process has completed.'
            echo 'Generate Test report...'
            sh "/home/jenkins/.dotnet/tools/trx2junit --output ${TEST_RESULT_PATH} ${TRX_FILE_PATH}"
            junit "${TRX_TO_XML_PATH}"
        }
    }
}

def sendDiscordNotification(status) {
    script {
        discordSend(
            title: "${env.JOB_NAME} - ${status}",
            description: """
                Build #${env.BUILD_NUMBER} ${status == "Build Success" ? 'completed successfully!' : 'has failed!'}
                **Commit**: ${env.GIT_COMMIT}
                **Author**: ${env.GIT_AUTHOR_NAME} <${env.GIT_AUTHOR_EMAIL}>
                **Branch**: ${env.GIT_BRANCH}
                **Message**: ${env.GIT_COMMIT_MESSAGE}
                
                [**Build output**](${JENKINS_SERVER}/job/${env.JOB_NAME}/${env.BUILD_NUMBER}/console)
                [**Test result**](${JENKINS_SERVER}/job/${env.JOB_NAME}/lastBuild/testReport/)
                [**Coverage report**](${JENKINS_SERVER}/job/${env.JOB_NAME}/lastBuild/Coverage_20Report/))
                [**History**](${JENKINS_SERVER}/job/${env.JOB_NAME}/${env.BUILD_NUMBER}/testReport/history/)
            """,
            footer: "Build Duration: ${currentBuild.durationString.replace(' and counting', '')}",
            webhookURL: DISCORD_WEBHOOK_URL,
            result: status == "Build Success" ? 'SUCCESS' : 'FAILURE'
        )
    }
}
