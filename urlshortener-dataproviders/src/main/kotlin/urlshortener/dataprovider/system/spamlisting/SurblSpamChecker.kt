package urlshortener.dataprovider.system.spamlisting

class SurblSpamChecker : SpamChecker {
    override fun isSpam(url: String): Boolean = check("$url.multi.surbl.org")
}