package urlshortener.usecase.shorturl

import urlshortener.domain.Click
import urlshortener.domain.ShortUrl
import urlshortener.usecase.click.SaveClick

class ReturnRedirectionWhileSavingClickImpl(private val find: FindUrlById,
                                            private val storeClick: SaveClick,
                                            private val dateFactory: DateFactory) : ReturnRedirectionWhileSavingClick {

    override fun returnRedirectionWhileSavingClick(hash: String, ip: String): ShortUrl {
        val shortUrl = find.findUrlById(hash)

        storeClick.saveClick(Click(hash = hash, created = dateFactory.now(), ip = ip))

        return shortUrl
    }
}