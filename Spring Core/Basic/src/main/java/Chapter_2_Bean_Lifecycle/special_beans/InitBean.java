package Chapter_2_Bean_Lifecycle.special_beans;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by Alexey_Zinovyev on 20-Mar-17.
 */
public class InitBean implements InitializingBean {
    public void afterPropertiesSet() throws Exception {
        System.out.println("After properties set() was called");
    }
}
