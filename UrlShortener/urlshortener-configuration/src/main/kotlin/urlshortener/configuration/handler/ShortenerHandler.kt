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
class ShortenerHandler(private val createAndSaveUrl: CreateAndSaveUrl,
                       private val retrieveUrlRedirection: RetrieveUrlRedirection,
                       private val requestHelper: RequestHelper) {

    fun redirectTo(sReq: ServerRequest): Mono<ServerResponse> = sReq.toMono()
            .map { req ->
                retrieveUrlRedirection.returnRedirectionWhileSavingClick(
                        hash = req.pathVariable("id"),
                        ip = requestHelper.getIp(req),
                        userAgent = requestHelper.getUserAgent(req)
                )
            }
            .flatMap { su ->
                ServerResponse.status(HttpStatus.valueOf(su.mode))
                        .header("location", su.target)
                        .body(su.toMono())
            }
            .doOnError(NotFoundException::class) { throw NotFoundFluxException(it) }

    fun link(sReq: ServerRequest): Mono<ServerResponse> {
        return sReq.bodyToMono(SaveRequest::class.java)
                .map { req ->
                    createAndSaveUrl.createAndSaveUrl(
                            targetUrl = req.url,
                            domainUri = requestHelper.getUri(sReq),
                            sponsor = req.sponsor,
                            owner = UUID.randomUUID().toString(),
                            mode = HttpStatus.TEMPORARY_REDIRECT.value(),
                            ip = requestHelper.getIp(sReq)
                    )
                }
                .flatMap { su ->
                    ServerResponse.created(su.uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(su.toMono())
                }
                .doOnError(BadRequestException::class) { throw BadRequestFluxException(it) }
    }
}
