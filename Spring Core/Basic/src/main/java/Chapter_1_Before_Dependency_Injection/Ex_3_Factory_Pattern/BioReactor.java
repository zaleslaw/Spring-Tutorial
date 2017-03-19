package Chapter_1_Before_Dependency_Injection.Ex_3_Factory_Pattern;

import Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.impl.Citizen;
import Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.impl.Joker;
import Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.impl.SuperHero;
import Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.interfaces.Human;


public class BioReactor {
    public static Human getHuman(String humanType) {
        if(humanType.equals("SuperHero")){
            return new SuperHero();
        }
        else if(humanType.equals("Villain")){
            return new Joker();
        }
        else {
            return new Citizen();
        }
    }
}
