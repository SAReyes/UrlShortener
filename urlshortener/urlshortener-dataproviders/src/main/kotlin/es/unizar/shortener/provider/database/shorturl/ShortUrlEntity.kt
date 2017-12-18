package es.unizar.shortener.provider.database.shorturl

@javax.persistence.Entity
data class ShortUrlEntity(@javax.persistence.Id
                          var hash: String,
                          var target: String,
                          var sponsor: String? = null,
                          var created: java.sql.Date,
                          var owner: String? = null,
                          var mode: Int = 500,
                          var safe: Boolean? = null,
                          var ip: String? = null,
                          var country: String? = null) {
    constructor(): this(hash = "", target = "", created = java.sql.Date(0))
}
