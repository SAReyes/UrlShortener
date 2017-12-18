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
import urlshortener.configuration.util.IpRetriever
import urlshortener.usecase.exception.BadRequestException
import urlshortener.usecase.exception.NotFoundException
import urlshortener.usecase.shorturl.CreateAndSaveUrl
import urlshortener.usecase.shorturl.ReturnRedirectionWhileSavingClick
import java.util.*

@Component
class ShortenerWebHandler(private val createAndSaveUrl: CreateAndSaveUrl,
                          private val returnRedirectionWhileSavingClick: ReturnRedirectionWhileSavingClick,
                          private val ipRetriever: IpRetriever) {

    fun redirectTo(sReq: ServerRequest): Mono<ServerResponse> = sReq.toMono()
            .doOnError(NotFoundException::class) { throw NotFoundFluxException(it) }
            .map { returnRedirectionWhileSavingClick.returnRedirectionWhileSavingClick(it.pathVariable("id"), getIp(it)) }
            .flatMap { su ->
                ServerResponse.status(HttpStatus.valueOf(su.mode))
                        .header("location", su.target)
                        .body(su.toMono())
            }

    fun link(sReq: ServerRequest): Mono<ServerResponse> {
        return sReq.bodyToMono(SaveRequest::class.java)
                .doOnError(BadRequestException::class) { throw BadRequestFluxException(it) }
                .map { req ->
                    createAndSaveUrl.createAndSaveUrl(
                            targetUrl = req.url,
                            sponsor = req.sponsor,
                            owner = UUID.randomUUID().toString(),
                            mode = HttpStatus.TEMPORARY_REDIRECT.value(),
                            ip = getIp(sReq)
                    )
                }
                .flatMap { su ->
                    ServerResponse.created(su.uri)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(su.toMono())
                }
    }

    private fun getIp(sReq: ServerRequest): String = ipRetriever.getIp(sReq)
}
