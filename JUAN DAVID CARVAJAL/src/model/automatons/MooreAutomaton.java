/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.automatons;

import java.util.*;
import java.util.Map.Entry;
import model.auxiliary.*;

/**
 * Moore Automaton implementation of a Finite State Machine.
 * @author JUAN DAVID CARVAJAL
 */
public class MooreAutomaton extends FiniteStateMachine {

    private HashMap<State, Symbol> outputFunction;
    private HashMap<Tuple<State, Symbol>, State> transitionFunction;

    /**
     * Constructs a MooreAutomaton with the specified parametets.
     * @param name The name of the FiniteStateMachine.
     * @param Q The set of States of the machine.
     * @param S The input alphabet for the machine.
     * @param R The output alphabet for the machine.
     * @param q0 Initial state for the machine.
     * @throws Exception Exception thrown when the specificed initial state is not contained in Q.
     */
    public MooreAutomaton(String name, String[] Q, String[] S, String[] R, String q0) throws Exception {
        super(name, Q, S, R, q0);
        outputFunction = new HashMap<>();
        transitionFunction = new HashMap<>();

    }

    private MooreAutomaton(String name) {
        super(name);
        outputFunction = new HashMap<>();
        transitionFunction = new HashMap<>();
    }

    private static boolean areEquivalent(MooreAutomaton M1, MooreAutomaton M2) throws Exception {

        if (M1.inputAlphabet.equals(M2.inputAlphabet) && M1.outputAlphabet.equals(M2.outputAlphabet)) {
            M1.deleteInnacesibleStates();
            M2.deleteInnacesibleStates();
            Random rand = new Random();
            for (State q1 : M1.states.values()) {
                for (State q2 : M2.states.values()) {
                    String newName = q1.getName() + rand.nextInt(10);
                    while (q1.equals(q2)) {
                        try {
                            M1.changeStateName(q1.getName(), newName);
                        } catch (Exception e) {
                            newName += rand.nextInt(10);
                        }
                    }
                }
            }

            MooreAutomaton DS = MooreAutomaton.directSum(M1, M2);
            //System.out.println(DS.stateTable);
            HashSet<HashSet<State>> sets = new HashSet<>();
            HashSet<State> partitioned = new HashSet<>();
            for (State q : DS.states.values()) {
                if (!partitioned.contains(q)) {
                    HashSet<State> partition = new HashSet<>();
                    partition.add(q);
                    partitioned.add(q);
                    for (State q2 : DS.states.values()) {
                        if (q != q2 && !partitioned.contains(q2)) {

                            Symbol s1 = DS.outputFunction.get(q);
                            Symbol s2 = DS.outputFunction.get(q2);
                            boolean equal = s1.equals(s2);
                            if (equal) {
                                partition.add(q2);
                                partitioned.add(q2);
                            }

                        }
                    }
                    sets.add(partition);
                }

            }
            System.out.println(sets);
            Tuple<HashSet<HashSet<State>>, Boolean> next = partition(sets, DS);
            boolean changed = next.getSecond();
            sets = next.getFirst();
            System.out.println(sets);
            while (changed) {
                next = partition(sets, DS);
                changed = next.getSecond();
                sets = next.getFirst();
                System.out.println(sets);
            }
            boolean equal = true;
            for (HashSet<State> set : sets) {
                if (set.size() < 2) {
                    equal = false;
                    break;
                }
                boolean m1Found = false;
                boolean m2Found = false;
                for (State q : set) {
                    if (q.getOwner() == M1) {
                        m1Found = true;
                    } else if (q.getOwner() == M2) {
                        m2Found = true;
                    }
                }
                if (!(m1Found && m2Found)) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                return belognsToSamePartition(M1.initialState, M2.initialState, sets);
            } else {
                return false;
            }

        } else {
            return false;
        }

    }

    private static Tuple<HashSet<HashSet<State>>, Boolean> partition(HashSet<HashSet<State>> p, MooreAutomaton M) {
        boolean changed = false;
        HashSet<HashSet<State>> newP = new HashSet<>();
        for (HashSet<State> subp : p) {
            HashSet<HashSet<State>> aux = new HashSet<>();
            HashSet<State> classified = new HashSet<>();
            for (State q1 : subp) {
                if (!classified.contains(q1)) {
                    classified.add(q1);
                    HashSet<State> newSet = new HashSet<>();
                    newSet.add(q1);
                    for (State q2 : subp) {
                        if (q1 != q2 && !classified.contains(q2)) {
                            boolean good = true;
                            for (Symbol s : M.inputAlphabet) {
                                if (!belognsToSamePartition(M.transitionFunction.get(new Tuple(q1, s)), M.transitionFunction.get(new Tuple(q2, s)), p)) {
                                    good = false;
                                    break;
                                }
                            }
                            if (good) {
                                newSet.add(q2);
                                classified.add(q2);
                            }
                        }
                    }
                    aux.add(newSet);
                }
                newP.addAll(aux);
            }
        }
        return new Tuple(newP, newP.size() > p.size());
    }

