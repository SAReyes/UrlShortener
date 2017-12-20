package urlshortener.dataprovider.database.shorturl

import urlshortener.dataprovider.extension.toEntity
import urlshortener.domain.ShortUrl
import java.sql.Date

class SaveUrlDataProvider(private val repository: ShortUrlRepository) : urlshortener.usecase.shorturl.SaveUrl {
    override fun saveUrl(url: ShortUrl) {
        repository.save(url.toEntity())
    }

    override fun update(url: ShortUrl) {
        val entity = repository.findById(url.hash)

        if (entity.isPresent) {
            entity.get().safetyLastChecked = Date(url.safetyLastChecked.time)
            repository.save(entity.get())
        }
    }
}