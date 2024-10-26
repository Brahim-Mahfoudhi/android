import okhttp3.OkHttpClient
import okhttp3.Request
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

// custom OkClient die alle certificaten accepteert, voor interactie Emulator & localhost server
// NIET SAFE VOOR PRODUCTIE
class UnsafeOkHttpClient {
    fun getUnsafeOkHttpClient(): OkHttpClient {
        return try {
            // Maak een TrustManager die geen certificaatvalidatie uitvoert
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
            })

            // Initialiseer een SSLContext met de custom TrustManager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Maak de OkHttpClient met de custom SSLContext
            OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true } // Accepteer alle hostnames
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
