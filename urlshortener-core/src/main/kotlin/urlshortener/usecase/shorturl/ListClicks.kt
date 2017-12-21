package urlshortener.usecase.shorturl

import urlshortener.domain.Click

interface ListClicks {
    fun listClicks(): List<Click>
}