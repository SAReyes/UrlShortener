package urlshortener.dataprovider.system

import nl.basjes.parse.useragent.UserAgentAnalyzer
import urlshortener.usecase.shorturl.GetBrowser
import urlshortener.usecase.shorturl.GetPlatform

class UserAgentDataProvider : GetPlatform, GetBrowser {

    private val uaParser: UserAgentAnalyzer = UserAgentAnalyzer.newBuilder()
            .hideMatcherLoadStats()
            .withCache(2500)
            .build()

    override fun getBrowser(ua: String): String = uaParser.parse(ua)["AgentName"].value

    override fun getPlatform(ua: String): String = uaParser.parse(ua)["OperatingSystemName"].value
}