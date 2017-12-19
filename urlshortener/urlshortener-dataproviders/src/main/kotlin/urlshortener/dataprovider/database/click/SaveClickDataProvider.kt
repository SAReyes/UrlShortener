package urlshortener.dataprovider.database.click

import urlshortener.dataprovider.extension.toEntity

class SaveClickDataProvider(private val repository: ClickRepository): urlshortener.usecase.click.SaveClick {

    override fun saveClick(click: urlshortener.domain.Click) {
        repository.save(click.toEntity())
    }
}