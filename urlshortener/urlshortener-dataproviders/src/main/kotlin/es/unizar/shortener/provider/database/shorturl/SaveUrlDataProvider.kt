package es.unizar.shortener.provider.database.shorturl

import es.unizar.shortener.provider.extension.toEntity

class SaveUrlDataProvider(private val repository: ShortUrlRepository) : urlshortener.usecase.shorturl.SaveUrl {

    override fun saveUrl(url: urlshortener.domain.ShortUrl) {
        repository.save(url.toEntity())
    }
}