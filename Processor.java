public class Processor {

    private Word PC;    // Program Counter
    private Word SP;    // Stack Pointer.
    private Word CI;    // Current Instruction
    private Bit haulted;

    Processor(){
        PC = new Word();
        SP = new Word();
        CI = new Word();
        haulted = new Bit();

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

    private void decode(){

    }

    private void execute(){

    }

    private void store(){

    }
}
