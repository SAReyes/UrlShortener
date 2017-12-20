package urlshortener.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router
import urlshortener.configuration.handler.ShortenerHandler
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
import urlshortener.dataprovider.system.spam.SpamHausSpamChecker
import urlshortener.dataprovider.system.spam.SurblSpamChecker
import urlshortener.dataprovider.system.spam.UriblSpamChecker
import urlshortener.usecase.shorturl.CreateAndSaveUrlImpl
import urlshortener.usecase.shorturl.RetrieveUrlRedirectionImpl
import java.util.*

@SpringBootApplication
@EnableAutoConfiguration
class ShortenerApp(private val clickRepository: ClickRepository,
                   private val shortUrlRepository: ShortUrlRepository) {

    @Value("\${urlshortener.qa-api}")
    private val qaApi: String = "https://chart.googleapis.com/chart?cht=qr&chs=250x250&chl="

    @Value("\${urlshortener.safety-date-limit}")
    private val safetyDateLimitString: String = "10"

    @Bean
    fun router() = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/{id}", shortenerWebHandler()::redirectTo)
            POST("/link", shortenerWebHandler()::link)
        }
    }

    @Bean
    fun shortenerWebHandler() = ShortenerHandler(
            createAndSaveUrl = createAndSaveUrl(),
            retrieveUrlRedirection = returnRedirectionWhileSavingClick(),
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
    fun returnRedirectionWhileSavingClick() = RetrieveUrlRedirectionImpl(
            find = findUrlById(),
            storeClick = saveClick(),
            dateFactory = dateFactory(),
            retrieveBrowser = userAgentProvider(),
            retrievePlatform = userAgentProvider(),
            saveUrl = saveUrl(),
            urlValidator = urlValidator()
    )

    @Bean
    fun userAgentProvider() = UserAgentDataProvider()

    @Bean
    fun ipRetriever() = RequestHelperImpl()

    @Bean
    fun urlValidator(): UrlValidatorDataProvider {
        return UrlValidatorDataProvider(
                spamCheckers = arrayListOf(SpamHausSpamChecker(), SurblSpamChecker(), UriblSpamChecker()),
                dateFactory = dateFactory(),
                safetyUrlDateLimit = Date(safetyDateLimitString.toLong() * 24 * 60 * 60 * 1000)
        )
    }

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
    SpringApplication.run(ShortenerApp::class.java, *args)
}