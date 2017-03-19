package Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.impl;

import Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.interfaces.Human;

/**
 * Created by Alexey_Zinovyev on 18-Mar-17.
 */
public class Joker implements Human {
    @Override
    public void speak() {
        System.out.println("Hey, I can treat somebody");
    }
}
