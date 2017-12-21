package urlshortener.dataprovider.database.click

import urlshortener.dataprovider.database.shorturl.ShortUrlEntity
import java.sql.Date
import javax.persistence.*

@Entity
data class ClickEntity(@Id @GeneratedValue(strategy = GenerationType.AUTO)
                       var id: Long?,
                       @ManyToOne(cascade = arrayOf(CascadeType.REMOVE), fetch = FetchType.LAZY)
                       @JoinColumn(name = "hash")
                       var hash: ShortUrlEntity,
                       var created: Date,
                       var referrer: String?,
                       var browser: String?,
                       var platform: String?,
                       var ip: String,
                       var country: String?) {
    constructor(): this(
            id = null,
            hash = ShortUrlEntity(hash = "", target = "", created = Date(0), safetyLastChecked = Date(0)),
            created = Date(0),
            referrer = null,
            browser = null,
            platform = null,
            ip = "0.0.0.0",
            country = null
    )
}