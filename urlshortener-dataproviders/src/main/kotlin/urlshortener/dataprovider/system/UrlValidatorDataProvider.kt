package urlshortener.dataprovider.system

import urlshortener.dataprovider.system.spamlisting.SpamChecker
import urlshortener.domain.ShortUrl
import urlshortener.usecase.shorturl.DateFactory
import urlshortener.usecase.shorturl.UrlValidator
import java.net.URI
import java.util.*
import org.apache.commons.validator.routines.UrlValidator as commonsValidator

class UrlValidatorDataProvider(private val spamCheckers: List<SpamChecker>,
                               private val dateFactory: DateFactory,
                               private val safetyUrlDateLimit: Date) : UrlValidator {

    override fun shouldCheckSafety(url: ShortUrl, now: Date): Boolean {
        val probability =
                Math.min(dateFactory.now().time - url.safetyLastChecked.time, safetyUrlDateLimit.time).toDouble() /
                        safetyUrlDateLimit.time

        val drawn = Random().nextDouble()

        return drawn <= probability
    }

    private val validator: commonsValidator = commonsValidator(arrayOf("http", "https"))

    override fun validate(url: String): Boolean = validator.isValid(url)

    override fun isSpam(url: String): Boolean {
        val uri = URI(url)
        return spamCheckers.any { it.isSpam(uri.host) }
    }
}