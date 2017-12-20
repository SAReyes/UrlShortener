package urlshortener.usecase.shorturl

interface RetrieveBrowser {

    fun getBrowser(ua: String): String
}