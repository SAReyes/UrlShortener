package urlshortener.domain

import java.net.URI
import java.util.*

data class ShortUrl(val hash: String,
                    val target: String,
                    val uri: URI = URI("/$hash"),
                    val sponsor: String?,
                    val created: Date,
                    val owner: String?,
                    val mode: Int,
                    val safe: Boolean?,
                    val ip: String?,
                    val country: String?)