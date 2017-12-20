package urlshortener.dataprovider.system.blocking

class SpamHausSpamChecker : SpamChecker {
    override fun isSpam(url: String): Boolean = check("$url.dbl.spamhaus.org")
}