package es.unizar.shortener.provider.system

import urlshortener.usecase.shorturl.UrlValidator
import org.apache.commons.validator.routines.UrlValidator as commonsValidator

class UrlValidatorDataProvider : urlshortener.usecase.shorturl.UrlValidator {

    private val validator: org.apache.commons.validator.routines.UrlValidator = org.apache.commons.validator.routines.UrlValidator(arrayOf("http", "https"))

    override fun validate(url: String): Boolean = validator.isValid(url)
}