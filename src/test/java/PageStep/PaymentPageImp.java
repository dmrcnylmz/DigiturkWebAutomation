package PageStep;

import BaseStep.StepImplementation;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentPageImp extends StepImplementation {

    @Step("Selecth month")
    public void selectMonth( ){
        Select s = new Select(new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("Ecom_Payment_Card_ExpDate_Month"))));
        s.selectByIndex(1);

    }
    @Step("Selecth year")
    public void selectYear(){
        Select s = new Select(new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.id("Ecom_Payment_Card_ExpDate_Year"))));
        s.selectByIndex(2);

    }
}
