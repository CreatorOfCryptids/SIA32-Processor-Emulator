public class Processor {

    private final int REGISTER_COUNT = 32;

    private Word PC;            // Program Counter.
    private Word SP;            // Stack Pointer.
    private Word CI;            // Current Instruction.
    private Bit haulted;        // Stores if the process is haulted.

    private Word[] registers;   // The 32 registers in this process.

    // Instruction storage
    private Word imm;           // Immedieate value.        Specified in bits 0-#
    private Word rs1;           // The register             specified in bits 8-12
    private Word rs2;           // The register             specified in bits 13-17
    private Bit[] function;     // function code            specified in bits 18-21
    private Word rd;            // The destination register specified in bits 22-26
    private OpCode op;          // The current opCode,      specified in bits 27-31

    private ALU alu;            // Reference to the ALU.

    private enum OpCode{
        MATH2,      MATH3,      MATH1,      MATH0,
        BRANCH2,    BRANCH3,    BRANCH1,    BRANCH0,
        CALL2,      CALL3,      CALL1,      CALL0,
        PUSH2,      PUSH3,      PUSH1,      PUSH0,
        LOAD2,      LOAD3,      LOAD1,      LOAD0,
        STORE2,     STORE3,     STORE1,     STORE0,
        POPI2,      POPI3,      POPI1,      POPI0,
    }

    /**
     * Constructor.
     * 
     * @throws Exception
     */
    Processor() throws Exception{

        PC = new Word();
        SP = new Word();    SP.set(1024);
        CI = new Word();
        haulted = new Bit();

        registers = new Word[REGISTER_COUNT];
        for(int i = 0; i< REGISTER_COUNT; i++)
            registers[i] = new Word();

        imm = new Word();
        rs1 = new Word();
        rs2 = new Word();
        rd = new Word();

        alu = new ALU();

        function = new Bit[4];
        for(int i = 0; i<function.length; i++){
            function[i] = new Bit();
        }

        
    }

    /**
     * Loops thru each instruction until hault is called.
     * @throws Exception
     */
    public void run() throws Exception{
        while(haulted.getValue() == false){
            fetch();
            decode();
            execute();
            store();
        }
    }

    /**
     * Gets the next instruction and increments the program counter.
     * 
     * @throws Exception
     */
    private void fetch() throws Exception{
        CI = MainMemory.read(PC);
        PC.increment();
    }

    /**
     * Breaks each instruction into its components for easy reading in the next two stages.
     * 
     * @throws Exception
     */
    private void decode() throws Exception{
        // 11 - 3 Register instruction
        if(CI.getBit(30).and(CI.getBit(31)).getValue()){
            // 000 - Math:          (27 + 28 + 29)'
            if (CI.getBit(27).or(CI.getBit(28)).or(CI.getBit(29)).not().getValue()){
                op = OpCode.MATH3;
            }
            // 010 - Call:          27' * 28 * 29'
            else if (CI.getBit(27).not().and(CI.getBit(28)).and(CI.getBit(29).not()).getValue()){
                op = OpCode.CALL3;
            }
            // 011 - Push:          27' * 28 * 29
            else if (CI.getBit(27).not().and(CI.getBit(28)).and(CI.getBit(29)).getValue()){
                op = OpCode.PUSH3;
            }
            // 100 - Load:          27 * 28' * 29'
            else if (CI.getBit(27).and(CI.getBit(28).not()).and(CI.getBit(29).not()).getValue()){
                op = OpCode.LOAD3;
            }
            // 101 - Store:         27 * 28' * 29
            else if (CI.getBit(27).and(CI.getBit(28).not()).and(CI.getBit(29)).getValue()){
                op = OpCode.STORE3;
            }
            // 110 - Pop/interrupt: 27 * 28 * 29'
            else if (CI.getBit(27).and(CI.getBit(28)).and(CI.getBit(29).not()).getValue()){
                op = OpCode.POPI3;
            }

            // Get immediate
            imm.copy(CI.rightShift(26).leftShift(26));

            // get RS1
            rs1.copy(registers[getRegisterIndex(CI, 19)]);

            // get RS2
            rs2.copy(registers[getRegisterIndex(CI, 14)]);

            // get function
            function[0] = CI.getBit(18);
            function[1] = CI.getBit(19);
            function[2] = CI.getBit(20);
            function[3] = CI.getBit(21);

            // get RD
            rd.copy(registers[getRegisterIndex(CI, 5)]);
        }
        // 10 - 2 Register instruction
        else if (CI.getBit(30).and(CI.getBit(31).not()).getValue()){
            // 000 - Math:          (27 + 28 + 29)'
            if (CI.getBit(27).or(CI.getBit(28)).or(CI.getBit(29)).not().getValue()){
                op = OpCode.MATH2;
            }
            // 010 - Call:          27' * 28 * 29'
            else if (CI.getBit(27).not().and(CI.getBit(28)).and(CI.getBit(29).not()).getValue()){
                op = OpCode.CALL2;
            }
            // 011 - Push:          27' * 28 * 29
            else if (CI.getBit(27).not().and(CI.getBit(28)).and(CI.getBit(29)).getValue()){
                op = OpCode.PUSH2;
            }
            // 100 - Load:          27 * 28' * 29'
            else if (CI.getBit(27).and(CI.getBit(28).not()).and(CI.getBit(29).not()).getValue()){
                op = OpCode.LOAD2;
            }
            // 101 - Store:         27 * 28' * 29
            else if (CI.getBit(27).and(CI.getBit(28).not()).and(CI.getBit(29)).getValue()){
                op = OpCode.STORE2;
            }
            // 110 - Pop/interrupt: 27 * 28 * 29'
            else if (CI.getBit(27).and(CI.getBit(28)).and(CI.getBit(29).not()).getValue()){
                op = OpCode.POPI2;
            }

            // get immediate
            imm.copy(CI.rightShift(19).leftShift(19));

            // get rs2
            rs2.copy(registers[getRegisterIndex(CI, 14)]);

            // get function
            function[0] = CI.getBit(18);
            function[1] = CI.getBit(19);
            function[2] = CI.getBit(20);
            function[3] = CI.getBit(21);

            // get rd
            rd.copy(registers[CI.rightShift(5).leftShift(27).getSigned()]);
        }
        // 01 - 1 Register instruction (Dest Only)
        else if ((CI.getBit(30).not()).and(CI.getBit(31)).getValue()){

            // 000 - Math:          (27 + 28 + 29)'
            if (CI.getBit(27).or(CI.getBit(28)).or(CI.getBit(29)).not().getValue()){
                op = OpCode.MATH1;
            }
            // 010 - Call:          27' * 28 * 29'
            else if (CI.getBit(27).not().and(CI.getBit(28)).and(CI.getBit(29).not()).getValue()){
                op = OpCode.CALL1;
            }
            // 011 - Push:          27' * 28 * 29
            else if (CI.getBit(27).not().and(CI.getBit(28)).and(CI.getBit(29)).getValue()){
                op = OpCode.PUSH1;
            }
            // 100 - Load:          27 * 28' * 29'
            else if (CI.getBit(27).and(CI.getBit(28).not()).and(CI.getBit(29).not()).getValue()){
                op = OpCode.LOAD1;
            }
            // 101 - Store:         27 * 28' * 29
            else if (CI.getBit(27).and(CI.getBit(28).not()).and(CI.getBit(29)).getValue()){
                op = OpCode.STORE1;
            }
            // 110 - Pop/interrupt: 27 * 28 * 29'
            else if (CI.getBit(27).and(CI.getBit(28)).and(CI.getBit(29).not()).getValue()){
                op = OpCode.POPI1;
            }

            // Immediate
            imm.copy(CI.rightShift(14).leftShift(14));

            // Function
            function[0] = CI.getBit(18);
            function[1] = CI.getBit(19);
            function[2] = CI.getBit(20);
            function[3] = CI.getBit(21);

            // Rd
            rd.copy(registers[getRegisterIndex(CI, 5)]);
        }
        // 00 - No Register
        else{
            
            // 000 - Math:          (27 + 28 + 29)'
            if (CI.getBit(27).or(CI.getBit(28)).or(CI.getBit(29)).not().getValue()){
                op = OpCode.MATH0;
            }
            // 010 - Call:          27' * 28 * 29'
            else if (CI.getBit(27).not().and(CI.getBit(28)).and(CI.getBit(29).not()).getValue()){
                op = OpCode.CALL0;
            }
            // 011 - Push:          27' * 28 * 29
            else if (CI.getBit(27).not().and(CI.getBit(28)).and(CI.getBit(29)).getValue()){
                op = OpCode.PUSH0;
            }
            // 100 - Load:          27 * 28' * 29'
            else if (CI.getBit(27).and(CI.getBit(28).not()).and(CI.getBit(29).not()).getValue()){
                op = OpCode.LOAD0;
            }
            // 101 - Store:         27 * 28' * 29
            else if (CI.getBit(27).and(CI.getBit(28).not()).and(CI.getBit(29)).getValue()){
                op = OpCode.STORE0;
            }
            // 110 - Pop/interrupt: 27 * 28 * 29'
            else if (CI.getBit(27).and(CI.getBit(28)).and(CI.getBit(29).not()).getValue()){
                op = OpCode.POPI0;
            }

            // Immediate
            imm.copy(CI.rightShift(5).leftShift(5));
        }
    }

    /**
     * Does the work required by the decoded instruciton.
     * 
     * @throws Exception
     */
    private void execute() throws Exception{
        switch (op){
            case BRANCH0:   // pc <- RS2 BOP rd ? pc + imm : pc
                break;
            case BRANCH1:   // pc <- RS1 BOP RS2 ? pc + imm : pc
                break;
            case BRANCH2:   // JUMP: pc <- cp + imm
                break;
            case BRANCH3:   // JUMP: pc <- imm
                break;
            case CALL0:     // push pc; pc <- imm
                break;
            case CALL1:     // push pc; pc <- RD + imm
                break;
            case CALL2:     // pc <- RS2 BOP RD ? push pc; pc + im : pc
                break;
            case CALL3:     // pc <- RS1 BOP RS2 ? push pc; RD + imm : pc
                break;
            case LOAD0:     // Return (pc <- pop)
                break;
            case LOAD1:     // RD <- mem[RD + imm]
                break;
            case LOAD2:     // RD <- mem[RS2 + imm]
                break;
            case LOAD3:     // RD <- mem[RS1 +RS2]
                break;
            case MATH0:     // HALT

                haulted.set();
                break;

            case MATH1:     // COPY: rd <- imm
                break;

            case MATH2:     // rd <- rd MOP reg[1]

                alu.op1.copy(rd);
                alu.op2.copy(rs2);

                alu.doOperation(function);
                break;

            case MATH3:     // rd <- rs1 MOP rs2

                alu.op1.copy(rs1);
                alu.op2.copy(rs2);

                alu.doOperation(function);
                break;

            case POPI0:     // INTERRUPT: Push pc; pc <- intvec[imm]
                break;
            case POPI1:     // POP: RD <- mem[sp++]
                break;
            case POPI2:     // Peek: RD <- mem[sp - (RS2 + imm)]
                break;
            case POPI3:     // PEEK RD <- mem[sp - (RS1 + RS2)]
                break;
            case PUSH0:     // UNUSED
                break;
            case PUSH1:     // mem[--sp] <- RD MOP imm
                break;
            case PUSH2:     // mem[--sp] <- RD MOP RS2
                break;
            case PUSH3:     // mem[--sp] <- RS1 MOP RS2
                break;
            case STORE0:    // UNUSED
                break;
            case STORE1:    // mem[RD] <- imm
                break;
            case STORE2:    // mem[RD + imm] <- RS2
                break;
            case STORE3:    // mem[RD + RS1] <- RS2
                break;
            default:        // Something didn't work.
                throw new Exception("Unrecognized OP Code: " + op.toString() + " from code : " + CI.toString());        
        }
    }

    /**
     * Upates the registers and main memory following each instruction.
     * 
     * @throws Exception
     */
    private void store() throws Exception{
        switch (op){
            case BRANCH0:   // pc <- RS2 BOP rd ? pc + imm : pc
                break;
            case BRANCH1:   // pc <- RS1 BOP RS2 ? pc + imm : pc
                break;
            case BRANCH2:   // JUMP: pc <- cp + imm
                break;
            case BRANCH3:   // JUMP: pc <- imm
                break;
            case CALL0:     // push pc; pc <- imm
                break;
            case CALL1:     // push pc; pc <- RD + imm
                break;
            case CALL2:     // pc <- RS2 BOP RD ? push pc; pc + im : pc
                break;
            case CALL3:     // pc <- RS1 BOP RS2 ? push pc; RD + imm : pc
                break;
            case LOAD0:     // Return (pc <- pop)
                break;
            case LOAD1:     // RD <- mem[RD + imm]
                break;
            case LOAD2:     // RD <- mem[RS2 + imm]
                break;
            case LOAD3:     // RD <- mem[RS1 +RS2]
                break;
            case MATH0:     // HALT (Do Nothing )
                break;
            case MATH1:     // COPY: rd <- imm
                registers[CI.rightShift(5).leftShift(27).getSigned()].copy(imm);
                break;

            case MATH2:     // rd <- rd MOP reg[1]
                registers[CI.rightShift(5).leftShift(27).getSigned()].copy(alu.result);
                break;

            case MATH3:     // rd <- rs1 MOP rs2
                registers[CI.rightShift(5).leftShift(27).getSigned()].copy(alu.result);
                break;

            case POPI0:     // INTERRUPT: Push pc; pc <- intvec[imm]
                break;
            case POPI1:     // POP: RD <- mem[sp++]
                break;
            case POPI2:     // Peek: RD <- mem[sp - (RS2 + imm)]
                break;
            case POPI3:     // PEEK RD <- mem[sp - (RS1 + RS2)]
                break;
            case PUSH0:     // UNUSED
                break;
            case PUSH1:     // mem[--sp] <- RD MOP imm
                break;
            case PUSH2:     // mem[--sp] <- RD MOP RS2
                break;
            case PUSH3:     // mem[--sp] <- RS1 MOP RS2
                break;
            case STORE0:    // UNUSED
                break;
            case STORE1:    // mem[RD] <- imm
                break;
            case STORE2:    // mem[RD + imm] <- RS2
                break;
            case STORE3:    // mem[RD + RS1] <- RS2
                break;
            default:        // Something didn't work.
                throw new Exception("Unrecognized OP Code: " + op.toString() + " from code : " + CI.toString());        
        }

        // Clear R0, so that it can only ever be 0. Very lazy, but quite effective.
        for(int i = 0; i<Word.WORD_SIZE; i++) 
            registers[0].setBit(i, false);
    }

    // Helper Methods:
    
    /**
     * We're not allowed to use getSigned() to figure out which register we need to use, so this
     * 
     * @param regIndex
     * @return
     */
    private int getRegisterIndex(Word instruction, int regStart) throws Exception{

        Word regIndex = instruction.rightShift(regStart).leftShift(27);

        int index = 0;
        for(int i = 4; i>=0; i--){   // Only look at the first 5 bits because there's only 32 registers.

            index *= 2;

            if (regIndex.getBit(i).getValue())
                index++;
        }

        return index;
    }

    // Debugging and testing:

    /**
     * Gets the specified register for easy testing and review.
     * 
     * @param i The index of the specified register. Must be between 0 and 31.
     * @return The specified register.
     */
    public Word TESTgetRegister(int i){
        return registers[i];
    }

    /**
     * DEBUGGING HELPER!!! Lets me see what's inside.
     * 
     * @return A string containing useful debugging info.
     */
    public String toString(){

        String retString = "PC: " + PC.getSigned() + " SP: " + SP.getSigned() + " CI: " + CI.toString() + " Haulted: " + haulted.toString() +"\n";

        for(int i = 0; i<registers.length; i++)
            retString += i + "\t" + registers[i].toString() + "\n";

        return retString;
    }
}
