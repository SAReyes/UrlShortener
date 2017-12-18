package urlshortener.usecase.shorturl

import urlshortener.domain.ShortUrl
import urlshortener.usecase.exception.BadRequestException
import java.net.URI

class CreateAndSaveUrlImpl(private val urlValidator: UrlValidator,
                           private val urlEncoder: UrlEncoder,
                           private val dateFactory: DateFactory,
                           private val saveUrl: SaveUrl) : CreateAndSaveUrl {

    override fun createAndSaveUrl(targetUrl: String,
                                  sponsor: String?,
                                  owner: String,
                                  mode: Int,
                                  ip: String,
                                  safe: Boolean,
                                  country: String?): ShortUrl {
        if (urlValidator.validate(url = targetUrl)) {
            val id = urlEncoder.encode(targetUrl)

            val su = ShortUrl(
                    hash = id,
                    target = targetUrl,
                    uri = URI("/$id"),
                    sponsor = sponsor,
                    created = dateFactory.now(),
                    owner = owner,
                    mode = mode,
                    safe = safe,
                    ip = ip,
                    country = country
            )

            saveUrl.saveUrl(su)

            return su
        } else {
            throw BadRequestException("$targetUrl failed validations")
        }
    }
}