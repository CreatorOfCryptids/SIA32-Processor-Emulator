


public class Word {
    
    public static final int WORD_SIZE = 32;     // I know "static" is taboo, but it makes it easier to change later if I want to. Fight me.
    private Bit[] bits = new Bit[WORD_SIZE];    // Lowest index is the lowest value (Big endian)

    /**
     * The default constructor.
     */
    public Word(){
        for(int i =0; i<WORD_SIZE; i++)
            bits[i] = new Bit(false);
    }

    /**
     * DEBUGGING HELPER. 
     * @param value The value of the new word
     */
    public Word(int value){
        for(int i =0; i<WORD_SIZE; i++)
            bits[i] = new Bit(false);
        
        this.set(value);
    }

    /**
     * Returns the i'th bit in the Word.
     * 
     * @param i
     * @return The i'th bit in the Word.
     * @throws Exception If the inputed number is outside the array bounds.
     */
    public Bit getBit(int i) throws Exception{
        if(i < 0 || i>WORD_SIZE)
            throw new Exception("The bit " + i + "is out of bounds for size " + WORD_SIZE);
        else
            return new Bit(bits[i]);
    }  

    /**
     * Changes the i'th bit in the word.
     * 
     * @param i
     * @param value The new Value of the i'th bit.
     * @throws Exception If the inputed number is outside the array bounds.
     */
    public void setBit(int i, Bit value)throws Exception{
        if(i < 0 || i>WORD_SIZE)
            throw new Exception("The bit " + i + "is out of bounds for size " + WORD_SIZE);
        else
            bits[i].set(value.getValue());
    }

    /**
     * EXTRA METHOD: Changes the i'th bit in the word. This time with a boolean instead of a Bit.
     * 
     * @param i
     * @param value The new value of the i'th bit.
     * @throws Exception If the inputed number is outside the array bounds.
     */
    public void setBit(int i, boolean value)throws Exception{
        if(i < 0 || i>WORD_SIZE)
            throw new Exception("The bit " + i + "is out of bounds for size " + WORD_SIZE);
        else
            bits[i].set(value);
    }

    /**
     * Performs the AND logical function on two Words.
     * 
     * @param other The second Word to be compared.
     * @return A new Word containing the result of the AND operation.
     * @throws Exception
     */
    public Word and(Word other) throws Exception{
        Word retWord = new Word();

        for(int i =0; i<WORD_SIZE; i++)
            retWord.setBit(i, bits[i].and(other.getBit(i)));

        return retWord;
    }
    
    /**
     * Performs the OR logical function on two Words.
     * 
     * @param other The second Word to be compared.
     * @return A new Word containing the result of the OR operation.
     * @throws Exception
     */
    public Word or(Word other) throws Exception{
        Word retWord = new Word();

        for(int i =0; i<WORD_SIZE; i++)
            retWord.setBit(i, bits[i].or(other.getBit(i)));

        return retWord;
    }

    /**
     * Performs the XOR logical function on two Words.
     * 
     * @param other The second Word to be compared.
     * @return A new Word containing the result of the XOR operation.
     * @throws Exception
     */
    public Word xor(Word other) throws Exception{
        Word retWord = new Word();

        for(int i =0; i<WORD_SIZE; i++)
            retWord.setBit(i, bits[i].xor(other.getBit(i)));

        return retWord;
    }

    /**
     * Performs the XOR logical function on the Words.
     * 
     * @return A new Word containing the result of the NOT operation.
     * @throws Exception
     */
    public Word not() throws Exception{
        Word retWord = new Word();

        for(int i =0; i<WORD_SIZE; i++)
            retWord.setBit(i, bits[i].not());

        return retWord;
    }

    /**
     * Right shifts the Bits from lowest index to hightest.
     * 
     * @param amount The distance shifted.
     * @return A new Word right shifted from the original.
     * @throws Exception
     */
    public Word rightShift(int amount) throws Exception{
        Word retWord = new Word();

        for(int i =0; i<WORD_SIZE; i++)  
            if(i-amount < WORD_SIZE && i-amount >= 0)   // subtract amount so the movement doesn't go out of bounds.
                retWord.setBit(i, bits[i-amount]);
            else 
                retWord.setBit(i, new Bit());

        return retWord;
    }

