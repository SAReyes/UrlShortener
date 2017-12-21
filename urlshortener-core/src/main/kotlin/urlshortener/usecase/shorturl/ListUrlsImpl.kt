package urlshortener.usecase.shorturl

class ListUrlsImpl(private val getAllShortUrls: GetAllShortUrls) : ListUrls {

    override fun listUrls() = getAllShortUrls.getAllShortUrls()
}