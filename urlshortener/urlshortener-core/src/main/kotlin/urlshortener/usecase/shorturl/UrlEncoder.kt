package urlshortener.usecase.shorturl

interface UrlEncoder {

    fun encode(url: String): String
}