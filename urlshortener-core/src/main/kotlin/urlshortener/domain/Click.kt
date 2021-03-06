package urlshortener.domain

import java.util.*

data class Click(val id: Long? = null,
                 val hash: String,
                 val created: Date,
                 val referrer: String? = null,
                 val browser: String,
                 val platform: String,
                 val ip: String,
                 val country: String? = null)