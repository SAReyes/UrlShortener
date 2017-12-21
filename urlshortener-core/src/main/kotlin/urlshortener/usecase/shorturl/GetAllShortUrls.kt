package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl

interface GetAllShortUrls {

    fun getAllShortUrls(): List<ShortUrl>
}