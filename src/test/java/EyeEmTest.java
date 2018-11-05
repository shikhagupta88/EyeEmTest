import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class EyeEmTest {


    private AppiumDriver<MobileElement> driver;
    private WebDriverWait webDriverWait;


    private static final String DEVICE_NAME_VALUE = "Nexus 5X";
    private static final String UDID_VALUE = "0253921df3701265";
    private static final String PLATFORM_NAME_VALUE = "Android";
    private static final String PLATFORM_VERSION_VALUE = "8.1.0";
    private static final boolean SKIP_UNLOCK_VALUE = true;
    private static final String APP_PACKAGE_VALUE = "com.baseapp.eyeem";
    private static final String APP_ACTIVITY_VALUE = "com.baseapp.eyeem.Start";
    private static final boolean NO_RESET_VALUE = false;

    private String parthnerLogoAirBnb = "//android.widget.ImageView[@content-desc=\"brands onboarding view - first logo\"]";
    private String parthnerLogoGettyImages = "//android.widget.ImageView[@content-desc=\"brands onboarding view - second logo\"]";
    private String parthnerLogoSpotify = "//android.widget.ImageView[@content-desc=\"brands onboarding view - third logo\"]";


    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", DEVICE_NAME_VALUE);
        caps.setCapability("udid", UDID_VALUE); //DeviceId from "adb devices" command
        caps.setCapability("platformName", PLATFORM_NAME_VALUE);
        caps.setCapability("platformVersion", PLATFORM_VERSION_VALUE);
        caps.setCapability("skipUnlock", SKIP_UNLOCK_VALUE);
        caps.setCapability("appPackage", APP_PACKAGE_VALUE);
        caps.setCapability("appActivity", APP_ACTIVITY_VALUE);
        caps.setCapability("noReset", NO_RESET_VALUE);
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
        webDriverWait = new WebDriverWait(driver, 10);
    }


    @Test
    public void test_lastOnboardingView_ShouldContain_PartnerLogos() {

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("com.baseapp.eyeem:id/pager")));


        // Scroll until we found the first view we'd like to assert
        while (false == driver.findElements(By.xpath(parthnerLogoAirBnb)).size() > 0) {
            scrollRightToLeft();
        }

        // Assert that all partner logos exists
        driver.findElement(By.xpath(parthnerLogoAirBnb)).isDisplayed();
        driver.findElement(By.xpath(parthnerLogoGettyImages)).isDisplayed();
        driver.findElement(By.xpath(parthnerLogoSpotify)).isDisplayed();

    }

    // Generic methods to swipe to next screen in view pager
    public void scrollRightToLeft() {
        Dimension dimension = driver.manage().window().getSize();
        int xStart = (int) (dimension.width * 0.90);
        int xEnd = (int) (dimension.width * 0.10);
        int y = dimension.height / 2;
        driver.swipe(xStart, y, xEnd, y, 2000);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

}
