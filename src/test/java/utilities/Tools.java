package utilities;

import io.appium.java_client.windows.WindowsDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;



public class Tools {

    public static WindowsDriver driver= null;



    public static void selectApplicationFromTaskbar(String applicationName) throws InterruptedException, MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Root");
        WindowsDriver newDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
        Actions actions = new Actions(newDriver);
        actions.contextClick(newDriver.findElement(By.name(applicationName))).build().perform();
        Thread.sleep(2000);
        newDriver.findElementByAccessibilityId("Open").click();
        newDriver.quit();
    }




    public static boolean isSimilarImage(BufferedImage actualImage, BufferedImage expectedImage) {
        double percentage = 1000;
        int w1 = actualImage.getWidth();
        int w2 = expectedImage.getWidth();
        int h1 = actualImage.getHeight();
        int h2 = expectedImage.getHeight();
        if ((w1 != w2) || (h1 != h2)) {
            System.out.println("Both images should have same dimensions");
        } else {
            long diff = 0;
            for (int j = 0; j < h1; j++) {
                for (int i = 0; i < w1; i++) {
                    //Getting the RGB values of a pixel
                    int pixel1 = actualImage.getRGB(i, j);
                    Color color1 = new Color(pixel1, true);
                    int r1 = color1.getRed();
                    int g1 = color1.getGreen();
                    int b1 = color1.getBlue();
                    int pixel2 = expectedImage.getRGB(i, j);
                    Color color2 = new Color(pixel2, true);
                    int r2 = color2.getRed();
                    int g2 = color2.getGreen();
                    int b2 = color2.getBlue();
                    //sum of differences of RGB values of the two images
                    long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                    diff = diff + data;
                }
            }
            double avg = diff / (w1 * h1 * 3);
            percentage = (avg / 255) * 100;
            //System.out.println("Difference: " + percentage);
        }
        if (percentage > 0.10) {
            return false;
        } else return true;
    }

    public static void maximizeScreen() throws InterruptedException {
        driver.findElement(By.name("System")).click();
        driver.findElement(By.name("Maximize")).click();
        Thread.sleep(3000);
    }

    public static void quitApplication(String applicationNameOnTaskbar, String quitElementName) throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("app", "Root");
        driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), desiredCapabilities);
        Actions actions = new Actions(driver);
        actions.contextClick(driver.findElement(By.name(applicationNameOnTaskbar))).build().perform();
        Thread.sleep(500);
        driver.findElement(By.name(quitElementName)).click();
    }

    public static void startApplication(String applicationPath){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("app", applicationPath);
        desiredCapabilities.setCapability("platformName", "Windows");
        desiredCapabilities.setCapability("deviceName", "WindowsPC");
        try {

            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), desiredCapabilities);
            //driver = new WindowsDriver<WindowsElement>(new URL("http://127.0.0.1:4723"), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public static BufferedImage takePartialScreenshot(String imageName, int xStartPoint, int yStartPoint, int screenshotWidth, int screenshotHeight) throws IOException {
        File Screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage Image = ImageIO.read(Screenshot);
        BufferedImage ImagePartial = Image.getSubimage(xStartPoint,yStartPoint,screenshotWidth,screenshotHeight);
        ImageIO.write(ImagePartial, "png", Screenshot);
        FileUtils.copyFile(Screenshot, new File(System.getProperty("user.dir")+"\\src\\test\\resources\\images\\"+imageName+".png"));
        return ImagePartial;
    }

    public static BufferedImage takeFullScreenshot(String imageName) throws IOException {
        File Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(Screenshot, new File(System.getProperty("user.dir") + "\\src\\test\\resources\\images\\"+imageName+".png"));
        return ImageIO.read(Screenshot);
    }
    public static void clickOnCoordinate(int xCoordinate, int yCoordinate) throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.mouseMove(xCoordinate,yCoordinate);
        Thread.sleep(500);
        Actions actions = new Actions(driver);
        actions.click().build().perform();
    }


}