    /**
     * Left shifts the Bits from highest index to lowest.
     * 
     * @param amount The distance shifted.
     * @return A new Word left shifted from the original.
     * @throws Exception
     */
    public Word leftShift(int amount) throws Exception{
        Word retWord = new Word();

        for(int i =0; i<WORD_SIZE; i++)  
            if(i+amount < WORD_SIZE && i+amount >= 0)   // add the amount so the movement doesn't go out of bounds.
                retWord.setBit(i, bits[i+amount]);
            else 
                retWord.setBit(i, new Bit());

        return retWord;
    }

    /**
     * Returns the Word's value as an unsigned long.
     * 
     * @return a long with the unsigned binary value of the Word.
     */
    public long getUnsigned(){

        long retval = 0;

        for(int i=WORD_SIZE-1; i>=0; i--){
            retval *= 2;                    // Poor man's leftShifting
            if(bits[i].getValue())          // Switch bits to match
                retval++;           
        }
        
        return retval;
    }

    /**
     * Returns the Word's value as a signed int.
     * 
     * @return An int with the signed value of the Word.
     */
    public int getSigned(){

        /*
         * Negative numbers are stored as the NOT of the absolute value plus 1. 
         * i.e: (`(|negValue|)) + 1
         * This can be rewriten as (`(|negValue| -1)).
         */

        int retval = 0;
        boolean isPositive = true;

        if(bits[WORD_SIZE-1].getValue() == true)
            isPositive = false;
        
        for(int i=WORD_SIZE-2; i>=0; i--){
            retval *= 2;                            // Poor man's leftShifting
            if(bits[i].getValue() == isPositive)    // if it's negative the bits need to be NOT-ed
                retval++;                           // Setting the right-most bit to match the stored number
        }
        
        if(isPositive == false)
            retval = (retval * -1) -1;              // Adjusting the value back to negative.

        return retval;
        /**/
    }

    /**
     * Makes a new Word that is a copy of the input.
     * 
     * @param other The Word to be copied.
     * @throws Exception
     */
    public void copy(Word other) throws Exception{
        for(int i =0; i<WORD_SIZE; i++)
            bits[i].set(other.getBit(i).getValue());
    }

    /**
     * Sets the word to be the binary equivelent of the inputted value.
     * 
     * @param value The value to be stored in Binary
     */
    public void set(int value){

        /**
         * Negative numbers are stored as the NOT of the absolute value plus 1. 
         * i.e: (`(|negValue|)) + 1
         * This can be rewriten as (`(|negValue| -1)).
         */

        boolean isPositive = value>=0;      
        if(isPositive == false)
            value = (value * -1) -1;

        for(int i=0; i<WORD_SIZE; i++){
            /**
             * Conversion decimal -> binary can be done by finding dec % 2 (1 or 0) and storing 
             * that as the least significant digit. Then divide the number and repeat
             */
            if(value % 2 == 1)
                bits[i].set(isPositive);    // Comparing against isPositive will NOT the number when negative.
            else
                bits[i].set(!isPositive);
            
            value /= 2;                     // The only rightShifting Java lets us have.
        }
    }

    /**
     * Determines if two words are equal.
     * 
     * @param other The second word to be compared.
     * @return True if they are equal, false if they are not.
     * @throws Exception
     */
    public boolean equals(Word other) throws Exception{
        for(int i = 0; i< WORD_SIZE; i++)
            if(bits[i].getValue() != other.getBit(i).getValue())
                return false;
        
        return true;
    }

    /**
     * Determines if two words are not equal.
     * 
     * @param other The second word to be compared
     * @return False if they are equal. True if they are not.
     * @throws Exception
     */
    public boolean notEqual(Word other) throws Exception{
        for(int i = 0; i< WORD_SIZE; i++)
            if(bits[i].getValue() != other.getBit(i).getValue())
                return true;
        
        return false;
    }

