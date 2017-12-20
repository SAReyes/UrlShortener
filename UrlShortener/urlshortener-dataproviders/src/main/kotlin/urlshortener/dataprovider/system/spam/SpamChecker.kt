package urlshortener.dataprovider.system.spam

import java.net.InetAddress
import java.net.UnknownHostException

interface SpamChecker {

    fun check(host: String): Boolean {
        return try {
            InetAddress.getAllByName(host)
            true
        } catch (ex: UnknownHostException) {
            false
        }
    }

    fun isSpam(url: String): Boolean
}