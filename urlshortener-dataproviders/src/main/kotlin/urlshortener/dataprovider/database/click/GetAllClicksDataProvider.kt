package urlshortener.dataprovider.database.click

import urlshortener.dataprovider.extension.toDomain
import urlshortener.domain.Click
import urlshortener.usecase.shorturl.GetAllClicks

class GetAllClicksDataProvider(private val repository: ClickRepository): GetAllClicks {

    override fun getAllClicks(): List<Click> = repository.findAll().map { it.toDomain() }
}