package urlshortener.configuration.handler

import com.nhaarman.mockito_kotlin.eq
import org.amshove.kluent.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import org.springframework.test.web.reactive.server.returnResult
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.toMono
import reactor.test.expectError
import reactor.test.test
import urlshortener.configuration.exception.BadRequestFluxException
import urlshortener.configuration.exception.NotFoundFluxException
import urlshortener.configuration.model.SaveRequest
import urlshortener.configuration.util.RequestHelper
import urlshortener.domain.ShortUrl
import urlshortener.usecase.exception.BadRequestException
import urlshortener.usecase.exception.NotFoundException
import urlshortener.usecase.shorturl.CreateAndSaveUrl
import urlshortener.usecase.shorturl.ListClicks
import urlshortener.usecase.shorturl.ListUrls
import urlshortener.usecase.shorturl.RetrieveUrlRedirection
import java.net.URI
import java.util.*

object ShortenerHandlerSpecs : Spek({
    describe("Shortener web handler") {
        val createAndSaveUrl = mock(CreateAndSaveUrl::class)
        val returnRedirectionWhileSavingClick = mock(RetrieveUrlRedirection::class)
        val requestHelper = mock(RequestHelper::class)
        val listUrls = mock(ListUrls::class)
        val listClicks = mock(ListClicks::class)

        val sut = ShortenerHandler(
                createAndSaveUrl = createAndSaveUrl,
                retrieveUrlRedirection = returnRedirectionWhileSavingClick,
                requestHelper = requestHelper,
                listUrls = listUrls,
                listClicks = listClicks
        )

        val routerFunction = router {
            GET("/{id}", sut::redirectTo)
            POST("/link", sut::link)
        }

        val client = WebTestClient.bindToRouterFunction(routerFunction).build()

        given("a redirection exists") {
            val aDate = GregorianCalendar(2017, 12, 17).time

            When calling returnRedirectionWhileSavingClick
                    .returnRedirectionWhileSavingClick("redirectTo", "127.0.0.1", "ua") `it returns`
                    ShortUrl(
                            hash = "redirectTo",
                            target = "https://google.es",
                            sponsor = null,
                            created = aDate,
                            owner = null,
                            mode = 307,
                            safe = true,
                            ip = null,
                            country = null,
                            qr = URI("/qr"),
                            uri = URI("http://localhost/redirectTo"),
                            safetyLastChecked = aDate
                    )

            When calling requestHelper.getIp(any(ServerRequest::class)) `it returns` "127.0.0.1"
            When calling requestHelper.getUserAgent(any(ServerRequest::class)) `it returns` "ua"

            it("should relocate the client to the target") {
                client.get()
                        .uri("/redirectTo")
                        .exchange()
                        .expectHeader()
                        .valueMatches("location", "https://google.es")
                        .expectStatus()
                        .isTemporaryRedirect
            }
        }

        given("a redirection does not exists") {
            When calling returnRedirectionWhileSavingClick
                    .returnRedirectionWhileSavingClick(eq("error"), any(String::class), eq("ua")) `it throws`
                    NotFoundException("not found")

            it("should return NotFoundFluxException") {
                client.get()
                        .uri("/error")
                        .exchange()
                        .returnResult<NotFoundFluxException>()
                        .responseBody
                        .test()
                        .expectError(NotFoundFluxException::class)

            }
        }


        given("the user wants to create a link reference") {
            val aDate = GregorianCalendar(2017, 12, 17).time
            val uri = URI("http://localhost")

            When calling requestHelper.getUri(any(ServerRequest::class)) `it returns` uri

            When calling createAndSaveUrl.createAndSaveUrl(
                    targetUrl = eq("a-target"),
                    domainUri = eq(uri),
                    sponsor = eq("sponsor"),
                    owner = any(String::class),
                    mode = any(Int::class),
                    ip = any(String::class),
                    safe = any(Boolean::class),
                    country = eq(null)
            ) `it returns` ShortUrl(
                    hash = "a-hash",
                    target = "http://google.es",
                    sponsor = "sponsor",
                    created = aDate,
                    owner = "owner",
                    mode = 200,
                    safe = true,
                    ip = "ip",
                    country = null,
                    uri = URI("http://localhost/a-hash"),
                    qr = URI(""),
                    safetyLastChecked = aDate
            )

            it("should respond with the correct data") {
                client.post()
                        .uri("/link")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .body(SaveRequest("a-target", "sponsor").toMono())
                        .exchange()
                        .expectStatus().isCreated
                        .expectBody()
                        .jsonPath("$.uri").isEqualTo("http://localhost/a-hash")
                        .jsonPath("$.hash").isEqualTo("a-hash")
                        .jsonPath("$.target").isEqualTo("http://google.es")
            }
        }

        given("an error is thrown while creating a url") {
            When calling createAndSaveUrl.createAndSaveUrl(
                    targetUrl = eq("error-target"),
                    sponsor = eq("sponsor"),
                    owner = any(String::class),
                    mode = any(Int::class),
                    ip = any(String::class),
                    safe = any(Boolean::class),
                    country = eq(null),
                    domainUri = eq(URI("http://localhost"))
            ) `it throws` BadRequestException("bad request")

            it("should return BadRequestFluxException") {
                client.post()
                        .uri("/link")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .body(SaveRequest("error-target", "sponsor").toMono())
                        .exchange()
                        .returnResult<BadRequestFluxException>()
                        .responseBody
                        .test()
                        .expectError(BadRequestFluxException::class)

            }
        }
    }
})