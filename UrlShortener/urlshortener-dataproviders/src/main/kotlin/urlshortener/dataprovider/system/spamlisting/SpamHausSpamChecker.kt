package urlshortener.dataprovider.system.spamlisting

class SpamHausSpamChecker : SpamChecker {
    override fun isSpam(url: String): Boolean = check("$url.dbl.spamhaus.org")
}