package urlshortener.dataprovider.extension

import urlshortener.dataprovider.database.click.ClickEntity
import urlshortener.dataprovider.database.shorturl.ShortUrlEntity
import java.sql.Date

fun urlshortener.domain.Click.toEntity() = ClickEntity(
        id = id,
        hash = ShortUrlEntity(
                hash = hash,
                created = Date(created.time),
                target = "http://example.org",
                safetyLastChecked = Date(created.time)
        ),
        created = Date(created.time),
        referrer = referrer,
        browser = browser,
        platform = platform,
        ip = ip,
        country = country
)

fun urlshortener.domain.ShortUrl.toEntity() = ShortUrlEntity(
        hash = hash,
        target = target,
        sponsor = sponsor,
        created = Date(created.time),
        owner = owner,
        mode = mode,
        safe = safe,
        ip = ip,
        country = country,
        safetyLastChecked = Date(safetyLastChecked.time)
)
