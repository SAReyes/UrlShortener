package urlshortener.dataprovider.system.spam

class SurblSpamChecker : SpamChecker {
    override fun isSpam(url: String): Boolean = check("$url.multi.surbl.org")
}