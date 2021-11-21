package net.peihuan.tieba_tool.task

import io.github.bonigarcia.wdm.WebDriverManager
import mu.KotlinLogging
import net.peihuan.tieba_tool.persistent.po.TiebaConfigPO
import net.peihuan.tieba_tool.persistent.service.TiebaConfigService
import org.openqa.selenium.By
import org.openqa.selenium.Cookie
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.boot.CommandLineRunner
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import javax.annotation.PostConstruct
import kotlin.random.Random
import kotlin.system.measureTimeMillis


@Component
class ReplyTask(
    private val tiebaConfigService: TiebaConfigService
) {

    private val log = KotlinLogging.logger {}


    var successCount = 0

    @Scheduled(fixedDelay = 1000000)
    fun run() {
        val path = "webdriver.chrome.driver"
        if (System.getProperty(path) == null) {
            System.setProperty(path, "/Users/peihuan/Downloads/chromedriver")
        } else {
            log.info { "$path 路径为： ${System.getProperty(path)}" }
        }


        while (true) {
            try {
                val allCards = tiebaConfigService.getAllCards()
                val allCookies = tiebaConfigService.getAllCookies()
                if(allCookies.isEmpty() || allCards.isEmpty()) {
                    log.info { "没有 cookie 或帖子， 等待" }
                }
                val cookie = allCookies.random()

                for (card in allCards) {
                    reply(card, cookie, "dd")
                    successCount++
                    log.info("成功 $successCount 次")
                    Thread.sleep(Random(0).nextLong(60_000) + 10_000)
                }
            } catch (e: Exception) {
                log.error(e.message, e)
            }
            Thread.sleep(60_000)
        }
    }

    private fun reply(card: TiebaConfigPO, cookie: String, content: String) {
        // WebDriverManager.chromedriver().setup()

        val driver = ChromeDriver()
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)

        var measureTimeMillis = measureTimeMillis { driver.get(card.content) }

        log.info { "====== 加载 ${card.desc} 完成，耗时 ${measureTimeMillis / 1000} 秒" }
        driver.manage().deleteAllCookies()

        val cookies: List<Cookie> = parseCookies(cookie)
        for (c in cookies) {
            driver.manage().addCookie(c)
        }
        log.info("====== cookie 设置完成，即将刷新")
        measureTimeMillis = measureTimeMillis { driver.navigate().refresh() }
        log.info("====== 刷新完成，耗时 ${measureTimeMillis / 1000} 秒")
        driver.findElement(By.id("ueditor_replace")).click()
        val delay: Long = 5000
        log.info("====== 等待 ${delay/1000} 秒后开始输入回复内容 $content")
        Thread.sleep(delay)
        driver.findElement(By.id("ueditor_replace")).sendKeys(content)
        driver.findElement(By.linkText("发 表")).click()
        log.info("===== 点击发表了，等待 ${delay/1000} 秒后关闭浏览器")
        Thread.sleep(delay)
        driver.quit()
        log.info("===== ${card.desc} 关闭浏览器，回帖成功")
    }

    fun parseCookies(cookies: String): List<Cookie> {
        val cookieList: MutableList<Cookie> = ArrayList()
        val cookiePattern = Pattern.compile("([^=]+)=([^\\;]*);?\\s?")
        val matcher = cookiePattern.matcher(cookies)
        while (matcher.find()) {
            val cookieKey = matcher.group(1)
            val cookieValue = matcher.group(2)
            val cookie = Cookie(cookieKey, cookieValue)
            cookieList.add(cookie)
        }
        return cookieList
    }

}