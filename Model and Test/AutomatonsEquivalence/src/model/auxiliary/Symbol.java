/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.auxiliary;

import java.util.Objects;

/**
 * Symbol representation for the FiniteStateMachine implementations.
 * @author JUAN DAVID CARVAJAL
 */
public class Symbol {
    
    private final String value;
    
    /**
     * Constructs a new Symbol instance with the specified value.
     * @param value Value for the new Symbol isntance.
     */
    public  Symbol(String value){
        this.value=value;
    }
    
    /**
     * Get the value of this Symbol.
     * @return Stirng ,representing the value of this Symbol.
     */
    public String getValue(){
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.value);
        return hash;
    }

    /**
     * Implements the equality comparison for this Class.
     * @param obj Object to be compared with this.
     * @return True if obj is of Class Symbol and both values are equal. False otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Symbol other = (Symbol) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    /**
     * String representation of this Class.
     * @return String, containing the value of this instance.
     */
    @Override
    public String toString() {
        return value;
    }
    
    
}
