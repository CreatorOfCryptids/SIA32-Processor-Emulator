package Processor;

public class InstructionCache {
    
    public static final int CACHE_SIZE = 8;
    private static Word[] cache = new Word[CACHE_SIZE];
    private static int startAddress = -10;

    /**
     * Gets the requested instruction through a cache.
     * 
     * Cache hit costs 10, cache miss costs 350.
     * 
     * @param addressWord The address of the requested instruction.
     * @return The requested instruction.
     * @throws Exception
     */
    public static Word readInstruction(Word addressWord) throws Exception{
        int address = addressWord.getSigned();

        // If address is not in cache...
        if (address < startAddress || address >= startAddress + CACHE_SIZE){
            
            Processor.clockCycles += 350;

            startAddress = address;

            // Load new instructions into cache
            for(int i = 0; i<CACHE_SIZE; i++){
                cache[i] = MainMemory.read(new Word(address + i));
                Processor.clockCycles -= 300;   // Undo the normal read cost of 300 cycles.
            }

        }
        else{
            Processor.clockCycles += 10;
        }

        return cache[address-startAddress];
    }
}
