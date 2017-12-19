package urlshortener.dataprovider.system

import nl.basjes.parse.useragent.UserAgentAnalyzer
import urlshortener.usecase.shorturl.RetrieveBrowser
import urlshortener.usecase.shorturl.RetrievePlatform

class UserAgentDataProvider : RetrievePlatform, RetrieveBrowser {

    private val uaParser: UserAgentAnalyzer = UserAgentAnalyzer.newBuilder()
            .hideMatcherLoadStats()
            .withCache(2500)
            .build()

    override fun getBrowser(ua: String): String = uaParser.parse(ua)["AgentName"].value

    override fun getPlatform(ua: String): String = uaParser.parse(ua)["OperatingSystemName"].value
}