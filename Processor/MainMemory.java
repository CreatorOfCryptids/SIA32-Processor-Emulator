package Processor;

public class MainMemory {
    public static final int MEM_SIZE = 1024;
    private static Word[] memory = new Word[MEM_SIZE];

    /**
     * Reads the memory at the given address.
     * 
     * @param address The address of the desired memory.
     * @return The data in the memory address.
     * @throws Exception
     */
    public static Word read(Word address) throws Exception{

        int adrs = (int) address.getUnsigned();

        Word[] memememory = memory; // Revealing to the debugger.

        if (adrs >= MEM_SIZE || adrs < 0)
            throw new Exception("Memory address "+adrs+" out of bounds");
        
        if (memory[adrs] == null)
            memory[adrs] = new Word();

        return memory[adrs];
    }

    /**
     * Writes the given word to memory.
     *  
     * @param address The address of memory to be writen to
     * @param value The new data for the address.
     * @throws Exception
     */
    public static void write(Word address, Word value) throws Exception{

        int adrs = (int) address.getUnsigned();

        Word[] memememory = memory; // Revealing to the debugger.
        
        if (adrs >= MEM_SIZE || adrs < 0)
            throw new Exception("Memory address "+adrs+" out of bounds");

        if (memory[adrs] == null)
            memory[adrs] = new Word();

        memory[adrs].copy(value);
    }

    /**
     * Loads data into memory from a string.
     * 
     * @param data The array of strings containing the data to be loaded.
     */
    public static void load(String[] data) throws Exception{

        for(int i=0; i<data.length; i++){
            // If not initialized, init.
            if(memory[i] == null){
                memory[i] = new Word();
            }

            // Loop over word to set
            for(int j = 0; j<Word.WORD_SIZE; j++){
                if(data[i].charAt(j) == '1'){
                    memory[i].setBit(j, true);
                }
                else if(data[i].charAt(j) == '0'){
                    memory[i].setBit(j, false);
                }
                else
                    throw new Exception("Unexpected character \'" + data[i].charAt(j) + "\' in String " + i + " at character " + j + ".");
            }
        }
    }

    /**
     * DEBUGGING HELPER!!! Returns the designated address in memory.
     * 
     * @param address The address of the desired memory. must be between 0-1023.
     * @return The Word at the designated address.
     */
    public static Word TEST_Read(int address){
        return memory[address];
    }
}
