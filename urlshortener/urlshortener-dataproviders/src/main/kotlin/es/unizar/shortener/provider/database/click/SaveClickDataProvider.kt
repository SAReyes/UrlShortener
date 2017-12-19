package es.unizar.shortener.provider.database.click

import es.unizar.shortener.provider.extension.toEntity

class SaveClickDataProvider(private val repository: ClickRepository): urlshortener.usecase.click.SaveClick {

    override fun saveClick(click: urlshortener.domain.Click) {
        repository.save(click.toEntity())
    }
}