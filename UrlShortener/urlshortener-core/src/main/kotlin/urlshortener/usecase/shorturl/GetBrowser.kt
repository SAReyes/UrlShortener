package urlshortener.usecase.shorturl

interface GetBrowser {

    fun getBrowser(ua: String): String
}