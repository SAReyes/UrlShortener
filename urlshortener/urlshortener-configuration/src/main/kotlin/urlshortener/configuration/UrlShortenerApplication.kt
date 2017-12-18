package urlshortener.configuration

import es.unizar.shortener.provider.database.click.ClickRepository
import es.unizar.shortener.provider.database.click.SaveClickDataProvider
import es.unizar.shortener.provider.database.shorturl.FindUrlByIdDataProvider
import es.unizar.shortener.provider.database.shorturl.SaveUrlDataProvider
import es.unizar.shortener.provider.database.shorturl.ShortUrlRepository
import es.unizar.shortener.provider.system.DateFactoryDataProvider
import es.unizar.shortener.provider.system.UrlEncoderDataProvider
import es.unizar.shortener.provider.system.UrlValidatorDataProvider
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router
import urlshortener.configuration.handler.ShortenerWebHandler
import urlshortener.configuration.util.IpRetrieverImpl
import urlshortener.usecase.shorturl.CreateAndSaveUrlImpl
import urlshortener.usecase.shorturl.ReturnRedirectionWhileSavingClickImpl

@SpringBootApplication
@EnableAutoConfiguration
class UrlShortenerApplication(private val clickRepository: ClickRepository,
                              private val shortUrlRepository: ShortUrlRepository) {

    @Bean
    fun router() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/{id}", shortenerWebHandler()::redirectTo)
            POST("/link", shortenerWebHandler()::link)
        }
    }

    @Bean
    fun shortenerWebHandler() = ShortenerWebHandler(
            createAndSaveUrl = createAndSaveUrl(),
            returnRedirectionWhileSavingClick = returnRedirectionWhileSavingClick(),
            ipRetriever = ipRetriever()
    )

    @Bean
    fun createAndSaveUrl() = CreateAndSaveUrlImpl(
            saveUrl = saveUrl(),
            urlValidator = urlValidator(),
            urlEncoder = urlEncoder(),
            dateFactory = dateFactory()
    )

    @Bean
    fun returnRedirectionWhileSavingClick() = ReturnRedirectionWhileSavingClickImpl(
            find = findUrlById(),
            storeClick = saveClick(),
            dateFactory = dateFactory()
    )

    @Bean
    fun ipRetriever() = IpRetrieverImpl()

    @Bean
    fun urlValidator() = UrlValidatorDataProvider()

    @Bean
    fun urlEncoder() = UrlEncoderDataProvider()

    @Bean
    fun dateFactory() = DateFactoryDataProvider()

    @Bean
    fun saveClick() = SaveClickDataProvider(clickRepository)

    @Bean
    fun saveUrl() = SaveUrlDataProvider(shortUrlRepository)

    @Bean
    fun findUrlById() = FindUrlByIdDataProvider(shortUrlRepository)
}

fun main(args: Array<String>) {
    SpringApplication.run(UrlShortenerApplication::class.java, *args)
}