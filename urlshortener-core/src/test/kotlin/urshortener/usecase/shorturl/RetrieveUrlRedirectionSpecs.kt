package urshortener.usecase.shorturl

import com.nhaarman.mockito_kotlin.eq
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import urlshortener.domain.Click
import urlshortener.domain.ShortUrl
import urlshortener.usecase.click.SaveClick
import urlshortener.usecase.exception.BadRequestException
import urlshortener.usecase.shorturl.*
import java.net.URI
import java.util.*

class RetrieveUrlRedirectionSpecs : Spek({
    describe("RetrieveUrlRedirection use case") {
        var sut: RetrieveUrlRedirection? = null
        var find: FindUrlById? = null
        var date: DateFactory? = null
        var saveUrl: SaveUrl? = null
        var urlValidator: UrlValidator? = null
        var click: SaveClick? = null

        val aDate = GregorianCalendar(2017, 12, 17).time

        val su = ShortUrl(
                hash = "hash",
                target = "target",
                ip = "ip",
                safetyLastChecked = aDate,
                country = "country",
                safe = true,
                mode = 200,
                owner = "owner",
                created = aDate,
                sponsor = "sponsor",
                uri = URI("/uri"),
                qr = URI("/qr")
        )

        beforeEachTest {
            val browser = mock(RetrieveBrowser::class)
            val platform = mock(RetrievePlatform::class)

            click = mock(SaveClick::class)
            date = mock(DateFactory::class)
            saveUrl = mock(SaveUrl::class)
            urlValidator = mock(UrlValidator::class)
            find = mock(FindUrlById::class)
            sut = RetrieveUrlRedirectionImpl(
                    retrieveBrowser = browser,
                    retrievePlatform = platform,
                    storeClick = click!!,
                    find = find!!,
                    dateFactory = date!!,
                    urlValidator = urlValidator!!,
                    saveUrl = saveUrl!!
            )

            When calling browser.getBrowser(any(String::class)) `it returns` "browser"
            When calling platform.getPlatform(any(String::class)) `it returns` "platform"
            When calling find!!.findUrlById(hash = eq("hash")) `it returns` su.copy()
            When calling date!!.now() `it returns` aDate
            When calling urlValidator!!.shouldCheckSafety(any(ShortUrl::class), eq(aDate)) `it returns` false
            When calling urlValidator!!.isSpam(any(String::class)) `it returns` false
        }

        given("the target is no longer safe") {
            beforeEachTest {
                val newUrl = su.copy()
                newUrl.safe = false
                When calling find!!.findUrlById(hash = eq("no-safe-hash")) `it returns` newUrl
            }

            it("should raise an exception about it") {
                val func = { sut!!.returnRedirectionWhileSavingClick(hash = "no-safe-hash", ip = "ip", userAgent = "ua") }

                func `should throw` BadRequestException::class `with message` "target is no longer safe"
            }
        }

        given("the target is safe but it needs to be reviewed") {
            val reviewDate = GregorianCalendar(2018, 12, 17).time

            beforeEachTest {
                When calling date!!.now() `it returns` reviewDate
                When calling urlValidator!!.shouldCheckSafety(any(ShortUrl::class), eq(reviewDate)) `it returns` true
            }

            it("updates safetyLastChecked on the url if it no longer is spamlisting") {
                When calling urlValidator!!.isSpam(any(String::class)) `it returns` false

                su.safetyLastChecked = reviewDate

                val response = sut!!.returnRedirectionWhileSavingClick(hash = "hash", ip = "ip", userAgent = "ua")

                Verify on saveUrl that saveUrl!!.saveUrl(su) was called
                response.safetyLastChecked `should equal` reviewDate
            }

            it("raises an exception if the url is now spamlisting") {
                When calling urlValidator!!.isSpam(any(String::class)) `it returns` true

                val func = { sut!!.returnRedirectionWhileSavingClick(hash = "hash", ip = "ip", userAgent = "ua") }

                func `should throw` BadRequestException::class `with message` "target is no longer safe"
            }
        }

        given("a specific hash") {
            it("should fetch for the correct url") {
                sut!!.returnRedirectionWhileSavingClick(hash = "hash", ip = "ip", userAgent = "ua")

                Verify on find that find!!.findUrlById(hash = "hash") was called
            }
        }
        given("all parameters are correct") {
            it("should store the correct data on click table") {
                sut!!.returnRedirectionWhileSavingClick(hash = "hash", ip = "ip", userAgent = "ua")

                Verify on click that click!!.saveClick(Click(
                        hash = "hash",
                        ip = "ip",
                        created = aDate,
                        browser = "browser",
                        platform = "platform"
                )) was called
            }
        }

    }
})