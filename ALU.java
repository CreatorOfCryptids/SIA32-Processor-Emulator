public class ALU {
    
    public Word op1, op2, result;

    ALU() throws Exception{
        op1 = new Word();
        op2 = new Word();
        result = new Word();
    }
    
    private enum op{
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
     * Performs the operation encoded by the passed Code Operation.
     * @param operation An array of 4 Bits 
     * @throws Exception
     */
    public void doOperation(Bit[] operation) throws Exception{

        switch(){
            case
        }
        throw new Exception("Unexpected code " + operation[0].toString() + operation[1].toString() + operation[2].toString() + operation[3].toString());
        
        /*if(operation.length != 4)
            throw new Exception("Wrong number of operation bits");

        byte code = opCodeToNum(operation);
        if(code == 8)           // AND
            result.copy(op1.and(op2));
        else if (code == 9)     // OR
            result.copy(op1.or(op2));
        else if (code == 10)    // XOR
            result.copy(op1.xor(op2));
        else if (code == 11)    // NOT            
            result.copy(op1.not());
        else if (code == 12){   // LeftShift
            result.copy(op1);
            int shift = 1;
            for(int i = 1; i<5; i++){
                result.leftShift(shift);
                shift *= 2;
            }
        }
        else if (code == 13){   // RightShift
            result.copy(op1);
            int shift = 1;
            for(int i = 1; i<5; i++){
                result.rightShift(shift);
                shift *= 2;
            }
        }
        else if (code == 14)    // Add
            add();
        else if (code == 15)    // Subtract
            subtract();
        else if (code == 7)     // Multiply
            multiply();
        else
            throw new Exception("Unexpected code " + opCodeToNum(operation));
        */
    }

    /**
     * Turns the CodeOperation into a number for easy switching.
     * @param operation
     * @return
     *
    public byte opCodeToNum(Bit[] operation){
        byte retval = 0;

        if(operation[0].getValue())
            retval+=8;
        if(operation[1].getValue())
            retval+=4;
        if(operation[2].getValue())
            retval+=2;
        if(operation[3].getValue())
            retval+=1;

        return retval;
    }/**/

    /**
     * Calls add2 to add the two words together.
     * @throws Exception
     */
    private void add() throws Exception{
        result.copy(add2(op1, op2));
    }

    /**
     * Adds two words together.
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
     * Uses add2 to add four words together.
     * @param op1 The first Word to add.
     * @param op2 The second Word to add.
     * @param op3 The thrid Word to add.
     * @param op4 The fourth Word to add.
     * @return The sum of the four numbers.
     * @throws Exception
     */
    private Word add4(Word op1, Word op2, Word op3, Word op4) throws Exception{
        return add2(add2(op1, op2), add2(op3, op4));
    }

    /**
     * Uses the formula for boolean subtraction found at https://www.101computing.net/binary-subtraction-using-logic-gates/ to subtract op1 and op2.
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
     * Multiplys the two words together.
     */
    private void multiply() throws Exception{

        

        /*result = 
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
        );*/
    }

    /**
     * TEST VERSION!!!!! Uses add2 to add four words together.
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
