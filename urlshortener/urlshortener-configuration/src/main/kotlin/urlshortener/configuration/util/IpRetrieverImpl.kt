package urlshortener.configuration.util

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.adapter.DefaultServerWebExchange
import java.net.URI

class IpRetrieverImpl : IpRetriever {
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