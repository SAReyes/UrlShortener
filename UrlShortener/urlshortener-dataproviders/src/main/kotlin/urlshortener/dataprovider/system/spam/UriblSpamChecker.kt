package urlshortener.dataprovider.system.spam

class UriblSpamChecker : SpamChecker {

    override fun isSpam(url: String): Boolean = check("$url.black.uribl.com")
}