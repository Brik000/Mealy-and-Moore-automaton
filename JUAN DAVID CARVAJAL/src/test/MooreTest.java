package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.automatons.MooreAutomaton;

class MooreTest {
	
	
	MooreAutomaton m;
	MooreAutomaton m1;
	MooreAutomaton m2;
	MooreAutomaton m3;
	
	
	
	void setUpM() throws Exception {
		
		String[] Q = {"A", "B"};
		String[] S = {"1", "0"};
		String[] R = {"1", "0"};
		m=new MooreAutomaton("M", Q, S, R, "A");
		
		m.addOutput("A", "1");
		m.addOutput("B", "0");
		
		m.addTransition("A", "0", "B");
		m.addTransition("B", "1", "A");
		
	}
	void setUpM1() throws Exception {
		
		String[] Q = {"C", "D"};
		String[] S = {"1", "0"};
		String[] R = {"1", "0"};
		m1=new MooreAutomaton("M", Q, S, R, "C");
		
		m1.addOutput("C", "1");
		m1.addOutput("D", "0");
		
		m1.addTransition("C", "0", "D");
		m1.addTransition("D", "1", "C");
		
	}

	void setUpM2() throws Exception {

		String[] Q = {"A", "B"};
		String[] S = {"1", "0"};
		String[] R = {"1", "0"};
		m2=new MooreAutomaton("M", Q, S, R, "A");

		m2.addOutput("A", "1");
		m2.addOutput("B", "0");
		
		m2.addTransition("A", "0", "B");
		m2.addTransition("A", "0", "A");
		m2.addTransition("B", "0", "A");
		m2.addTransition("B", "0", "B");
		
	}
	

	@Test
	void test() {
		try {
			setUpM();
			setUpM1();
			assertTrue(m.equals(m1));
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			assertTrue(false);
		}		
		
	}
	@Test
	void test1() {
		try {
			setUpM1();
			setUpM2();
			assertTrue(!m1.equals(m2));
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			assertTrue(false);
		}		
		
	}
	@Test
	void test2() {
		try {
			setUpM1();
			
			assertTrue(m1.equals(m1));
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			assertTrue(false);
		}		
		
	}

}
