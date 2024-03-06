public class Processor {

    private final int REGISTER_COUNT = 31;

    private Word PC;            // Program Counter
    private Word SP;            // Stack Pointer.
    private Word CI;            // Current Instruction

    
    private Bit haulted;        // Stores if the process is haulted.

    private Word[] registers;   // The 32 registers in this process

    private OpCode op;          // The current opCode
    

    private ALU alu;

    private enum OpCode{
        MATH2,      MATH3,      MATH1,      MATH0,
        BRANCH2,    BRANCH3,    BRANCH1,    BRANCH0,
        CALL2,      CALL3,      CALL1,      CALL0,
        PUSH2,      PUSH3,      PUSH1,      PUSH0,
        LOAD2,      LOAD3,      LOAD1,      LOAD0,
        STORE2,     STORE3,     STORE1,     STORE0,
        POPI2,      POPI3,      POPI1,      POPI0,
    }

    Processor() throws Exception{
        PC = new Word();
        SP = new Word();
        CI = new Word();

        haulted = new Bit();

        registers = new Word[REGISTER_COUNT];

        ALU alu = new ALU();

        SP.set(1024);

    }

    public void run() throws Exception{
        while(haulted.getValue() == false){
            fetch();
            decode();
            execute();
            store();
        }
    }

    private void fetch() throws Exception{
        CI = MainMemory.read(PC);
        PC.increment();
    }

    private void decode() throws Exception{
        //* if Statements version.
        // 11 - 3 Register instruction
        if(CI.getBit(31).and(CI.getBit(30)).getValue()){
            
        }
        // 10 - 2 Register instruction
        else if (CI.getBit(31).and(CI.getBit(30).not()).getValue()){

        }
        // 01 - 1 Register instruction (Dest Only)
        else if ((CI.getBit(31).not()).and(CI.getBit(30)).getValue()){
            
        }
        // 00 - No Register
        else{
            
        }
        //*/
    }

    private void execute(){

    }

    private void store(){

    }
}
