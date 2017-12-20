package urlshortener.dataprovider.system.spamlisting

class UriblSpamChecker : SpamChecker {

    override fun isSpam(url: String): Boolean = check("$url.black.uribl.com")
}