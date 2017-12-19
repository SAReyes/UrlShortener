package urlshortener.configuration.util

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.server.adapter.DefaultServerWebExchange

class IpRetrieverImpl : IpRetriever {
    override fun getIp(sReq: ServerRequest): String {
        val f = sReq.javaClass.getDeclaredField("exchange")
        f.isAccessible = true
        return (f.get(sReq) as DefaultServerWebExchange).request.remoteAddress!!.address.hostAddress
    }
}