package urlshortener.usecase.shorturl

interface GetPlatformFromUserAgent {

    fun getPlatformFromUserAgent(ua: String): String
}