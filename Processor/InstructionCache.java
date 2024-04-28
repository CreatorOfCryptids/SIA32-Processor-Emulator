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

        Word[] cachee = cache;  // So the debugger will show me the cache.
        int startAddressssss = startAddress;

        // If address is not in cache...
        if (address < startAddress || address >= startAddress + CACHE_SIZE){
            startAddress = address;

            // Load new instructions into cache
            for(int i = 0; i<CACHE_SIZE; i++){
                cache[i] = L2Cache.readInstruction(new Word(address + i));
                //Processor.clockCycles -= 300;   // Undo the normal read cost of 300 cycles.
            }
        }

        // L2Cache acounts for the extra costs of reading from InstructionCache, so it's always a cost of 10.
        Processor.clockCycles += 10;    

        return cache[address-startAddress];
    }

    /**
     * Clears the cache to prevent seperate processes from bleeding into eachother.
     */
    public static void clear() {
        startAddress = -10;

        for(int i = 0; i< CACHE_SIZE; i++)
            cache[i] = new Word();
    }
}
