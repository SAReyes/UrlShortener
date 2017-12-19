package urshortener.usecase.shorturl

import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import urlshortener.domain.Click
import urlshortener.usecase.click.SaveClick
import urlshortener.usecase.shorturl.*
import java.util.*

object ReturnRedirectionWhileSavingClickSpecs : Spek({
    describe("ReturnRedirectionWhileSavingClick use case") {
        val find = mock(FindUrlById::class)
        val click = mock(SaveClick::class)
        val date = mock(DateFactory::class)
        val browser = mock(RetrieveBrowser::class)
        val platform = mock(RetrievePlatform::class)

        val sut = ReturnRedirectionWhileSavingClickImpl(
                find = find,
                storeClick = click,
                dateFactory = date,
                retrieveBrowser = browser,
                retrievePlatform = platform
        )

        given("the date factory returns a specific date") {
            val aDate = GregorianCalendar(2017, 12, 17).time

            When calling date.now() `it returns` aDate
            When calling browser.getBrowser("ua") `it returns` "ua-browser"
            When calling platform.getPlatform("ua") `it returns` "ua-platform"

            it("should store the correct data on click table") {
                sut.returnRedirectionWhileSavingClick(hash = "hash", ip = "ip", userAgent = "ua")

                Verify on click that click.saveClick(Click(
                        hash = "hash",
                        ip = "ip",
                        created = aDate,
                        browser = "ua-browser",
                        platform = "ua-platform"
                )) was called
            }
        }

        given("a specific hash") {

            it("should fetch for the correct url") {
                sut.returnRedirectionWhileSavingClick(hash = "some-hash", ip = "ip", userAgent = "ua")

                Verify on find that find.findUrlById(hash = "hash") was called
            }
        }
    }
})