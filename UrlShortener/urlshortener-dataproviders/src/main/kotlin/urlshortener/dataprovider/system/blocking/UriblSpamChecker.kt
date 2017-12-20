package urlshortener.dataprovider.system.blocking

class UriblSpamChecker : SpamChecker {

    override fun isSpam(url: String): Boolean = check("$url.black.uribl.com")
}