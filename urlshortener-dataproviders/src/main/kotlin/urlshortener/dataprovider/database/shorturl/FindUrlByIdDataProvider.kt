package urlshortener.dataprovider.database.shorturl

import urlshortener.dataprovider.extension.toDomain

class FindUrlByIdDataProvider(private val repository: ShortUrlRepository) : urlshortener.usecase.shorturl.FindUrlById {

    override fun findUrlById(hash: String): urlshortener.domain.ShortUrl {
        val shortUrl = repository.findById(hash)

        if(shortUrl.isPresent) {
            return shortUrl.get().toDomain()
        } else {
            throw urlshortener.usecase.exception.NotFoundException("$hash was not found on the url repository")
        }
    }
}