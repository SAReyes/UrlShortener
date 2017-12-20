package urlshortener.dataprovider.database.click

import urlshortener.dataprovider.extension.toEntity
import urlshortener.domain.Click

class SaveClickDataProvider(private val repository: ClickRepository): urlshortener.usecase.click.SaveClick {

    override fun saveClick(click: Click) {
        repository.save(click.toEntity())
    }
}