package urlshortener.configuration.util

import org.springframework.web.reactive.function.server.ServerRequest
import java.net.URI

interface IpRetriever {
    fun getIp(sReq: ServerRequest): String

    fun getUri(sReq: ServerRequest): URI
}