package urlshortener.dataprovider.extension

import urlshortener.dataprovider.database.shorturl.ShortUrlEntity
import urlshortener.domain.ShortUrl
import java.net.URI
import java.sql.Date

fun ShortUrlEntity.toDomain() = ShortUrl(
        hash = hash,
        target = target,
        sponsor = sponsor,
        created = Date(created.time),
        owner = owner,
        mode = mode,
        safe = safe!!,
        ip = ip,
        country = country,
        uri = URI("/$hash"),
        qr = URI("/qr"),
        safetyLastChecked = Date(safetyLastChecked.time)
)