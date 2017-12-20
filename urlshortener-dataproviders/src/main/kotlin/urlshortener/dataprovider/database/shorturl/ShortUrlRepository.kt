package urlshortener.dataprovider.database.shorturl

import org.springframework.data.repository.CrudRepository

interface ShortUrlRepository : CrudRepository<ShortUrlEntity, String>
