package urlshortener.dataprovider.database.shorturl

import es.unizar.shortener.provider.database.shorturl.FindUrlByIdDataProvider
import es.unizar.shortener.provider.database.shorturl.ShortUrlEntity
import es.unizar.shortener.provider.database.shorturl.ShortUrlRepository
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import urlshortener.domain.ShortUrl
import urlshortener.usecase.exception.NotFoundException
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
                    mode = 200
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
                        safe = null,
                        ip = null,
                        country = null
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