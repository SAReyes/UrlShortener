package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl

interface FindUrlById {

    fun findUrlById(hash: String): ShortUrl
}