package urlshortener.dataprovider.extension

import urlshortener.dataprovider.database.shorturl.ShortUrlEntity

fun ShortUrlEntity.toDomain() = urlshortener.domain.ShortUrl(
        hash = hash,
        target = target,
        sponsor = sponsor,
        created = java.util.Date(created.time),
        owner = owner,
        mode = mode,
        safe = safe,
        ip = ip,
        country = country,
        uri = java.net.URI("/$hash"),
        qr = java.net.URI("/qr")
)