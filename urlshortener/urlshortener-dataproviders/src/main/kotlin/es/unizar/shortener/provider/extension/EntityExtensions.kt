package es.unizar.shortener.provider.extension

import es.unizar.shortener.provider.database.shorturl.ShortUrlEntity
import java.util.*

fun ShortUrlEntity.toDomain() = urlshortener.domain.ShortUrl(
        hash = hash,
        target = target,
        sponsor = sponsor,
        created = Date(created.time),
        owner = owner,
        mode = mode,
        safe = safe,
        ip = ip,
        country = country
)