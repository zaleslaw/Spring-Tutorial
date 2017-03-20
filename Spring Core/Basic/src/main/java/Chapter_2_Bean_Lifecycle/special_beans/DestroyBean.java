package Chapter_2_Bean_Lifecycle.special_beans;

import org.springframework.beans.factory.DisposableBean;

/**
 * Created by Alexey_Zinovyev on 20-Mar-17.
 */
public class DestroyBean implements DisposableBean {
    public void destroy() throws Exception {
        System.out.println("Old disposable bean is here!");
    }
}