    /**
     * Checks if this Word is less than another Word.
     * 
     * @param other The Word to be compared against.
     * @return True if this Word is lesser than other. False otherwize.
     * @throws Exception
     */
    public boolean lessThan(Word other) throws Exception{
        Word result = new Word();
        Bit borrow = new Bit(false);
        Bit a, b;

        // Subtract other from this.
        for(int i = 0; i<Word.WORD_SIZE; i++){
            a = bits[i];
            b = other.getBit(i);

            // S = A x B x Bin
            result.setBit(i, a.xor(b).xor(borrow));

            // Bout = (A` * B) + ((AxB) * Bin)
            borrow = ((a.not()).and(b)).or(((a.xor(b)).not()).and(borrow));
        }

        // Check if the difference between the two words is negative.
        return result.getBit(WORD_SIZE-1).getValue();
    }

    /**
     * Determines if this Word is greater than or equal to another Word.
     * 
     * @param other The Word to be compared against.
     * @return True if this word is greater than or equal to other. False otherwize.
     * @throws Exception
     */
    public boolean greaterEquals(Word other) throws Exception{
        Word result = new Word();
        Bit borrow = new Bit(false);
        Bit a, b;

        // Subtract other from this.
        for(int i = 0; i<Word.WORD_SIZE; i++){
            a = bits[i];
            b = other.getBit(i);

            // S = A x B x Bin
            result.setBit(i, a.xor(b).xor(borrow));

            // Bout = (A` * B) + ((AxB) * Bin)
            borrow = ((a.not()).and(b)).or(((a.xor(b)).not()).and(borrow));
        }

        // Check if the difference between the two words is negative.
        return !result.getBit(WORD_SIZE-1).getValue();
    }
    /**
     * Checks if this word is greater than another word.
     * 
     * @param other The Word to be compared against.
     * @return True if this Word is greater than other. False otherwize.
     * @throws Exception
     */
    public boolean greaterThan(Word other) throws Exception{
        Word result = new Word();
        Bit borrow = new Bit(false);
        Bit a, b;

        // Subtract this from other.
        for(int i = 0; i<Word.WORD_SIZE; i++){
            a = other.getBit(i);
            b = bits[i];

            // S = A x B x Bin
            result.setBit(i, a.xor(b).xor(borrow));

            // Bout = (A` * B) + ((AxB) * Bin)
            borrow = ((a.not()).and(b)).or(((a.xor(b)).not()).and(borrow));
        }

        // Check if the difference between the two words is negative.
        return result.getBit(WORD_SIZE-1).getValue();
    }

    /**
     * Determines if this Word is greater than or equal to another Word.
     * 
     * @param other The Word to be compared against.
     * @return True if this word is greater than or equal to other. False otherwize.
     * @throws Exception
     */
    public boolean lessEquals(Word other) throws Exception{
        Word result = new Word();
        Bit borrow = new Bit(false);
        Bit a, b;

        // Subtract this from other.
        for(int i = 0; i<Word.WORD_SIZE; i++){
            a = other.getBit(i);
            b = bits[i];

            // S = A x B x Bin
            result.setBit(i, a.xor(b).xor(borrow));

            // Bout = (A` * B) + ((AxB) * Bin)
            borrow = ((a.not()).and(b)).or(((a.xor(b)).not()).and(borrow));
        }

        // Check if the difference between the two words is negative.
        return !result.getBit(WORD_SIZE-1).getValue();
    }
    
    /**
     * The toString() method. Places the most significant bit (bit0) on the Right (BigEndian).
     * 
     * @return A comma seperated list of the Bits with either T or F.
     */
    public String toString() {
        String retString = "";
        for(int i = 0; i < WORD_SIZE - 1; i++)
            retString += bits[i].toString() + ',';

        retString += bits[WORD_SIZE-1].toString();

        return retString;
    }

    /**
     * Increments the integer value of the word by one.
     */
    public void increment(){

        Bit carry = new Bit(true);
        Bit original = new Bit();

        for(int i=0; (i<WORD_SIZE) && (carry.getValue() == true); i++){
            original = bits[i];
            bits[i] = bits[i].xor(carry);
            carry = original.and(carry);
        }
    }

    /**
     * Decrements the integer value of the word.
     */
    public void decrement(){

        Bit borrow = new Bit(true);

        for(int i=0; (i<WORD_SIZE) && (borrow.getValue() == true); i++){
            bits[i] = bits[i].xor(borrow);
            borrow = bits[i];
        }
    }
}
