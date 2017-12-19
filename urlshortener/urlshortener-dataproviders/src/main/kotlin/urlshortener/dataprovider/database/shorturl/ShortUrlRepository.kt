package urlshortener.dataprovider.database.shorturl

interface ShortUrlRepository : org.springframework.data.repository.CrudRepository<ShortUrlEntity, String>
