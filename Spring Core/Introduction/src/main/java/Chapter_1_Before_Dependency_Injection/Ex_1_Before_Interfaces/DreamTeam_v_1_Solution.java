package Chapter_1_Before_Dependency_Injection.Ex_1_Before_Interfaces;

import java.util.LinkedList;

/**
 * It's very easy to forget change something after multiple copy-paste inserts
 */
public class DreamTeam_v_1_Solution {
    public static void main(String[] args) {

        Joker joker = new Joker();
        SuperHero batman = new SuperHero();
        Citizen kane = new Citizen();


        LinkedList dreamTeam = new LinkedList();
        dreamTeam.add(new Object());
        dreamTeam.add(joker);
        dreamTeam.add(batman);
        dreamTeam.add(kane);

        dreamTeam.forEach((Object o)->{
            if(o instanceof Joker){
                ((Joker)o).speak();
            }
            else if(o instanceof SuperHero){
                ((SuperHero)o).speak();
            }
            else if(o instanceof Citizen){
                ((Citizen)o).speak(); //<--- Change type here
            }
        });
    }
}
