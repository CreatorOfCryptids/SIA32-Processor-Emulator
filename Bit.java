public class Bit{

    private boolean value;

    /**
     * Bit constructor
     * @param value The value stored in the bit
     */
    public Bit(boolean value){
        this.value = value;
    }

    /**
     * Set
     * @param value The new value for the bit.
     */
    public void set(boolean value){
        this.value = value;
    }

    /**
     * Switches the bit from false to true and vice versa.
     */
    public void toggle(){
        value = !value;
    }

    /**
     * Sets the bit to true.
     */
    public void set(){
        value = true;
    }

    /**
     * Sets the bit to false.
     */
    public void clear(){
        value = false;
    }

    /**
     * Returns the value of the bit.
     * @return The boolean value of the bit.
     */
    public boolean getValue(){
        return value;
    }

    /**
     * Performs the AND logical function on the two bits.
     * @param other The second bit to be compared.
     * @return A new bit containing the result of the AND function.
     */
    public Bit and(Bit other){
        if(value && other.getValue() )
            return new Bit(true);
        else
            return new Bit(false);
    }

    /**
     * Performs the OR logical function on the two bits.
     * @param other The second bit to be compared
     * @return A new bit containing the result of the OR function.
     */
    public Bit or(Bit other){
        if(value || other.getValue())
            return new Bit(true);
        else
            return new Bit(false);
    }

    /**
     * Performs the XOR logical function on the two bits.
     * @param other The second bit to be compared
     * @return A new bit containing the result of the XOR function.
     */
    public Bit xor(Bit other){
        if((value || other.getValue()) && !(value && other.getValue()))
            return new Bit(true);
        else
            return new Bit(false);
    }
    /**
     * Performs the NOT logical function on the bit.
     * @return A bit containing the oposite value as the bit.
     */
    public Bit not(){
        return new Bit(!value);
    }

    /**
     * @return "T" for true and "F" for false.
     */
    public String toString(){
        if(value)
            return "T";
        else    
            return "F";
    }
}
