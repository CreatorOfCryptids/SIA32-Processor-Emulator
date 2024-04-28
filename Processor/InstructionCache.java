package Processor;

public class InstructionCache {
    
    public static final int CACHE_SIZE = 8; // The amount of words that this cache can hold.
    private static Word[] cache = new Word[CACHE_SIZE];
    private static int startAddress = -2 - CACHE_SIZE;  // The inital starting address. Set to negative so that accessing 0 doesn't return a null value.

    /**
     * Gets the requested instruction through a cache.
     * 
     * Cache hit costs 10, cache misses cost 350.
     * 
     * @param addressWord The address of the requested instruction.
     * @return The requested instruction.
     * @throws Exception
     */
    public static Word readInstruction(Word addressWord) throws Exception{
        Processor.clockCycles += 10;
        int address = addressWord.getSigned();

        // If address is not in cache...
        if (address < startAddress || address >= startAddress + CACHE_SIZE){
            
            startAddress = address;

            // Load new instructions into cache
            L2Cache.Tuple tuple = L2Cache.getInstructions(new Word(address));

            for(int i=0; i< CACHE_SIZE; i++)
                cache[i].copy(tuple.words[i]);

            startAddress = tuple.address;

        }

        return cache[address-startAddress];
    }

    /**
     * Clears the cache for use by another process.
     */
    public static void clear(){
        for(int i=0; i< CACHE_SIZE; i++){
            cache[i] = new Word();
        }
    }
}