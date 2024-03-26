public class Processor {

    private final int REGISTER_COUNT = 32;

    private final int RS1_START = 19;   // RS1's highest bit index.
    private final int RS2_START = 14;   // RS2's highest bit index.
    private final int RD_START = 5;     // RD's highest bit index.

    private Word PC;            // Program Counter.
    private Word SP;            // Stack Pointer.
    private Word CI;            // Current Instruction.
    private Word exitCode;
    private Bit haulted;        // Stores if the process is haulted.

    private Word[] registers;   // The 32 registers in this process.
    int instructionCount;   // For debugging.

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

        exitCode =  new Word();
        instructionCount = 0;
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
            instructionCount++;
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
            if ((CI.getBit(27).or(CI.getBit(28)).or(CI.getBit(29))).not().getValue()){
                op = OpCode.MATH3;
            }
            // 001 - Branch:        27 * 28 * 29'
            else if (CI.getBit(27).not().and(CI.getBit(28).not()).and(CI.getBit(29)).getValue()){
                op = OpCode.BRANCH3;
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
            rs1.copy(registers[getRegisterIndex(RS1_START)]);

            // get RS2
            rs2.copy(registers[getRegisterIndex(RS2_START)]);

            // get function
            function[0] = CI.getBit(18);
            function[1] = CI.getBit(19);
            function[2] = CI.getBit(20);
            function[3] = CI.getBit(21);

            // get RD
            rd.copy(registers[getRegisterIndex(RD_START)]);
        }
        // 10 - 2 Register instruction
        else if (CI.getBit(30).and(CI.getBit(31).not()).getValue()){
            // 000 - Math:          (27 + 28 + 29)'
            if ((CI.getBit(27).or(CI.getBit(28)).or(CI.getBit(29))).not().getValue()){
                op = OpCode.MATH2;
            }
            // 001 - Branch:        27 * 28 * 29'
            else if (CI.getBit(27).not().and(CI.getBit(28).not()).and(CI.getBit(29)).getValue()){
                op = OpCode.BRANCH2;
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
            rs2.copy(registers[getRegisterIndex(RS2_START)]);

            // get function
            function[0] = CI.getBit(18);
            function[1] = CI.getBit(19);
            function[2] = CI.getBit(20);
            function[3] = CI.getBit(21);

            // get rd
            rd.copy(registers[getRegisterIndex(RD_START)]);
        }
        // 01 - 1 Register instruction (Dest Only)
        else if ((CI.getBit(30).not()).and(CI.getBit(31)).getValue()){

            // 000 - Math:          (27 + 28 + 29)'
            if ((CI.getBit(27).or(CI.getBit(28)).or(CI.getBit(29))).not().getValue()){
                op = OpCode.MATH1;
            }
            // 001 - Branch:        27 * 28 * 29'
            else if (CI.getBit(27).not().and(CI.getBit(28).not()).and(CI.getBit(29)).getValue()){
                op = OpCode.BRANCH1;
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
            rd.copy(registers[getRegisterIndex(RD_START)]);
        }
        // 00 - No Register
        else{
            // 000 - Math:          (27 + 28 + 29)'
            if ((CI.getBit(27).or(CI.getBit(28)).or(CI.getBit(29))).not().getValue()){
                op = OpCode.MATH0;
            }
            // 001 - Branch:        27' * 28' * 29'
            else if (CI.getBit(27).not().and(CI.getBit(28).not()).and(CI.getBit(29)).getValue()){
                op = OpCode.BRANCH0;
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
            case BRANCH0:   // JUMP: pc <- imm
                break;

            case BRANCH1:   // JUMP: pc <- pc + imm
                addWords(PC, imm);
                break;

            case BRANCH2:   // pc <- RS2 BOP rd ? pc + imm : pc
                addWords(PC, imm);
                break;

            case BRANCH3:   // pc <- RS1 BOP RS2 ? pc + imm : pc
                addWords(PC, imm);
                break;

            case CALL0:     // push pc; pc <- imm
                break;

            case CALL1:     // push pc; pc <- RD + imm
                addWords(rd, imm);
                break;

            case CALL2:     // pc <- RS2 BOP RD ? push pc; pc + imm : pc
                addWords(PC, imm);
                break;

            case CALL3:     // pc <- RS1 BOP RS2 ? push pc; RD + imm : pc
                addWords(rd, imm);
                break;

            case LOAD0:     // Return (pc <- pop)
                break;

            case LOAD1:     // RD <- mem[RD + imm]
                addWords(rd, imm);
                break;

            case LOAD2:     // RD <- mem[RS2 + imm]
                addWords(rs2, imm);
                break;

            case LOAD3:     // RD <- mem[RS1 +RS2]
                addWords(rs1, rs2);
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
                addWords(rs2, imm);
                subtractWords(SP, alu.result);
                break;

            case POPI3:     // PEEK RD <- mem[sp - (RS1 + RS2)]
                addWords(rs1, rs2);
                subtractWords(SP, alu.result);
                break;

            case PUSH0:     // UNUSED
                break;

            case PUSH1:     // mem[--sp] <- RD MOP imm
                alu.op1.copy(rd);
                alu.op2.copy(imm);

                alu.doOperation(function);
                break;

            case PUSH2:     // mem[--sp] <- RD MOP RS2
                alu.op1.copy(rd);
                alu.op2.copy(rs2);

                alu.doOperation(function);
                break;

            case PUSH3:     // mem[--sp] <- RS1 MOP RS2
                alu.op1.copy(rs1);
                alu.op2.copy(rs2);

                alu.doOperation(function);
                break;

            case STORE0:    // UNUSED
                break;

            case STORE1:    // mem[RD] <- imm
                break;

            case STORE2:    // mem[RD + imm] <- RS2
                addWords(rd, imm);
                break;

            case STORE3:    // mem[RD + RS1] <- RS2
                addWords(rd, rs1);
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
            case BRANCH0:   // JUMP: pc <- imm
                PC.copy(imm);
                break;

            case BRANCH1:   // JUMP: pc <- pc + imm
                PC.copy(alu.result);
                break;

            case BRANCH2:   // pc <- RS2 BOP rd ? pc + imm : pc
                if (performBOP(function, rs2, rd))
                    PC.copy(alu.result);
                break;

            case BRANCH3:   // pc <- RS1 BOP RS2 ? pc + imm : pc
                if (performBOP(function, rs1, rs2))
                    PC.copy(alu.result);
                break;

            case CALL0:     // push pc; pc <- imm
                push(PC);
                PC.copy(imm);
                break;

            case CALL1:     // push pc; pc <- RD + imm
                push(PC);
                PC.copy(alu.result);
                break;

            case CALL2:     // pc <- RS2 BOP RD ? push pc; pc + imm : pc
                if (performBOP(function, rs2, rd)){
                    push(PC);
                    PC.copy(alu.result);
                }
                break;

            case CALL3:     // pc <- RS1 BOP RS2 ? push pc; RD + imm : pc
                if (performBOP(function, rs1, rs2)){
                    push(PC);
                    PC.copy(alu.result);
                }
                break;

            case LOAD0:     // Return (pc <- pop)
                PC.copy(pop());
                break;

            case LOAD1:     // RD <- mem[RD + imm]
                registers[getRegisterIndex(RD_START)].copy(MainMemory.read(alu.result));
                break;

            case LOAD2:     // RD <- mem[RS2 + imm]
                registers[getRegisterIndex(RD_START)].copy(MainMemory.read(alu.result));
                break;

            case LOAD3:     // RD <- mem[RS1 +RS2]
                registers[getRegisterIndex(RD_START)].copy(MainMemory.read(alu.result));
                break;

            case MATH0:     // HALT (Do Nothing )
                exitCode.copy(imm);
                break;

            case MATH1:     // COPY: rd <- imm
                registers[getRegisterIndex(RD_START)].copy(imm);
                break;

            case MATH2:     // rd <- rd MOP reg[1]
                registers[getRegisterIndex(RD_START)].copy(alu.result);
                break;

            case MATH3:     // rd <- rs1 MOP rs2
                registers[getRegisterIndex(RD_START)].copy(alu.result);
                break;

            case POPI0:     // INTERRUPT: Push pc; pc <- intvec[imm]
                // TODO: Later
                break;
            case POPI1:     // POP: RD <- mem[sp++]
                registers[getRegisterIndex(RD_START)].copy(pop());
                break;

            case POPI2:     // Peek: RD <- mem[sp - (RS2 + imm)]
                registers[getRegisterIndex(RD_START)].copy(MainMemory.read(alu.result));
                break;

            case POPI3:     // PEEK RD <- mem[sp - (RS1 + RS2)]
                registers[getRegisterIndex(RD_START)].copy(MainMemory.read(alu.result));
                break;

            case PUSH0:     // UNUSED
                break;
            case PUSH1:     // mem[--sp] <- RD MOP imm
                push(alu.result);
                break;
            case PUSH2:     // mem[--sp] <- RD MOP RS2
                push(alu.result);
                break;
            case PUSH3:     // mem[--sp] <- RS1 MOP RS2
                push(alu.result);
                break;
            case STORE0:    // UNUSED
                break;
            case STORE1:    // mem[RD] <- imm
                MainMemory.write(rd, imm);
                break;

            case STORE2:    // mem[RD + imm] <- RS2
                MainMemory.write(alu.result, rs2);
                break;

            case STORE3:    // mem[RD + RS1] <- RS2
                MainMemory.write(alu.result, rs2);
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
     * We're not allowed to use getSigned() to figure out which register we need to use.
     * 
     * Finds the index of the register by shifting to get the specific sequence of bits for the register speficfier.
     * 
     * @param CI The instruction to be parsed.
     * @param regStart The start of the register specifier ***FROM THE END OF THE WORD***!!!
     * @return
     */
    private int getRegisterIndex(int regStart) throws Exception{

        Word regIndex = CI.rightShift(regStart).leftShift(27); // Shift to get the bits we want.

        int index = 0;

        // Only look at the first 5 bits because there's only 32 registers. Going backwards because its big-endian.
        for(int i = 4; i>=0; i--){

            index *= 2;

            if (regIndex.getBit(i).getValue())
                index++;
        }

        return index;
    }

    /**
     * Compares two words using the appropriate boolean operation.
     * 
     * @param opCode The four Bit code for the boolean operation.
     * @param op1 The first Word to be compared.
     * @param op2 The second Word to be compared.
     * @return The result of the specified boolean operation.
     * @throws Exception
     */
    private boolean performBOP(Bit[] opCode, Word op1, Word op2) throws Exception{
        
        // 0000 - Equals                (a + b + c + d)
        if ((opCode[0].or(opCode[1]).or(opCode[2]).or(opCode[3])).not().getValue())
            return op1.equals(op2);
        // 0001 - Not equal             (a + b + c)' * d
        else if ((opCode[0].or(opCode[1]).or(opCode[2])).not().and(opCode[3]).getValue())
            return op1.notEqual(op2);
        // 0010 - Less than             a' * b' * c * d' 
        else if (opCode[0].not().and(opCode[1].not()).and(opCode[2]).and(opCode[3].not()).getValue())
            return op1.lessThan(op2);
        // 0011 - Greater than or equal (a + b)' * c * d
        else if ((opCode[0].or(opCode[1])).not().and(opCode[2]).and(opCode[3]).getValue())
            return op1.greaterEquals(op2);
        // 0100 - Greater than          a' * b * (c + d)'
        else if (opCode[0].not().and(opCode[1]).and(opCode[2].or(opCode[3])).not().getValue())
            return op1.greaterThan(op2);
        // 0101 - Less than or equal    a' * b * c' * d
        else if (opCode[0].not().and(opCode[1]).and(opCode[2].not()).and(opCode[3]).getValue())
            return op1.lessEquals(op2);
        // Unregognized OpCode.
        else 
            throw new Exception("Unregocnized boolean operation: " + opCode[0].toString() + opCode[1].toString() + opCode[2].toString() + opCode[3].toString());
    }

    /**
     * Pushes the suplied data onto the stack.
     * 
     * @param data The Word to be added to the top of the stack.
     * @throws Exception
     */
    private void push(Word data) throws Exception{
        SP.decrement();
        MainMemory.write(SP, data);
    }

    /**
     * Pops the suplied data off the data stack.
     * 
     * @return The Word at the top of the stack.
     * @throws Exception
     */
    private Word pop() throws Exception {
        // Read the current top value before we move up a level.
        Word temp = MainMemory.read(SP);
        SP.increment();

        return temp;
    }

    /**
     * Passes two Words and the add opCode to the ALU. I'm too lazy to copy paste it each time.
     * 
     * @param op1 The first Word to be added.
     * @param op2 The second Word to be added.
     * @throws Exception
     */
    private void addWords(Word op1, Word op2) throws Exception{
        alu.op1 = op1;
        alu.op2 = op2;

        // 1110 - Add
        alu.doOperation(new Bit[]{new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
    }

    /**
     * Passes two Words and the subtract opCode to the ALU.
     * 
     * @param op1 The first Word to be subtracted.
     * @param op2 The second Word to be subtracted.
     * @throws Exception
     */
    private void subtractWords(Word op1, Word op2) throws Exception{
        alu.op1 = op1;
        alu.op2 = op2;

        // 1111 - Subtract
        alu.doOperation(new Bit[]{new Bit(true),new Bit(true),new Bit(true),new Bit(false)});
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

    /**
     * Returns the exit code from the process.
     * 
     * @return The numerical value of the exit code.
     */
    public int getExitCode(){
        return exitCode.getSigned();
    }
}
