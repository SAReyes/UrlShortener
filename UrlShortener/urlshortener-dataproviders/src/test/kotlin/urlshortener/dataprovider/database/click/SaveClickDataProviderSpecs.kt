package urlshortener.dataprovider.database.click

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import urlshortener.dataprovider.database.shorturl.ShortUrlEntity
import urlshortener.domain.Click
import java.sql.Date
import java.util.*

object SaveClickDataProviderSpecs : Spek({
    describe("SaveClick data provider") {
        val repository = mock(ClickRepository::class)

        val sut = SaveClickDataProvider(repository)

        given("a domain object") {
            val aDate = GregorianCalendar(2017, 12, 17).time

            val domain = Click(
                    id = 1,
                    hash = "hash",
                    created = aDate,
                    referrer = "referrer",
                    browser = "browser",
                    platform = "platform",
                    ip = "ip",
                    country = "country"
            )

            it("should save the correct entity object") {
                sut.saveClick(domain)

                Verify on repository that repository.save(ClickEntity(
                        id = 1,
                        hash = ShortUrlEntity(
                                hash = "hash",
                                target = "http://example.org",
                                sponsor = null,
                                created = Date(aDate.time),
                                owner = null,
                                mode = 500,
                                safe = null,
                                ip = null,
                                country = null,
                                safetyLastChecked = Date(aDate.time)
                        ),
                        created = Date(aDate.time),
                        referrer = "referrer",
                        browser = "browser",
                        platform = "platform",
                        ip = "ip",
                        country = "country"
                )) was called
            }
        }
    }
})