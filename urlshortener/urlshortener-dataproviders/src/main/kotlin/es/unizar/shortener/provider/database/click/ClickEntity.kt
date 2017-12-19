package es.unizar.shortener.provider.database.click

import es.unizar.shortener.provider.database.shorturl.ShortUrlEntity


@javax.persistence.Entity
data class ClickEntity(@javax.persistence.Id @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
                       var id: Long?,
                       @javax.persistence.ManyToOne(cascade = arrayOf(javax.persistence.CascadeType.REMOVE), fetch = javax.persistence.FetchType.LAZY)
                       @javax.persistence.JoinColumn(name = "hash")
                       var hash: ShortUrlEntity,
                       var created: java.sql.Date,
                       var referrer: String?,
                       var browser: String?,
                       var platform: String?,
                       var ip: String,
                       var country: String?)