    private static MooreAutomaton directSum(MooreAutomaton M1, MooreAutomaton M2) {
        MooreAutomaton DS = new MooreAutomaton("DirectSum");
        DS.inputAlphabet = M1.inputAlphabet;
        DS.outputAlphabet = M2.outputAlphabet;
        for (State q1 : M1.states.values()) {
            DS.states.put(q1.getName(), q1);
        }
        for (State q2 : M2.states.values()) {
            DS.states.put(q2.getName(), q2);
        }

        for (Entry<State, Symbol> e : M1.outputFunction.entrySet()) {
            DS.outputFunction.put(e.getKey(), e.getValue());
        }

        for (Entry<State, Symbol> e : M2.outputFunction.entrySet()) {
            DS.outputFunction.put(e.getKey(), e.getValue());
        }

        for (Entry<Tuple<State, Symbol>, State> e : M1.transitionFunction.entrySet()) {
            DS.transitionFunction.put(e.getKey(), e.getValue());
        }

        for (Entry<Tuple<State, Symbol>, State> e : M2.transitionFunction.entrySet()) {
            DS.transitionFunction.put(e.getKey(), e.getValue());
        }

        return DS;

    }

    /**
     * Maps the State with name q,and Symbol with value s, to the State with name q1.
     * This method will specify this:
     * When in State q, receiving Symbol s, go to State q1.
     * @param q Name of initial State.
     * @param s Value for the transition Symbol.
     * @param q1 Name of the go-to State.
     * @throws Exception Exception thrown when some of the specified parameters are not part of this MooreAutomaton.
     */
    public void addTransition(String q, String s, String q1) throws Exception {
        if (!this.states.containsKey(q)) {
            throw new Exception("No state " + q + " exists.");
        }
        if (!this.states.containsKey(q1)) {
            throw new Exception("No state " + q1 + " exists.");
        }
        if (!this.inputAlphabet.contains(new Symbol(s))) {
            throw new Exception("No symbol " + s + " in input alphabet exists.");
        }

        this.transitionFunction.put(new Tuple<>(this.states.get(q), new Symbol(s)), this.states.get(q1));

    }

    /**
     * Maps the specified State to output the specified Symbol.
     * @param q Name of state.
     * @param r Value for the output Symbol.
     * @throws Exception  Exception thrown is no State with name q exists, or if no Symbol with value r exists in the output alphabet.
     */
    public void addOutput(String q, String r) throws Exception {
        if (!this.states.containsKey(q)) {
            throw new Exception("No state " + q + " exists.");
        }
        if (!this.outputAlphabet.contains(new Symbol(r))) {
            throw new Exception("No symbol " + r + " in output alphabet exists.");
        }
        this.outputFunction.put(this.states.get(q), new Symbol(r));
    }

    @Override
    public void deleteInnacesibleStates() {
        HashSet<String> visited = new HashSet<>();
        stateTraversal(this.initialState, visited);

        HashSet<State> toDelete = new HashSet<>();
        for (State q : this.states.values()) {
            if (!visited.contains(q.getName())) {
                toDelete.add(q);
            }
        }

        for (State q : toDelete) {
            for (Symbol s : this.inputAlphabet) {
                this.transitionFunction.remove(new Tuple<>(q, s));
                this.outputFunction.remove(q);
            }
            this.states.remove(q.getName());
        }
    }

    private void stateTraversal(State state, HashSet<String> visited) {
        visited.add(state.getName());
        //System.out.println("Visited " + state.getName());
        for (Symbol s : this.inputAlphabet) {
            Tuple<State, Symbol> t = new Tuple<>(state, s);

            if (this.transitionFunction.containsKey(t) && this.transitionFunction.get(t) != null) {
                if (!visited.contains(this.transitionFunction.get(t).getName())) {
                    stateTraversal(this.transitionFunction.get(t), visited);
                }
            }

        }
    }
    
    
    @Override
    public boolean equals(Object obj) {
        try {
            if(obj instanceof MooreAutomaton){
                 return MooreAutomaton.areEquivalent(this, (MooreAutomaton)obj);
            }else{
                return false;
            }
           
        } catch (Exception e) {
            return false;
        }
    }

}
