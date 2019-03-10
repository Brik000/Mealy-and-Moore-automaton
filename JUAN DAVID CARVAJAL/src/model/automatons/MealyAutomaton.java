/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.automatons;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import model.auxiliary.State;
import model.auxiliary.Symbol;
import model.auxiliary.Tuple;

/**
 * Mealy Automaton implementation of a Finite State Machine.
 * @author JUAN DAVID CARVAJAL
 */
public class MealyAutomaton extends FiniteStateMachine {

    private HashMap<Tuple<State, Symbol>, Tuple<State, Symbol>> stateTable;

    /**
     * Constructs a MealyAutomaton with the specified parametets.
     * @param name The name of the FiniteStateMachine.
     * @param Q The set of States of the machine.
     * @param S The input alphabet for the machine.
     * @param R The output alphabet for the machine.
     * @param q0 Initial state for the machine.
     * @throws Exception Exception thrown when the specificed initial state is not contained in Q.
     */
    public MealyAutomaton(String name, String[] Q, String[] S, String[] R, String q0) throws Exception {
        super(name, Q, S, R, q0);
        stateTable = new HashMap<>();
        for (State q : this.states.values()) {
            for (Symbol s : this.inputAlphabet) {
                stateTable.put(new Tuple<>(q, s), null);
            }
        }
    }

    /**
     * Generates a new mapping for the transition and output function of this MealyAutomaton.
     * This method specifies two things:
     * 1. When in State q, receiving Symbol s, go to State q1.
     * 2. When in State q, receiving Symbol s, output Symbol r.
     * @param q The initial State of the mapping.
     * @param s The input Symbol for the mapping.
     * @param q1 The go-to State of the mapping.
     * @param r The output Symbol of the mapping.
     * @throws Exception Exception thrown is any of the specified parameters are not part of the MealyAutomaton.
     */
    public void addMapping(String q, String s, String q1, String r) throws Exception {
        if (!this.states.containsKey(q)) {
            throw new Exception("No state " + q + " exists.");
        }
        if (!this.states.containsKey(q1)) {
            throw new Exception("No state " + q1 + " exists.");
        }

        if (!this.inputAlphabet.contains(new Symbol(s))) {
            throw new Exception("No symbol " + s + " in input alphabet exists.");
        }
        if (!this.outputAlphabet.contains(new Symbol(r))) {
            throw new Exception("No symbol " + r + " in output alphabet exists.");
        }
        Tuple<State, Symbol> tuple = new Tuple<>(this.states.get(q), new Symbol(s));
        this.stateTable.put(tuple, new Tuple<>(this.states.get(q1), new Symbol(r)));
    }

    private void stateTraversal(State state, HashSet<String> visited) {
        visited.add(state.getName());
        //System.out.println("Visited " + state.getName());
        for (Symbol s : this.inputAlphabet) {
            Tuple<State, Symbol> t = new Tuple<>(state, s);

            if (this.stateTable.containsKey(t) && this.stateTable.get(t) != null) {
                if (!visited.contains(this.stateTable.get(t).getFirst().getName())) {
                    stateTraversal(this.stateTable.get(t).getFirst(), visited);
                }
            }

        }
    }

    private static boolean areEquivalent(MealyAutomaton M1, MealyAutomaton M2) throws Exception {

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

            MealyAutomaton DS = MealyAutomaton.directSum(M1, M2);
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

                            boolean equal = true;
                            for (Symbol s : DS.inputAlphabet) {

                                Tuple<State, Symbol> t1 = DS.stateTable.get(new Tuple(q, s));
                                Tuple<State, Symbol> t2 = DS.stateTable.get(new Tuple(q2, s));
                                if(t1==null){
                                    throw new Exception("Empty mapping for "+new Tuple(q, s)+".");
                                }
                                if(t2==null){
                                    throw new Exception("Empty mapping for "+new Tuple(q2, s)+".");
                                }
                                if (!DS.stateTable.get(new Tuple(q, s)).getSecond().getValue().equals(DS.stateTable.get(new Tuple(q2, s)).getSecond().getValue())) {
                                    equal = false;
                                    break;
                                }

                            }
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

    private static Tuple<HashSet<HashSet<State>>, Boolean> partition(HashSet<HashSet<State>> p, MealyAutomaton M) {
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
                                if (!belognsToSamePartition(M.stateTable.get(new Tuple(q1, s)).getFirst(), M.stateTable.get(new Tuple(q2, s)).getFirst(), p)) {
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

    private MealyAutomaton(String name) {
        super(name);
        this.stateTable = new HashMap<>();
    }

    private static MealyAutomaton directSum(MealyAutomaton M1, MealyAutomaton M2) {
        MealyAutomaton DS = new MealyAutomaton("DirectSum");
        DS.inputAlphabet = M1.inputAlphabet;
        DS.outputAlphabet = M2.outputAlphabet;
        for (State q1 : M1.states.values()) {
            DS.states.put(q1.getName(), q1);
        }
        for (State q2 : M2.states.values()) {
            DS.states.put(q2.getName(), q2);
        }
        for (Entry s : M1.stateTable.entrySet()) {
            DS.stateTable.put((Tuple<State, Symbol>) s.getKey(), (Tuple<State, Symbol>) s.getValue());
        }

        for (Entry s : M2.stateTable.entrySet()) {
            DS.stateTable.put((Tuple<State, Symbol>) s.getKey(), (Tuple<State, Symbol>) s.getValue());
        }

        return DS;
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
                this.stateTable.remove(new Tuple<>(q, s));
            }
            this.states.remove(q.getName());
        }
    }

    @Override
    public boolean equals(Object obj) {
        try {
            if(obj instanceof MealyAutomaton){
                return MealyAutomaton.areEquivalent(this, (MealyAutomaton)obj);
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
