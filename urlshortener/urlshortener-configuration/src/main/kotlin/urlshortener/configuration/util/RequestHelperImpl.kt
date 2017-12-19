package urlshortener.configuration.util

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.adapter.DefaultServerWebExchange
import urlshortener.usecase.exception.BadRequestException
import java.net.URI

class RequestHelperImpl : RequestHelper {

    override fun getUserAgent(sReq: ServerRequest): String {
        val uas = sReq.headers().header("User-Agent")

        if(uas.size != 1) {
            throw BadRequestException("found multiple, or none, user agents")
        }

        return uas.first()
    }

    override fun getIp(sReq: ServerRequest): String {
        val f = sReq.javaClass.getDeclaredField("exchange")
        f.isAccessible = true
        val ex = (f.get(sReq) as DefaultServerWebExchange)
        return ex.request.remoteAddress!!.address.hostAddress
    }

    override fun getUri(sReq: ServerRequest): URI {
        val ex = defaultServerWebExchange(sReq)
        return ex.request.uri
    }

    private fun defaultServerWebExchange(sReq: ServerRequest): DefaultServerWebExchange {
        val f = sReq.javaClass.getDeclaredField("exchange")
        f.isAccessible = true
        return (f.get(sReq) as DefaultServerWebExchange)
    }
}