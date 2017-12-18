package urlshortener.usecase.shorturl

interface UrlValidator {

    fun validate(url: String): Boolean
}