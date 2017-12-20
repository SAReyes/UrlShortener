package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl

interface SaveUrl {

    fun saveUrl(url: ShortUrl)

    fun update(url: ShortUrl)
}