package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl

interface ReturnRedirection {
    fun returnRedirectionWhileSavingClick(hash: String,
                                          ip: String,
                                          userAgent: String): ShortUrl
}