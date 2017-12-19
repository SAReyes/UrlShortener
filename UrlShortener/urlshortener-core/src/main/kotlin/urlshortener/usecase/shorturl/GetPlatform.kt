package urlshortener.usecase.shorturl

interface GetPlatform {

    fun getPlatform(ua: String): String
}