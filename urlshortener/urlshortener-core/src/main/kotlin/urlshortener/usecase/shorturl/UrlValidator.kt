package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl
import java.util.*

interface UrlValidator {

    fun validate(url: String): Boolean

    fun isSpam(url: String): Boolean

    fun shouldCheckSafety(url: ShortUrl, now: Date): Boolean
}