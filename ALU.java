public class ALU {
    
    public Word op1, op2, result;

    ALU() throws Exception{
        op1 = new Word();
        op2 = new Word();
        result = new Word();
    }
    
    private enum OP{
        AND,        // 1000
        OR,         // 1001
        XOR,        // 1010
        NOT,        // 1011
        LEFT,       // 1100
        RIGHT,      // 1101
        ADD,        // 1110
        SUBTRACT,   // 1111
        MULTIPLY    // 0111
    }

    /**
     * Takes an operation and turns it into an enum for easy switching.
     * 
     * @param operation A sequence of four bits that contains the code for the desired operation.
     * @return An enum that stores which operation was encoded.
     * @throws Exception Throws an exception if the code does not reference a valid operation.
     */
    private OP interpretOperation(Bit[] operation) throws Exception{

        Bit first, second, third, fourth;
        first = operation[0];
        second = operation[1];
        third = operation[2];
        fourth = operation[3];


        if(first.and(second.not()).and(third.not()).and(fourth.not()).getValue())   // 1000
            return OP.AND;
        else if (first.and(second.not()).and(third.not()).and(fourth).getValue())   // 1001
            return OP.OR;
        else if (first.and(second.not()).and(third).and(fourth.not()).getValue())   // 1010
            return OP.XOR;
        else if (first.and(second.not()).and(third).and(fourth).getValue())         // 1011
            return OP.NOT;
        else if (first.and(second).and(third.not().and(fourth.not())).getValue())   // 1100
            return OP.LEFT;
        else if (first.and(second).and(third.not()).and(fourth).getValue())         // 1101
            return OP.RIGHT;
        else if (first.and(second).and(third).and(fourth.not()).getValue())         // 1110
            return OP.ADD;
        else if (first.and(second).and(third).and(fourth).getValue())               // 1111
            return OP.SUBTRACT;
        else if (first.not().and(second.and(third).and(fourth)).getValue())         // 0111
            return OP.MULTIPLY;
        else 
            throw new Exception("Unexpected code " + operation[0].toString() + operation[1].toString() + operation[2].toString() + operation[3].toString());

    }

    /**
     * Performs the operation encoded by the passed Code Operation.
     * 
     * @param operation An array of 4 Bits.
     * @throws Exception
     */
    public void doOperation(Bit[] operation) throws Exception{
        int shift;

        switch(interpretOperation(operation)){
            case AND:
                result.copy(op1.and(op2));
                break;

            case OR:
                result.copy(op1.or(op2));
                break;
            
            case XOR:
                result.copy(op1.xor(op2));
                break;

            case NOT:
                result.copy(op1.not());
                break;

            case LEFT:
                result.copy(op1);
                shift = 1;
                for(int i = 1; i<5; i++){   // Only looks at the first 5 bits. Anything more would clear the word.
                    result.leftShift(shift);
                    shift *= 2;
                }
                break;

            case RIGHT:
                result.copy(op1);
                shift = 1;
                for(int i = 1; i<5; i++){
                    result.rightShift(shift);
                    shift *= 2;
                }
                break;

            case ADD:
                add();
                break;

            case SUBTRACT:
                subtract();
                break;

            case MULTIPLY:
                multiply();
                break;
            
            default:
                throw new Exception("Unexpected code " + operation[0].toString() + operation[1].toString() + operation[2].toString() + operation[3].toString());            
        }
    }

    /**
     * Calls add2 to add the two words together.
     * 
     * @throws Exception
     */
    private void add() throws Exception{
        result.copy(add2(op1, op2));
    }

    /**
     * Adds two words together.
     * 
     * @param op1 The first word to be added.
     * @param op2 The second word to be added.
     * @return The sum of the two words.
     * @throws Exception
     */
    private Word add2(Word op1, Word op2) throws Exception{
        
        Bit carry = new Bit(false);
        Bit a, b;

        Word retval = new Word();

        for(int i = 0; i<Word.WORD_SIZE; i++){
            
            a = op1.getBit(i);
            b = op2.getBit(i);

            // S = A x B x Cin
            retval.setBit(i, (a).xor(b).xor(carry));
            // result.setBit(i, ((op1.getBit(i).xor(op2.getBit(i))).xor(carry)));

            // Cout = (A*B) + (Cin * (A x B))
            carry = ((a.and(b)).or(carry.and(a.xor(b))));
            // carry.set((op1.getBit(i).and(op2.getBit(i))).or((op1.getBit(i).xor(op2.getBit(i))).and(carry)).getValue());
        }

        return retval;
    }

    /**
     * Uses bit logic to add 4 words together.
     * 
     * @param op1 The first Word to add.
     * @param op2 The second Word to add.
     * @param op3 The thrid Word to add.
     * @param op4 The fourth Word to add.
     * @return A new word containtin the sum of the four numbers.
     * @throws Exception
     */
    private Word add4(Word op1, Word op2, Word op3, Word op4) throws Exception{

        Word sum = new Word();
        Word carry = new Word();
        Word temp = new Word();

        for(int i=0; i<Word.WORD_SIZE; i++){
            // TODO: Add
            byte bitSum = 0;

            if (op1.getBit(i).getValue() == true)
                bitSum++;
            
            if (op2.getBit(i).getValue() == true)
                bitSum++;

            if (op3.getBit(i).getValue() == true)
                bitSum++;

            if (op4.getBit(i).getValue() == true)
                bitSum++;
            
            if (carry.getBit(i).getValue() == true)
                bitSum++;
            
            // XOR the bits at index i to get the sum.
            sum.setBit(i, op1.getBit(i).xor(op2.getBit(i)).xor(op3.getBit(i).xor(op4.getBit(i))).xor(carry.getBit(i)));

            /*
            // Don't need a false case becuase Bits default to false.
            if (bitSum % 2 == 1)
                sum.setBit(i, true);
            /**/
            
            temp.set(bitSum/2);
            temp = temp.leftShift(i+1);
            
            carry = add2(carry, temp);
        }

        return sum;
    }

    /**
     * Uses the formula for boolean subtraction found at https://www.101computing.net/binary-subtraction-using-logic-gates/ to subtract op1 and op2.
     * 
     * @throws Exception
     */
    private void subtract() throws Exception{

        Bit borrow = new Bit(false);
        Bit a, b;

        for(int i = 0; i<Word.WORD_SIZE; i++){
            a = op1.getBit(i);
            b = op2.getBit(i);

            // S = A x B x Bin
            result.setBit(i, a.xor(b).xor(borrow));

            // Bout = (A` * B) + ((AxB) * Bin)
            borrow = ((a.not()).and(b)).or(((a.xor(b)).not()).and(borrow));
        }
    }

    /**
     * Multiplys the two operands together.
     */
    private void multiply() throws Exception{

        /*
         * Finds the product by adding a series of bit shifts.
         */

        //* There's got to be a better way, but this works, and I'm lazy.
        result = 
        add2(
            add4( 
                add4( 
                    op2.getBit(0).getValue() ? op1.leftShift(0) : new Word(),
                    op2.getBit(1).getValue() ? op1.leftShift(1) : new Word(),
                    op2.getBit(2).getValue() ? op1.leftShift(2) : new Word(),
                    op2.getBit(3).getValue() ? op1.leftShift(3) : new Word()
                ),
                add4( 
                    op2.getBit(4).getValue() ? op1.leftShift(4) : new Word(),
                    op2.getBit(5).getValue() ? op1.leftShift(5) : new Word(),
                    op2.getBit(6).getValue() ? op1.leftShift(6) : new Word(),
                    op2.getBit(7).getValue() ? op1.leftShift(7) : new Word()
                ),
                add4( 
                    op2.getBit(8).getValue() ? op1.leftShift(8) : new Word(),
                    op2.getBit(9).getValue() ? op1.leftShift(9) : new Word(),
                    op2.getBit(10).getValue() ? op1.leftShift(10) : new Word(),
                    op2.getBit(11).getValue() ? op1.leftShift(11) : new Word()
                ),
                add4( 
                    op2.getBit(12).getValue() ? op1.leftShift(12) : new Word(),
                    op2.getBit(13).getValue() ? op1.leftShift(13) : new Word(),
                    op2.getBit(14).getValue() ? op1.leftShift(14) : new Word(),
                    op2.getBit(15).getValue() ? op1.leftShift(15) : new Word()
                )
            ),
            add4( 
                add4( 
                    op2.getBit(16).getValue() ? op1.leftShift(16) : new Word(),
                    op2.getBit(17).getValue() ? op1.leftShift(17) : new Word(),
                    op2.getBit(18).getValue() ? op1.leftShift(18) : new Word(),
                    op2.getBit(19).getValue() ? op1.leftShift(19) : new Word()
                ),
                add4( 
                    op2.getBit(20).getValue() ? op1.leftShift(20) : new Word(),
                    op2.getBit(21).getValue() ? op1.leftShift(21) : new Word(),
                    op2.getBit(22).getValue() ? op1.leftShift(22) : new Word(),
                    op2.getBit(23).getValue() ? op1.leftShift(23) : new Word()
                ),
                add4( 
                    op2.getBit(24).getValue() ? op1.leftShift(24) : new Word(),
                    op2.getBit(25).getValue() ? op1.leftShift(25) : new Word(),
                    op2.getBit(26).getValue() ? op1.leftShift(26) : new Word(),
                    op2.getBit(27).getValue() ? op1.leftShift(27) : new Word()
                ),
                add4( 
                    op2.getBit(28).getValue() ? op1.leftShift(28) : new Word(),
                    op2.getBit(29).getValue() ? op1.leftShift(29) : new Word(),
                    op2.getBit(30).getValue() ? op1.leftShift(30) : new Word(),
                    op2.getBit(31).getValue() ? op1.leftShift(31) : new Word()
                )
            )
        );
        /**/
    }

    /**
     * TEST VERSION!!!!! Uses bit logic to add 4 words together.
     * @param op1 The first Word to add.
     * @param op2 The second Word to add.
     * @param op3 The thrid Word to add.
     * @param op4 The fourth Word to add.
     * @return The sum of the four numbers.
     * @throws Exception
     */
    public Word TESTadd4(Word op1, Word op2, Word op3, Word op4) throws Exception{
        return add4(op1, op2, op3, op4);
    }
}
