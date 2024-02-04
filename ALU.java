public class ALU {
    
    public Word op1, op2, result;

    ALU() throws Exception{
        op1 = new Word();
        op2 = new Word();
        result = new Word();
    }
    
    /**
     * 
     * @param operation An array of 4 Bits 
     * @throws Exception
     */
    public void doOperation(Bit[] operation) throws Exception{
        
        if(operation.length != 4)
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
            for(int i = 1; i<Word.WORD_SIZE; i++){
                result.leftShift(shift);
                shift *= 2;
            }
        }
        else if (code == 13){   // RightShift
            result.copy(op1);
            int shift = 1;
            for(int i = 1; i<Word.WORD_SIZE; i++){
                result.rightShift(shift);
                shift *= 2;
            }
        }
        else if (code == 14){   // Add
            add2();
        }
        else if (code == 15){   // Subtract
            // TODO
            subtract();
        }
        else if (code == 7){    // Multiply
            // TODO
        }
        else
            throw new Exception("Unexpected code " + opCodeToNum(operation));
    }

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
    }

    /**
     * 
     */
    private void add2() throws Exception{
        
        Bit carry = new Bit(false);
        Bit a, b;

        for(int i = 0; i<Word.WORD_SIZE; i++){
            
            a = op1.getBit(i);
            b = op2.getBit(i);

            // S = A x B x Cin
            result.setBit(i, (a).xor(b).xor(carry));
            // result.setBit(i, ((op1.getBit(i).xor(op2.getBit(i))).xor(carry)));

            // Cout = (A*B) + (Cin * (A x B))
            carry = ((a.and(b)).or(carry.and(a.xor(b))));
            // carry.set((op1.getBit(i).and(op2.getBit(i))).or((op1.getBit(i).xor(op2.getBit(i))).and(carry)).getValue());
        }
    }

    private Word add4(Word op1, Word op2, Word op3, Word op4){
        
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

    private void multiply(){

    }

    public Word TESTadd4(Word op1, Word op2, Word op3, Word op4){

        return add4(op1, op2, op3, op4);
    }
}
