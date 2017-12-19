package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl

interface ReturnRedirectionWhileSavingClick {
    fun returnRedirectionWhileSavingClick(hash: String,
                                          ip: String,
                                          userAgent: String): ShortUrl
}