package utils.framework;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import utils.api.Authorization;

public class SuiteListener implements ISuiteListener {
    public void onStart(ISuite suite) {
        Authorization.loginToJIRA();
    }

    public void onFinish(ISuite suite) {

    }
}
