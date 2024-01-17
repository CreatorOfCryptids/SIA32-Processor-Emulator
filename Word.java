public class Word {

    private Bit[] bits = new Bit[32];
    public final int WORD_SIZE = 32;

    /**
     * The default constructor.
     */
    public Word(){}

    /**
     * Returns the i'th bit in the Word.
     * @param i
     * @return The i'th bit in the Word.
     * @throws Exception
     */
    public Bit getBit(int i) throws Exception{
        if(i < 0 || i>WORD_SIZE)
            throw new Exception("The bit " + i + "is out of bounds for size 32");
 
        return new Bit(bits[i]);
    }  

    /**
     * Changes the i'th bit in the word.
     * @param i
     * @param value The new Value of the i'th bit.
     * @throws Exception
     */
    public void setBit(int i, Bit value)throws Exception{
        if(i < 0 || i>WORD_SIZE)
            throw new Exception("The bit " + i + "is out of bounds for size 32");
        
        bits[i].set(value.getValue());
    }

    /**
     * Performs the AND logical function on two Words.
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
     * @return A new Word right shifted from the original.
     * @throws Exception
     */
    public Word rightShift() throws Exception{
        Word retWord = new Word();

        for(int i =0; i<WORD_SIZE-1; i++)
            retWord.setBit(i, bits[i+1]);

        return retWord;
    }

    /**
     * Left shifts the Bits from highest index to lowest.
     * @return A new Word left shifted from the original.
     * @throws Exception
     */
    public Word leftShift() throws Exception{
        Word retWord = new Word();

        for(int i =32; i>1; i--)
            retWord.setBit(i, bits[i-1]);

        return retWord;
    }

    /**
     * Returns a comma seperated list of the Bits with either T or F.
     */
    public String toString() {
        String retString = bits[0].toString();

        for(int i = 1; i< WORD_SIZE; i++);
            retString += ',' + bits.toString();

        return retString;
    }

    /**
     * Returns the Word's value as an unsigned long
     * @return a long with the unsigned binary value of the Word.
     */
    public long getUnsigned(){
        long retval = 0;

        for(int i=0; i<WORD_SIZE; i++)
            if(bits[i].getValue())
                retval += Math.pow(2, i + 1);
        
        return retval;
    }

    /**
     * Returns the Word's value as a signed int.
     * @return An int with the signed value of the Word.
     */
    public int getSigned(){
        int retval = 0;

        //TODO: Which bit stores the difference?\

        return retval;
    }
}
