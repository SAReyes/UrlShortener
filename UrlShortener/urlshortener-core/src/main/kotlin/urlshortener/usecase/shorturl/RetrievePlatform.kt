package urlshortener.usecase.shorturl

interface RetrievePlatform {

    fun getPlatform(ua: String): String
}