package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl
import java.net.URI

interface CreateAndSaveUrl {
    fun createAndSaveUrl(targetUrl: String,
                         domainUri: URI,
                         sponsor: String?,
                         owner: String,
                         mode: Int,
                         ip: String,
                         safe: Boolean = true,
                         country: String? = null): ShortUrl
}