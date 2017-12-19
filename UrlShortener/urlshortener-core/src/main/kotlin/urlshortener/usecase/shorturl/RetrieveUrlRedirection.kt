package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl

interface RetrieveUrlRedirection {
    fun returnRedirectionWhileSavingClick(hash: String,
                                          ip: String,
                                          userAgent: String): ShortUrl
}