package urlshortener.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router
import urlshortener.configuration.handler.ShortenerWebHandler
import urlshortener.configuration.util.RequestHelperImpl
import urlshortener.dataprovider.database.click.ClickRepository
import urlshortener.dataprovider.database.click.SaveClickDataProvider
import urlshortener.dataprovider.database.shorturl.FindUrlByIdDataProvider
import urlshortener.dataprovider.database.shorturl.SaveUrlDataProvider
import urlshortener.dataprovider.database.shorturl.ShortUrlRepository
import urlshortener.dataprovider.system.DateFactoryDataProvider
import urlshortener.dataprovider.system.UrlEncoderDataProvider
import urlshortener.dataprovider.system.UrlValidatorDataProvider
import urlshortener.dataprovider.system.UserAgentDataProvider
import urlshortener.usecase.shorturl.CreateAndSaveUrlImpl
import urlshortener.usecase.shorturl.ReturnRedirectionWhileSavingClickImpl

@SpringBootApplication
@EnableAutoConfiguration
class UrlShortenerApplication(private val clickRepository: ClickRepository,
                              private val shortUrlRepository: ShortUrlRepository) {

    @Value("\${urlshortener.qa-api}")
    private val qaApi: String = "https://chart.googleapis.com/chart?cht=qr&chs=250x250&chl="

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
            requestHelper = ipRetriever()
    )

    @Bean
    fun createAndSaveUrl() = CreateAndSaveUrlImpl(
            saveUrl = saveUrl(),
            urlValidator = urlValidator(),
            urlEncoder = urlEncoder(),
            dateFactory = dateFactory(),
            qrApi = qaApi
    )

    @Bean
    fun returnRedirectionWhileSavingClick() = ReturnRedirectionWhileSavingClickImpl(
            find = findUrlById(),
            storeClick = saveClick(),
            dateFactory = dateFactory(),
            getBrowserFromUserAgent = userAgentProvider(),
            getPlatformFromUserAgent = userAgentProvider()
    )

    @Bean
    fun userAgentProvider() = UserAgentDataProvider()

    @Bean
    fun ipRetriever() = RequestHelperImpl()

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