package urlshortener.usecase.shorturl

import java.util.*

interface DateFactory {

    fun now(): Date
}