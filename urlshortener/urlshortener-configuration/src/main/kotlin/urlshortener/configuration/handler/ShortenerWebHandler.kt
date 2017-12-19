package urlshortener.configuration.handler

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.core.publisher.doOnError
import reactor.core.publisher.toMono
import urlshortener.configuration.exception.BadRequestFluxException
import urlshortener.configuration.exception.NotFoundFluxException
import urlshortener.configuration.model.SaveRequest
import urlshortener.configuration.util.RequestHelper
import urlshortener.usecase.exception.BadRequestException
import urlshortener.usecase.exception.NotFoundException
import urlshortener.usecase.shorturl.CreateAndSaveUrl
import urlshortener.usecase.shorturl.RetrieveUrlRedirection
import java.util.*

@Component
class ShortenerWebHandler(private val createAndSaveUrl: CreateAndSaveUrl,
                          private val retrieveUrlRedirection: RetrieveUrlRedirection,
                          private val requestHelper: RequestHelper) {

    fun redirectTo(sReq: ServerRequest): Mono<ServerResponse> = sReq.toMono()
            .doOnError(NotFoundException::class) { throw NotFoundFluxException(it) }
            .map {
                retrieveUrlRedirection.returnRedirectionWhileSavingClick(
                        hash = it.pathVariable("id"),
                        ip = requestHelper.getIp(it),
                        userAgent = requestHelper.getUserAgent(it)
                )
            }
            .flatMap {
                ServerResponse.status(HttpStatus.valueOf(it.mode))
                        .header("location", it.target)
                        .body(it.toMono())
            }

    fun link(sReq: ServerRequest): Mono<ServerResponse> {
        return sReq.bodyToMono(SaveRequest::class.java)
                .doOnError(BadRequestException::class) { throw BadRequestFluxException(it) }
                .map {
                    createAndSaveUrl.createAndSaveUrl(
                            targetUrl = it.url,
                            domainUri = requestHelper.getUri(sReq),
                            sponsor = it.sponsor,
                            owner = UUID.randomUUID().toString(),
                            mode = HttpStatus.TEMPORARY_REDIRECT.value(),
                            ip = requestHelper.getIp(sReq)
                    )
                }
                .flatMap {
                    ServerResponse.created(it.uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(it.toMono())
                }
    }
}
