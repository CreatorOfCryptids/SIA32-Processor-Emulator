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
            
            startAddress = address;

            // Load new instructions into cache
            L2Cache.Tuple tuple = L2Cache.getInstructions(new Word(address));

            for(int i=0; i< CACHE_SIZE; i++)
                cache[i].copy(tuple.words[i]);

            startAddress = tuple.address;

        }
        else{
            Processor.clockCycles += 10;
        }

        return cache[address-startAddress];
    }

    public static void clear(){
        for(int i=0; i< CACHE_SIZE; i++){
            cache[i] = new Word();
        }
    }
}