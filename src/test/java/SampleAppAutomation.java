import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.Tools;

import java.awt.*;
import java.net.MalformedURLException;

public class SampleAppAutomation extends Tools {

    @BeforeMethod
    public void runApplication() {
        startApplication("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
    }

    @AfterMethod
    public void closeApplication() throws MalformedURLException, InterruptedException {
        quitApplication("Firefox - 1 running window", "Close all windows");
    }

    @Test
    public void clearCacheAndCookie() throws InterruptedException, AWTException {
        Actions action = new Actions(driver);
        driver.findElementByAccessibilityId("urlbar-input").sendKeys("winAppDriver");
        action.sendKeys(Keys.ENTER).build().perform();
        Thread.sleep(500);
        if(driver.findElementsByName("Accept all").size()!=0){
            driver.findElementByName("Accept all").click();
        }else if(driver.findElementsByName("I agree").size()!=0){
            driver.findElementByName("I agree").click();
        }
        driver.findElementByName("microsoft/WinAppDriver: Windows Application Driver - GitHub https://github.com › microsoft › WinAppDriver").click();
        Thread.sleep(1000);
        action.contextClick(driver.findElementByName("Code")).build().perform();
        Thread.sleep(1000);
        action.sendKeys("q").build().perform();
        String elementText = driver.findElementByName("Code").getText();
        System.out.println("elementText = " + elementText);
        action.keyDown(Keys.CONTROL).sendKeys("f").keyUp(Keys.CONTROL).sendKeys(Keys.ENTER).build().perform();
        driver.findElementByAccessibilityId("inspector-searchbox").sendKeys("//*[.='"+elementText+"']");
        action.sendKeys(Keys.ENTER).build().perform();
        Thread.sleep(500);
        action.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).build().perform();
        driver.findElementByAccessibilityId("PanelUI-menu-button").click();
        Thread.sleep(500);
        driver.findElementByAccessibilityId("appMenu-settings-button").click();

        clickOnCoordinate(206, 21);
        driver.findElementByAccessibilityId("category-privacy").click();
        Thread.sleep(500);
        driver.findElementByAccessibilityId("clearSiteDataButton").click();
        Thread.sleep(500);
        driver.findElementByName("Clear").click();
        Thread.sleep(500);
        driver.findElementByName("Clear Now").click();
        Thread.sleep(1000);
    }
}
