package urlshortener.usecase.shorturl

class ListClicksImpl(private val getAllClicks: GetAllClicks) : ListClicks {

    override fun listClicks() = getAllClicks.getAllClicks()
}