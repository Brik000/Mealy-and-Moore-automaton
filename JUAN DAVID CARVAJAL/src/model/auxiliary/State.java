/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.auxiliary;

import java.util.Objects;
import model.automatons.FiniteStateMachine;


/**
 * Represents a State for the FiniteStateMachine implementations.
 * @author JUAN DAVID CARVAJAL
 */
public class State {
    
    private String name;
    private FiniteStateMachine owner;
    
    /**
     * Constructs a State with specified parameters.
     * @param name The name for the new State.
     * @param owner Reference to the FiniteStateMachine owning this State.
     */
    public State(String name, FiniteStateMachine owner){
        this.name=name;
        this.owner=owner;
    }
    
    /**
     * Gets the name of this State.
     * @return String containing the name of this State.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Changes the name of this State.
     * @param name New name for this State.
     */
    public void setName(String name){
        this.name=name;
    }
    
    
    /**
     * Returns the FiniteStateMachine owning this State.
     * @return FiniteStateMachine reference.
     */
    public FiniteStateMachine getOwner(){
        return this.owner;
    }



   

    @Override
    public int hashCode() {
        
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
    
    
}
