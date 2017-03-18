package Before_Dependency_Injection.Ex_2_With_Interfaces.impl;

import Before_Dependency_Injection.Ex_2_With_Interfaces.interfaces.Human;

/**
 * Created by Alexey_Zinovyev on 18-Mar-17.
 */
public class SuperHero implements Human {
    @Override
    public void speak() {
        System.out.println("Hello, I'm SuperHero");
    }
}
