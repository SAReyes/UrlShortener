package urlshortener.usecase.click

import urlshortener.domain.Click

interface SaveClick {

    fun saveClick(click: Click)
}