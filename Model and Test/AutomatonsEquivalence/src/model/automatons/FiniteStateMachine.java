/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.automatons;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import model.auxiliary.*;

/**
 * This class represents the generic implementation of a Finite State Machine.
 * @author JUAN DAVID CARVAJAL
 */
public abstract class FiniteStateMachine {

    protected HashMap<String, State> states;
    protected HashSet<Symbol> inputAlphabet;
    protected HashSet<Symbol> outputAlphabet;
    protected State initialState;
    protected String name;

    protected FiniteStateMachine(String name) {
        this.name = name;
        this.states = new HashMap<>();
        this.inputAlphabet = new HashSet<>();
        this.outputAlphabet = new HashSet<>();
    }

    /**
     * Constructs a FiniteStateMachine with parameters.
     * @param name The name of the FiniteStateMachine.
     * @param Q The set of States of the machine.
     * @param S The input alphabet for the machine.
     * @param R The output alphabet for the machine.
     * @param q0 Initial state for the machine.
     * @throws Exception Exception thrown when the specificed initial state is not contained in Q.
     */
    public FiniteStateMachine(String name, String[] Q, String[] S, String[] R, String q0) throws Exception {
        if (Arrays.asList(Q).contains(q0)) {
            this.name = name;

            this.states = new HashMap<>();
            for (String q : Q) {
                State s = new State(q, this);
                this.states.put(q, s);
                if (q.equals(q0)) {
                    this.initialState = s;
                }

            }
            this.inputAlphabet = new HashSet<>();
            for (String s : S) {
                this.inputAlphabet.add(new Symbol(s));
            }
            this.outputAlphabet = new HashSet<>();
            for (String r : R) {
                this.outputAlphabet.add(new Symbol(r));
            }
        } else {
            throw new Exception("The initial state specified (" + q0 + ") is not part of the set of states Q.");
        }

    }

    /**
     * Changes the name of the specified State.
     * @param oldName The name of the State to be renamed.
     * @param newName The new name.
     * @throws Exception Exception thrown when the State to be renamed does not exist in the machine, or if the newName is already taken by another State.
     */
    public void changeStateName(String oldName, String newName) throws Exception {
        if (this.states.containsKey(oldName)) {
            if (!this.states.containsKey(newName)) {
                State s = this.states.get(oldName);
                //State sAux = new State(oldName, this);

                s.setName(newName);
                this.states.put(newName, s);

                this.states.remove(oldName);
            } else {
                throw new Exception("There is already a State called " + newName + ".");
            }
        } else {
            throw new Exception("There is no State called " + oldName + ".");
        }

    }

    /**
     * String representation of this FiniteStateMachine.
     * @return A String containing the String representation if this object.
     */
    @Override
    public String toString() {
        return name + " = {" + "Q=" + states.keySet() + ", S=" + inputAlphabet + ", R=" + outputAlphabet + ", q0=" + initialState + '}';
    }

    /**
     * Deletes all States not accesible from the initial State.
     */
    public abstract void deleteInnacesibleStates();

    /**
     * Determines if this FiniteStateMachine is equals to another Object.
     * @param obj The Object to be compared.
     * @return boolean value, true if this FiniteStateMachine is equivalent to the parameter obj.
     */
    @Override
    public abstract boolean equals(Object obj);

    protected static boolean belognsToSamePartition(State X, State Y, HashSet<HashSet<State>> sets) {
        HashSet<State> XSet = null;
        for (HashSet<State> set : sets) {
            if (set.contains(X)) {
                XSet = set;
                break;
            }
        }
        HashSet<State> YSet = null;
        for (HashSet<State> set : sets) {
            if (set.contains(Y)) {
                YSet = set;
                break;
            }
        }
        return XSet == YSet;
    }

}
