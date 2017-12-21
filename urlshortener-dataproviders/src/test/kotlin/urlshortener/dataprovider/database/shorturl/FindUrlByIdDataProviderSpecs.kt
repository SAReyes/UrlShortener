package urlshortener.dataprovider.database.shorturl

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import urlshortener.domain.ShortUrl
import urlshortener.usecase.exception.NotFoundException
import java.net.URI
import java.sql.Date
import java.util.*

object FindUrlByIdDataProviderSpecs : Spek({
    describe("FindUrlById data provider") {
        val repository = mock(ShortUrlRepository::class)

        val sut = FindUrlByIdDataProvider(repository = repository)

        given("a shortUrl is returned") {
            val aDate = GregorianCalendar(2017, 12, 17).time

            When calling repository.findById("hash") `it returns` Optional.of(ShortUrlEntity(
                    hash = "hash",
                    target = "target",
                    created = Date(aDate.time),
                    mode = 200,
                    safetyLastChecked = Date(aDate.time),
                    safe = true
            ))

            val response = sut.findUrlById("hash")

            it("should return the correct domain object") {
                response `should equal` ShortUrl(
                        hash = "hash",
                        target = "target",
                        created = aDate,
                        sponsor = null,
                        owner = null,
                        mode = 200,
                        safe = true,
                        ip = null,
                        country = null,
                        uri = null,
                        qr = null,
                        safetyLastChecked = aDate
                )
            }
        }

        given("no shortUrl is found") {
            When calling repository.findById("hash") `it returns` Optional.empty()

            val func = {
                sut.findUrlById("a-hash")
            }

            it("should throw a not found exception") {
                func `should throw` NotFoundException::class `with message` "a-hash was not found on the url repository"
            }
        }
    }
})