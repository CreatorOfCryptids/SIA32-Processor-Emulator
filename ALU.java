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
            // TODO
        }
        else if (code == 15){   // Subtract
            // TODO
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
    private Bit add2(Bit b1, Bit b2){
        Bit carry = new Bit();

        return carry;
    }

    private void add4(){

    }

    private void subtract(){

    }

    private void multiply(){

    }

    public Bit TESTadd2(Bit b1, Bit b2){

        return add2(b1, b2);
    }

    public Word TESTadd4(Word op1, Word op2){

        this.op1 = op1;
        this.op2 = op2;

        add4();

        return result;
    }
}
