package urlshortener.dataprovider.system.spam

class SpamHausSpamChecker : SpamChecker {
    override fun isSpam(url: String): Boolean = check("$url.dbl.spamhaus.org")
}