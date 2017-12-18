package urlshortener.dataprovider.database.shorturl

import es.unizar.shortener.provider.database.shorturl.SaveUrlDataProvider
import es.unizar.shortener.provider.database.shorturl.ShortUrlEntity
import es.unizar.shortener.provider.database.shorturl.ShortUrlRepository
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import urlshortener.domain.ShortUrl
import java.sql.Date
import java.util.*

object SaveUrlDataProviderSpecs : Spek({
    describe("SaveUrl data provider") {
        val repository = mock(ShortUrlRepository::class)

        val sut = SaveUrlDataProvider(repository)

        given("a domain object") {
            val aDate = GregorianCalendar(2017, 12, 17).time

            val domain = ShortUrl(
                    hash = "hash",
                    target = "target",
                    sponsor = "sponsor",
                    created = aDate,
                    owner = "owner",
                    mode = 200,
                    safe = true,
                    ip = "ip",
                    country = "country"
            )

            it("should save the correct entity object") {
                sut.saveUrl(domain)

                Verify on repository that repository.save(ShortUrlEntity(
                        hash = "hash",
                        target = "target",
                        sponsor = "sponsor",
                        created = Date(aDate.time),
                        owner = "owner",
                        mode = 200,
                        safe = true,
                        ip = "ip",
                        country = "country"
                )) was called
            }
        }
    }
})