package urlshortener.usecase.shorturl

import urlshortener.domain.Click
import urlshortener.domain.ShortUrl
import urlshortener.usecase.click.SaveClick
import urlshortener.usecase.exception.BadRequestException

class RetrieveUrlRedirectionImpl(private val find: FindUrlById,
                                 private val storeClick: SaveClick,
                                 private val dateFactory: DateFactory,
                                 private val retrievePlatform: RetrievePlatform,
                                 private val retrieveBrowser: RetrieveBrowser,
                                 private val urlValidator: UrlValidator,
                                 private val saveUrl: SaveUrl) : RetrieveUrlRedirection {

    override fun returnRedirectionWhileSavingClick(hash: String,
                                                   ip: String,
                                                   userAgent: String): ShortUrl {
        val shortUrl = find.findUrlById(hash)

        if (!shortUrl.safe) {
            throw BadRequestException("${shortUrl.target} is no longer safe")
        }

        val now = dateFactory.now()
        if (urlValidator.shouldCheckSafety(shortUrl, now)) {
            shortUrl.safetyLastChecked = now
            shortUrl.safe = !urlValidator.isSpam(shortUrl.target)

            saveUrl.saveUrl(shortUrl)

            if(!shortUrl.safe){
                throw BadRequestException("${shortUrl.target} is no longer safe")
            }
        }

        storeClick.saveClick(Click(
                hash = hash,
                created = dateFactory.now(),
                ip = ip,
                browser = retrieveBrowser.getBrowser(userAgent),
                platform = retrievePlatform.getPlatform(userAgent)
        ))

        return shortUrl
    }
}