package com.oasis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: Felix
 * @Description: 秒杀商品
 */
public class SecKill {
    private static Logger logger = LogManager.getLogger(SecKill.class);
    /**
     * 抢购时间 yyyy/MM/dd-HH:mm:ss
     */
    private static final String BUY_TIME = "2021/06/15-22:51:00:000";
    /**
     * 抢购的商品购物车页面元素id
     */
    private static final String SKU_ELEMENT_ID = "J_CheckBox_3062426698817";

    private static final char[] PASSWORD = new char[]{'*','*','*','*','*'};

    public static void main(String[] args) {
        //目标站点
        String aimUrl = "https://www.taobao.com";
        String driver = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driver);
        WebDriver webDriver = new ChromeDriver();
        /*String driver = "C:\\Program Files\\Mozilla Firefox\\browser\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", driver);
        WebDriver webDriver = new FirefoxDriver();*/
        Navigation navigation = webDriver.navigate();
        navigation.to(aimUrl);
        Window window = webDriver.manage().window();
        window.maximize();
        //登录
        webDriver.findElement(By.linkText("亲，请登录")).click();
        //webDriver.findElement(By.linkText("密码登录")).click();
            /*//1.微博登录
            webDriver.findElement(By.className("weibo-login")).click();
            webDriver.findElement(By.name("username")).sendKeys("******");
            webDriver.findElement(By.name("password")).sendKeys("******");
            webDriver.findElement(By.linkText("登录")).click();*/
            /*//2.账户密码登录
            webDriver.findElement(By.id("fm-login-id")).sendKeys("******");
            webDriver.findElement(By.id("fm-login-password")).sendKeys("******");
            webDriver.findElement(By.linkText("登录")).click();*/
        //3.支付宝扫码登录
        try {
            webDriver.findElement(By.className("alipay-login")).click();
            logger.info("==========扫码登陆==========");
            logger.info("======请在10秒内完成登录======");
            Thread.sleep(10000);
            navigation.to("https://cart.taobao.com/cart.htm");
            //webDriver.findElement(By.id("J_SelectAll1"));
            logger.info("==========选择商品==========");
            while (true) {
                /*URL url = new URL(aimUrl);
                URLConnection uc = url.openConnection();
                uc.connect();
                // 读取网站日期时间
                long ld = uc.getDate();
                // 转换为标准时间对象
                Date dateNow = new Date(ld);*/
                Date dateNow = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS");
                Date dateNeed = sdf.parse(BUY_TIME);
                logger.info("当前时间：" + sdf.format(new Date()));
                if (dateNeed.equals(dateNow) || dateNeed.before(dateNow)) {
                    webDriver.findElement(By.linkText("结 算")).click();
                    logger.info("结算时间：" + sdf.format(new Date()));
                    while (true) {
                        if (isJudgingElement(webDriver, By.linkText("提交订单"))) {
                            webDriver.findElement(By.linkText("提交订单")).click();
                            logger.info("提交订单时间：" + sdf.format(new Date()));
                            break;
                        }
                    }
/*
                    Thread.sleep(50000);
                    for (int i = 0; i < 6; i++) {
                        char c = PASSWORD[i];
                        webDriver.findElement(By.xpath("//*[@class='sixDigitPassword']/i[" + i + "]/b[" + i + "]")).sendKeys(Character.toString(PASSWORD[i]));
                    }
                    logger.info("输入支付密码时间：" + sdf.format(new Date()));

                    webDriver.findElement(By.id("J_authSubmit")).click();
                    logger.info("确认付款时间：" + sdf.format(new Date()));*/
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author Felix
     * @Description 判断当前页面是否存在元素
     * @Param
     * @Return
     */
    public static boolean isJudgingElement(WebDriver webDriver, By by) {
        try {
            webDriver.findElement(by);
            return true;
        } catch (Exception e) {
            logger.error("不存在元素" + by.toString() + ":" + new Date());
            return false;
        }
    }
}
