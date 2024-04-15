package Compiler;

public abstract class Instruction {

    public enum OpCode{
        MATH,
        BRANCH,
        CALL,
        PUSH,
        LOAD,
        STORE,
        POP
    }

    public static final String[] opToString = new String[]{
        "000", // Math
        "001", // Branch
        "010", // Call
        "011", // Push
        "100", // Load
        "101", // Store
        "110"  // Pop 
    };

    public enum Function{
        AND,
        OR,
        XOR,
        NOT,
        LEFT,
        RIGHT,
        ADD,
        SUBTRACT,
        MULTIPLY,
        EQ,
        NE,
        LT,
        GE,
        GT,
        LE
    }

    public static final String[] funcToString = new String[]{
        "1000", // AND
        "1001", // OR
        "1010", // XOR
        "1011", // NOT
        "1100", // Left
        "1101", // Right
        "1110", // Add
        "1111", // Sub
        "0111", // Mult
        "0000", // Eq
        "0001", // Ne
        "0010", // LT
        "0100", // GE
        "0100", // GT
        "0101"  // LE
    };

    public static final String[] regMap = new String[]{
        "00000","10000","01000","11000","00100","10100","01100","11100",
        "00010","10010","01010","11010","00110","10110","01110","11110",
        "00001","10001","01001","11001","00101","10101","01101","11101",
        "00011","10011","01011","11011","00111","10111","01111","11111",
    };
    
    protected Instruction.OpCode op;
    protected int imm;
    
    /**
     * Returns the binary of the instruction.
     * 
     * @return A string containing the binary of this instruction.
     */
    public abstract String toInstruction();

    /**
     * Returns the imm as a string of bits.
     * 
     * @param imm The integer value of the imm.
     * @param immSize The max size of the imm.
     * @return
     */
    protected String immToString(int imm, int immSize) {

        String retVal = "";

        for(int i=immSize-1; i>=0; i++){
            /**
             * Conversion decimal -> binary can be done by finding dec % 2 (1 or 0) and storing 
             * that as the least significant digit. Then divide the number and repeat
             */
            if(imm % 2 == 1)
                retVal += "1";    
            else
                retVal += "0";
            
            imm /= 2;       // The only rightShifting Java lets us have.
        }

        return retVal;
    }

    /**
     * Gets the immediate value from the instruction.
     * 
     * @return A String with the ones and zeros of the immidate value.
     */
    protected int getImm(){
        return imm;
    }

    /**
     * Imm mutator.
     * 
     * @param imm The new value for imm. Must be positive.
     */
    protected void setImm(int imm){
        this.imm = imm;
    }

    /**
     * Gets the OpCode value from the instruction.
     * 
     * @return A String with the ones and zeros of the OpCode.
     */
    protected Instruction.OpCode getOp(){
        return op;
    }
}
