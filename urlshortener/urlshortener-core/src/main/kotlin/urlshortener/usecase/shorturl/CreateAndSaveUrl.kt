package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl

interface CreateAndSaveUrl {
    fun createAndSaveUrl(targetUrl: String,
                         sponsor: String?,
                         owner: String,
                         mode: Int,
                         ip: String,
                         safe: Boolean = true,
                         country: String? = null): ShortUrl
}