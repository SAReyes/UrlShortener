package urlshortener.dataprovider.database.shorturl

import java.sql.Date
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ShortUrlEntity(@Id
                          var hash: String,
                          var target: String,
                          var sponsor: String? = null,
                          var created: Date,
                          var owner: String? = null,
                          var mode: Int = 500,
                          var safe: Boolean? = null,
                          var ip: String? = null,
                          var country: String? = null,
                          var safetyLastChecked: Date) {
    constructor() : this(hash = "", target = "", created = Date(0), safetyLastChecked = Date(0))
}
