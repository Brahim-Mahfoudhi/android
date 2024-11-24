pipeline {
    agent any

    environment {
        APP_ARCHIVE_NAME = 'app'
        APP_MODULE_NAME = 'android-template'
        CHANGELOG_CMD = 'git log --date=format:"%Y-%m-%d" --pretty="format: * %s% b (%an, %cd)" | head -n 10 > commit-changelog.txt'
//        FIREBASE_GROUPS = 'mobile-dev-team, mobile-qa-team'
//        FIREBASE_APP_DIST_CMD = "firebase appdistribution:distribute app/build/outputs/apk/\$APP_BUILD_TYPE/app-\$APP_BUILD_TYPE.apk --app \$FIREBASE_ID --release-notes-file commit-changelog.txt --groups \"\$FIREBASE_GROUPS\""
//        GOOGLE_APPLICATION_CREDENTIALS = "${HOME}/google-service-accounts/${APP_MODULE_NAME}.json"
    }

    tools {
        jdk "Java 11"
    }

    options {
        // prevent multiple builds from running concurrently, that come from the same git branch
        disableConcurrentBuilds()
    }

    stages {
        stage("PR") {
            when {
                changeRequest target: 'master'
            }
            stages {
                stage("Build") {
                    steps {
                        sh './gradlew clean assembleAlpha'
                    }
                }
                stage("Test") {
                    steps {
                        sh './gradlew testAlphaUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint") {
                    steps {
                        sh './gradlew lintAlpha'
                    }
                    post {
                        always {
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Detekt") {
                    steps {
                        sh './gradlew downloadDetektConfig detektAlpha'
                    }
                    post {
                        always {
                            archiveArtifacts '*/build/reports/detekt/*.html'
                        }
                    }
                }
            }
        }
        stage("Alpha") {
            environment {
                APP_BUILD_TYPE = "alpha"
                FIREBASE_ID = '1:292666345594:android:21903887d2d5200065d3c3' // from your project google-services.json client_info
            }

            when {
                branch 'master'
            }

            stages {
                stage("License") {
                    steps {
                        sh './gradlew createLicenseReport'
                    }
                    post {
                        always {
                            archiveArtifacts "**/build/licenses/*.html"
                        }
                    }
                }
                stage("Build") {
                    steps {
                        sh './gradlew clean assembleAlpha bundleAlpha'
                    }
                }
                stage("Test") {
                    steps {
                        sh './gradlew testAlphaUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint") {
                    steps {
                        sh './gradlew lintAlpha'
                    }
                    post {
                        always {
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Detekt") {
                    steps {
                        sh './gradlew downloadDetektConfig detektAlpha'
                    }
                    post {
                        always {
                            archiveArtifacts '*/build/reports/detekt/*.html'
                        }
                    }
                }
                stage("App Distribution") {
                    steps {
                        sh "${CHANGELOG_CMD}"
                        sh './gradlew appDistributionUploadAlpha'
//                        sh "${FIREBASE_APP_DIST_CMD}"
                    }
                }
                stage("Deploy to Play Store Alpha") {
                    steps {
                        sh './gradlew publishAlphaBundle --artifact-dir app/build/outputs/bundle/alpha'
                    }
                }
            }
        }
        stage("Beta") {
            environment {
                APP_BUILD_TYPE = "beta"
                FIREBASE_ID = '1:292666345594:android:79471cfe9138c223' // from your project google-services.json client_info
            }
            when {
                branch 'beta'
            }
            stages {
                stage("License") {
                    steps {
                        sh './gradlew createLicenseReport'
                    }
                    post {
                        always {
                            archiveArtifacts "**/build/licenses/*.html"
                        }
                    }
                }
                stage("Build") {
                    steps {
                        sh './gradlew clean assembleBeta bundleBeta'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/apk/beta/${APP_ARCHIVE_NAME}-beta.apk"
                        }
                    }
                }
                stage("Test") {
                    steps {
                        sh './gradlew testBetaUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint") {
                    steps {
                        sh './gradlew lintBeta'
                    }
                    post {
                        always {
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Detekt") {
                    steps {
                        sh './gradlew detektBeta'
                    }
                    post {
                        always {
                            archiveArtifacts '*/build/reports/detekt/*.html'
                        }
                    }
                }
                stage("App Distribution") {
                    steps {
                        sh "${CHANGELOG_CMD}"
                        sh './gradlew appDistributionUploadAlpha'
//                        sh "${FIREBASE_APP_DIST_CMD}"
                    }
                }
                stage("Deploy to Play Store") {
                    steps {
                        sh "./gradlew publishBetaBundle --artifact-dir app/build/outputs/bundle/beta"
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/bundle/beta/${APP_ARCHIVE_NAME}-beta.aab"
                        }
                    }
                }
            }
        }
        stage("Release") {
            environment {
                APP_BUILD_TYPE = "release"
                FIREBASE_ID = '1:292666345594:android:79471cfe9138c223' // from your project google-services.json client_info
            }
            when {
                branch 'release'
            }
            stages {
                stage("License") {
                    steps {
                        sh './gradlew createLicenseReport'
                    }
                    post {
                        always {
                            archiveArtifacts "**/build/licenses/*.html"
                        }
                    }
                }
                stage("Build") {
                    steps {
                        sh "${CHANGELOG_CMD}"
                        sh './gradlew clean assembleRelease bundleRelease appDistributionUploadRelease'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/apk/release/${APP_ARCHIVE_NAME}-release.apk"
                        }
                    }
                }
                stage("Test") {
                    steps {
                        sh './gradlew testReleaseUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint") {
                    steps {
                        sh './gradlew lintRelease'
                    }
                    post {
                        always {
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Detekt") {
                    steps {
                        sh './gradlew downloadDetektConfig detektRelease'
                    }
                    post {
                        always {
                            archiveArtifacts '*/build/reports/detekt/*.html'
                        }
                    }
                }
                stage("App Distribution") {
                    steps {
                        sh "${CHANGELOG_CMD}"
                        sh './gradlew appDistributionUploadAlpha'
//                        sh "${FIREBASE_APP_DIST_CMD}"
                    }
                }
                stage("Deploy to Play Store") {
                    steps {
                        sh "./gradlew publishReleaseBundle --artifact-dir app/build/outputs/bundle/release"
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/bundle/release/${APP_ARCHIVE_NAME}-release.aab"
                        }
                    }
                }
            }
        }
        stage("Increment Version Code") {
            when {
                anyOf { branch 'master'; branch 'beta'; branch 'release' }
            }
            steps {
                sh './gradlew incrementVersionCode'
            }
        }
    }

    post {
        failure {
            mail(to: 'jeffdcamp@gmail.com',
                    subject: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) has failed",
                    body: "Please go to ${env.BUILD_URL}.")

            // Notify build breaker
            step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: emailextrecipients([[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']])])
        }
    }
}
