package urlshortener.dataprovider.system.blocking

class SurblSpamChecker : SpamChecker {
    override fun isSpam(url: String): Boolean = check("$url.multi.surbl.org")
}