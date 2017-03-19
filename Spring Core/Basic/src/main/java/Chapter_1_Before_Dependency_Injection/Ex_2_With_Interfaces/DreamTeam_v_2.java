package Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces;

import Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.impl.Citizen;
import Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.impl.Joker;
import Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.impl.SuperHero;
import Chapter_1_Before_Dependency_Injection.Ex_2_With_Interfaces.interfaces.Human;

import java.util.ArrayList;
import java.util.List;

public class DreamTeam_v_2 {
    public static void main(String[] args) {

        List<Human> dreamTeam = new ArrayList<Human>();
        //dreamTeam.add(new Object()); it doesn't work due to generics + interfaces
        Human citizen = new Citizen();
        Human batman = new SuperHero();
        Human joker = new Joker();

        dreamTeam.add(citizen);
        dreamTeam.add(batman);
        dreamTeam.add(joker);

        dreamTeam.forEach((Human h) -> h.speak());

    }
}
