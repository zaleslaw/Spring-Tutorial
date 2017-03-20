package Chapter_2_Bean_Lifecycle.special_beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * Created by Alexey_Zinovyev on 20-Mar-17.
 */
public class CustomBPP implements BeanPostProcessor, Ordered {
    //before custom init method
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("Bean " + s + " START");
        System.out.println("BPP: " + o.toString());
        return o;
    }
    // after custom init method
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("Bean " + s + " END");
        System.out.println("BPP: " + o.toString());
        return o;
    }

    public int getOrder() {
        return 1;
    }
}
