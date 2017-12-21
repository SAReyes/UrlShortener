package urlshortener.domain

import java.net.URI
import java.util.*

data class ShortUrl(val hash: String,
                    val target: String,
                    val uri: URI? = null,
                    val sponsor: String?,
                    val created: Date,
                    val owner: String?,
                    val mode: Int,
                    var safe: Boolean,
                    var safetyLastChecked: Date,
                    val ip: String?,
                    val country: String?,
                    val qr: URI? = null)
