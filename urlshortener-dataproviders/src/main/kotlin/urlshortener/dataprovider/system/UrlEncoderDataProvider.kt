package urlshortener.dataprovider.system

import com.google.common.hash.Hashing
import urlshortener.usecase.shorturl.UrlEncoder
import java.nio.charset.StandardCharsets

class UrlEncoderDataProvider : urlshortener.usecase.shorturl.UrlEncoder {
    override fun encode(url: String): String = com.google.common.hash.Hashing.murmur3_32().hashString(url, java.nio.charset.StandardCharsets.UTF_8).toString()
}