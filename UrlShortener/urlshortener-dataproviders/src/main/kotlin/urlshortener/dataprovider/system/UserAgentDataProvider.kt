package urlshortener.dataprovider.system

import nl.basjes.parse.useragent.UserAgentAnalyzer
import urlshortener.usecase.shorturl.GetBrowserFromUserAgent
import urlshortener.usecase.shorturl.GetPlatformFromUserAgent

class UserAgentDataProvider : GetPlatformFromUserAgent, GetBrowserFromUserAgent{

    private val uaParser: UserAgentAnalyzer = UserAgentAnalyzer.newBuilder()
            .hideMatcherLoadStats()
            .withCache(2500)
            .build()

    override fun getBrowserFromUserAgent(ua: String): String = uaParser.parse(ua)["AgentName"].value

    override fun getPlatformFromUserAgent(ua: String): String = uaParser.parse(ua)["OperatingSystemName"].value
}