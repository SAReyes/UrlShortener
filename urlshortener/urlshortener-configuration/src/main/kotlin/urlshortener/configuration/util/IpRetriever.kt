package urlshortener.configuration.util

import org.springframework.web.reactive.function.server.ServerRequest

interface IpRetriever {
    fun getIp(sReq: ServerRequest): String
}