package PageStep;

import BaseStep.StepImplementation;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.Cookie;

import java.util.Set;

public class HomePageStepImp extends StepImplementation
{

    @Step("Delete all cookies")
    public void deleteAllCookies(){

        Set<Cookie> allCookies = driver.manage().getCookies();
        for (Cookie cookie : allCookies) {
            driver.manage().deleteCookieNamed(cookie.getName());
        }
}}
