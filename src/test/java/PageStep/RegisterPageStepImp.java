package PageStep;

import BaseStep.StepImplementation;
import com.thoughtworks.gauge.Step;

import java.util.Random;

public class RegisterPageStepImp extends StepImplementation {

    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    @Step("Write randomly email")
    public void mailGenerator()
    {
        String email= getSaltString()+"@gmail.com" ;
        sendKeys(email,"CREATE_ACCOUNT_EMAIL_INPUT_AREA");

    }
}
