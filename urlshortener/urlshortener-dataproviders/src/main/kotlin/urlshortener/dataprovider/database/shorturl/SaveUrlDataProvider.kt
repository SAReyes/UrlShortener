package urlshortener.dataprovider.database.shorturl

import urlshortener.dataprovider.extension.toEntity

class SaveUrlDataProvider(private val repository: ShortUrlRepository) : urlshortener.usecase.shorturl.SaveUrl {

    override fun saveUrl(url: urlshortener.domain.ShortUrl) {
        repository.save(url.toEntity())
    }
}