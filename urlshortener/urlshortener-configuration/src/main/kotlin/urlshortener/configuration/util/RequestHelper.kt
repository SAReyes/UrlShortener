package urlshortener.configuration.util

import nl.basjes.parse.useragent.UserAgent
import org.springframework.web.reactive.function.server.ServerRequest
import java.net.URI

interface RequestHelper {
    fun getUserAgent(sReq: ServerRequest): String

    fun getIp(sReq: ServerRequest): String

    fun getUri(sReq: ServerRequest): URI
}