package urlshortener.dataprovider.database.shorturl

import urlshortener.dataprovider.extension.toDomain
import urlshortener.domain.ShortUrl
import urlshortener.usecase.shorturl.GetAllShortUrls

class GetAllShortUrlsDataProvider(private val repository: ShortUrlRepository): GetAllShortUrls {
    override fun getAllShortUrls(): List<ShortUrl> = repository.findAll().map { it.toDomain() }
}