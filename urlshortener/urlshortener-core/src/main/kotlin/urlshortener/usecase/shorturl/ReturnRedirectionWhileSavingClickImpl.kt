package urlshortener.usecase.shorturl

import urlshortener.domain.Click
import urlshortener.domain.ShortUrl
import urlshortener.usecase.click.SaveClick
import urlshortener.usecase.shorturl.GetPlatformFromUserAgent
import urlshortener.usecase.shorturl.GetBrowserFromUserAgent

class ReturnRedirectionWhileSavingClickImpl(private val find: FindUrlById,
                                            private val storeClick: SaveClick,
                                            private val getPlatformFromUserAgent: GetPlatformFromUserAgent,
                                            private val getBrowserFromUserAgent: GetBrowserFromUserAgent,
                                            private val dateFactory: DateFactory) : ReturnRedirectionWhileSavingClick {

    override fun returnRedirectionWhileSavingClick(hash: String,
                                                   ip: String,
                                                   userAgent: String): ShortUrl {
        val shortUrl = find.findUrlById(hash)

        storeClick.saveClick(Click(
                hash = hash,
                created = dateFactory.now(),
                ip = ip,
                browser = getBrowserFromUserAgent.getBrowserFromUserAgent(userAgent),
                platform = getPlatformFromUserAgent.getPlatformFromUserAgent(userAgent)
        ))

        return shortUrl
    }
}