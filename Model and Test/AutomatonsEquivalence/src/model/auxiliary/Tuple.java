/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.auxiliary;

import java.util.Objects;

/**
 * Generic tuple implementation.
 * @author JUAN DAVID CARVAJAL
 * @param <T> Type parameter of first element.
 * @param <K> Type parameter of second element.
 */
public class Tuple<T, K> {

    private final T first;
    private final K second;

    /**
     * Constructs a new Tuple instance with specified parameters.
     * @param first The value to be stored as first.
     * @param second The value to be stored as second.
     */
    public Tuple(T first, K second) {

        this.first = first;
        this.second = second;
    }

    /**
     * Gets the first value of this Tuple.
     * @return Reference to a T instance.
     */
    public T getFirst() {
        return first;
    }

    /**
     * Gets the second value of this Tuple.
     * @return Reference to a K instance.
     */
    public K getSecond() {
        return second;
    }

    /**
     * Implements equality comparison for this Class.
     * @param obj Object to be compared to this instance.
     * @return True if both objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple<?, ?> other = (Tuple<?, ?>) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.second, other.second)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(this.first) - Objects.hashCode(this.second);

    }

    /**
     * String representation of this Class.
     * @return A String, containing the String representation of this Tuple isntance.
     */
    @Override
    public String toString() {
        return "(" + first + "," + second + ')';
    }
    
    

}
