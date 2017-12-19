package urlshortener.usecase.shorturl

interface GetBrowserFromUserAgent {

    fun getBrowserFromUserAgent(ua: String): String
}