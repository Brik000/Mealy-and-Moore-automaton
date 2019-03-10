/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea_teorica_v2;

import model.automatons.MealyAutomaton;

/**
 *
 * @author garab
 */
public class TAREA_TEORICA_V2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
       mealy2();
        
    }

    /**
     * deberia de imprimir true
     * @throws Exception 
     */
    public static void mealy2() throws Exception {
 String[] Q1 = {"A", "B", "C"};
        String[] S1 = {"1", "0"};
        String[] R1 = {"1", "0"};
        String[] Q2 = {"D", "E", "F", "G", "H"};
        String[] S2 = {"1", "0"};
        String[] R2 = {"1", "0"};
        MealyAutomaton m1 = new MealyAutomaton("M1", Q1, S1, R1, "A");
        System.out.println(m1);
        m1.addMapping("A", "0", "B", "0");
        m1.addMapping("A", "1", "A", "0");
        m1.addMapping("B", "0", "A", "0");
        m1.addMapping("B", "1", "C", "1");
        m1.addMapping("C", "0", "C", "1");
        m1.addMapping("C", "1", "A", "0");

        MealyAutomaton m2 = new MealyAutomaton("M2", Q2, S2, R2, "D");
        System.out.println(m2);
        m2.addMapping("D", "0", "E", "0");
        m2.addMapping("D", "1", "D", "0");
        m2.addMapping("E", "0", "D", "0");
        m2.addMapping("E", "1", "F", "1");
        m2.addMapping("F", "0", "F", "1");
        m2.addMapping("F", "1", "D", "0");
        m2.addMapping("G", "0", "E", "0");
        m2.addMapping("G", "1", "H", "1");
        m2.addMapping("H", "0", "D", "1");
        m2.addMapping("H", "1", "G", "0");
        System.out.println(m1.equals(m2));
    }

    /**
     * deberia de imrimir false
     * @throws Exception 
     */
    public static void mealy1() throws Exception {
        String[] Q1 = {"A", "B", "C"};
        String[] S1 = {"1", "0"};
        String[] R1 = {"a", "b", "c"};
        String[] Q2 = {"F", "G", "H"};
        String[] S2 = {"1", "0"};
        String[] R2 = {"a", "b", "c"};
        MealyAutomaton m = new MealyAutomaton("M1", Q1, S1, R1, "A");
        m.addMapping("A", "1", "B", "a");
        m.addMapping("A", "0", "A", "b");
        m.addMapping("B", "1", "A", "c");
        m.addMapping("B", "0", "C", "b");
        m.addMapping("C", "1", "A", "a");
        m.addMapping("C", "0", "C", "b");

        m.deleteInnacesibleStates();

        //m.changeStateName("A", "S");
        System.out.println(m);
        MealyAutomaton m2 = new MealyAutomaton("M2", Q2, S2, R2, "F");
        //m2.changeStateName("J", "Z");
        m2.addMapping("F", "0", "G", "a");
        m2.addMapping("F", "1", "F", "a");
        m2.addMapping("G", "0", "H", "b");
        m2.addMapping("G", "1", "H", "c");
        m2.addMapping("H", "0", "F", "b");
        m2.addMapping("H", "1", "G", "a");
        System.out.println(m2);
        System.out.println(m.equals(m2));
    }
    
    


}
