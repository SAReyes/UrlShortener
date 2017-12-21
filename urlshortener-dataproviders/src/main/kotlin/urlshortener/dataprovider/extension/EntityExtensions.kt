package urlshortener.dataprovider.extension

import urlshortener.dataprovider.database.click.ClickEntity
import urlshortener.dataprovider.database.shorturl.ShortUrlEntity
import urlshortener.domain.Click
import urlshortener.domain.ShortUrl
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
        safetyLastChecked = Date(safetyLastChecked.time)
)

fun ClickEntity.toDomain() = Click(
        hash = hash.hash,
        created = Date(created.time),
        referrer = referrer,
        browser = browser!!,
        platform = platform!!,
        ip = ip,
        country = country
)