package urlshortener.usecase.shorturl

import urlshortener.domain.Click

interface GetAllClicks {
    fun getAllClicks(): List<Click>
}