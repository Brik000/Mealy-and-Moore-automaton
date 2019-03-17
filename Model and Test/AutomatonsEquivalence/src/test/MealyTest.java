package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.automatons.MealyAutomaton;

class MealyTest {

	MealyAutomaton m;
	MealyAutomaton m1;
	MealyAutomaton m2;
	
	MealyAutomaton m3;
	MealyAutomaton m4;


	void setUpM1() throws Exception {

		String[] Q1 = {"A", "B", "C"};
		String[] S1 = {"1", "0"};
		String[] R1 = {"1", "0"};


		/**
		 * Constructs a MealyAutomaton with the specified parametets.
		 * @param name The name of the FiniteStateMachine.
		 * @param Q The set of States of the machine.
		 * @param S The input alphabet for the machine.
		 * @param R The output alphabet for the machine.
		 * @param q0 Initial state for the machine.
		 * @throws Exception Exception thrown when the specificed initial state is not contained in Q.
		 */
		m1 = new MealyAutomaton("M1", Q1, S1, R1, "A");
		System.out.println(m1);
		m1.addMapping("A", "0", "B", "0");
		m1.addMapping("A", "1", "A", "0");
		m1.addMapping("B", "0", "A", "0");
		m1.addMapping("B", "1", "C", "1");
		m1.addMapping("C", "0", "C", "1");
		m1.addMapping("C", "1", "A", "0");

	}




	void setUpM2() throws Exception {
		String[] Q2 = {"D", "E", "F", "G", "H"};
		String[] S2 = {"1", "0"};
		String[] R2 = {"1", "0"};
		m2 = new MealyAutomaton("M2", Q2, S2, R2, "D");
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
	} 
	
	void setUpM3() throws Exception {
		String[] Q1 = {"H", "I", "J","K"};
		String[] S1 = {"1", "0"};
		String[] R1 = {"1", "0"};
		
		m3=new MealyAutomaton("M3",Q1,S1,R1,"H");
		
		m3.addMapping("H","1", "I","0");
		m3.addMapping("H","0", "I","1");
		m3.addMapping("I","1", "J","1");
		m3.addMapping("I","0", "J","0");
		m3.addMapping("J","1", "K","1");
		m3.addMapping("J","0", "K","0");
		m3.addMapping("K","1", "I","1");
		m3.addMapping("K","0", "I","0");
		
	
	  }
		
		
	
	void setUpM4() throws Exception {
		String[] Q1 = {"A", "B", "C","D", "E", "F","G"};
		String[] S1 = {"1", "0"};
		String[] R1 = {"1", "0"};
		m4=new MealyAutomaton("M4",Q1,S1,R1,"A");
		
		m4.addMapping("A","1", "C","0");
		m4.addMapping("A","0", "B","1");
		m4.addMapping("B","1", "E","0");
		m4.addMapping("B","0", "D","1");
		m4.addMapping("C","1", "E","0");
		m4.addMapping("C","0", "D","1");
		m4.addMapping("D","1", "G","1");
		m4.addMapping("D","0", "F","0");
		m4.addMapping("E","1", "G","1");
		m4.addMapping("E","0", "F","0");
		m4.addMapping("F","1", "B","1");
		m4.addMapping("F","0", "B","0");
		m4.addMapping("G","1", "C","1");
		m4.addMapping("G","0", "C","0");
		
		
		
		
		
	}


	void  setUpM() throws Exception {
		String[] Q1 = {"A", "B", "C"};
		String[] S1 = {"1", "0"};
		String[] R1 = {"a", "b", "c"};
		String[] Q2 = {"F", "G", "H"};
		String[] S2 = {"1", "0"};
		String[] R2 = {"a", "b", "c"};
		m = new MealyAutomaton("M1", Q1, S1, R1, "A");
		m.addMapping("A", "1", "B", "a");
		m.addMapping("A", "0", "A", "b");
		m.addMapping("B", "1", "A", "c");
		m.addMapping("B", "0", "C", "b");
		m.addMapping("C", "1", "A", "a");
		m.addMapping("C", "0", "C", "b");

		m.deleteInnacesibleStates();    
	}
	@Test
	void testEquals1() {
		try {
			setUpM1();
			setUpM2();
			assertTrue(m1.equals(m2));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}	
	}

	@Test
	void testEquals2() {
		try {
			setUpM2();
			setUpM();
			assertTrue(!m.equals(m2));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}	
	}
	


	@Test
	void testEquals3() {
		try {
			setUpM2();
			setUpM();
			assertTrue(!m.equals(m1));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}		
	}

	@Test
	void testEquals4() {
		try {
			setUpM3();
			setUpM4();
			assertTrue(!m3.equals(m4));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}		
		
	}
	@Test
	void testEquals5() {
		try {
			setUpM3();
			assertTrue(m3.equals(m3));
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			assertTrue(false);
		}		
		
	}

}
