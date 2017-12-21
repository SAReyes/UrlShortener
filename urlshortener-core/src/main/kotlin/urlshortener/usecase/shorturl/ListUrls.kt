package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl

interface ListUrls {

    fun listUrls(): List<ShortUrl>
}