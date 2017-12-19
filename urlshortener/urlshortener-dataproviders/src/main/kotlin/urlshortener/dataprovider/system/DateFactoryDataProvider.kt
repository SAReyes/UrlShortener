package urlshortener.dataprovider.system

import urlshortener.usecase.shorturl.DateFactory
import java.util.*

class DateFactoryDataProvider : urlshortener.usecase.shorturl.DateFactory {
    override fun now(): java.util.Date = java.util.Date(System.currentTimeMillis())
}