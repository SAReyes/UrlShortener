package urshortener.usecase.shorturl

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import urlshortener.domain.ShortUrl
import urlshortener.usecase.exception.BadRequestException
import urlshortener.usecase.shorturl.*
import java.net.URI
import java.util.*

object CreateAndSaveUrlSpecs : Spek({
    describe("CreateAndSaveUrl use case") {
        val validator = mock(UrlValidator::class)
        val encoder = mock(UrlEncoder::class)
        val date = mock(DateFactory::class)
        val saveUrl = mock(SaveUrl::class)

        val sut = CreateAndSaveUrlImpl(
                urlValidator = validator,
                urlEncoder = encoder,
                dateFactory = date,
                saveUrl = saveUrl
        )

        given("all parameters are set properly") {
            val aDate = GregorianCalendar(2017, 12, 17).time

            When calling date.now() `it returns` aDate
            When calling encoder.encode(any(String::class)) `it returns` "hash"
            When calling validator.validate(any(String::class)) `it returns` true

            val response = sut.createAndSaveUrl(
                    targetUrl = "target",
                    sponsor = "sponsor",
                    owner = "owner",
                    mode = 200,
                    ip = "ip",
                    safe = false,
                    country = "country"
            )

            it("should store the correct short url") {
                Verify on saveUrl that saveUrl.saveUrl(ShortUrl(
                        hash = "hash",
                        target = "target",
                        uri = URI("/hash"),
                        sponsor = "sponsor",
                        created = aDate,
                        owner = "owner",
                        mode = 200,
                        safe = false,
                        ip = "ip",
                        country = "country"
                )) was called
            }

            it("should return the expected object") {
                response `should equal` ShortUrl(
                        hash = "hash",
                        target = "target",
                        uri = URI("/hash"),
                        sponsor = "sponsor",
                        created = aDate,
                        owner = "owner",
                        mode = 200,
                        safe = false,
                        ip = "ip",
                        country = "country"
                )
            }

            it("should call the encoder with the correct parameter") {
                Verify on encoder that encoder.encode("target") was called
            }
        }

        given("the target cannot be validated") {
            When calling validator.validate(any(String::class)) `it returns` false

            val func = {
                sut.createAndSaveUrl(
                        targetUrl = "target",
                        sponsor = "sponsor",
                        owner = "owner",
                        mode = 200,
                        ip = "ip",
                        safe = false,
                        country = "country"
                )
            }

            it("should raise an exception") {
                func `should throw` BadRequestException::class `with message` "target failed validations"
            }
        }
    }
})
