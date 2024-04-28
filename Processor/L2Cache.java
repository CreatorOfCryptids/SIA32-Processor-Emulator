package Processor;

public class L2Cache {
    
    private static final int GROUP_COUNT = 4;
    private static final int GROUP_SIZE = 8;
    private static Word[][] cache = new Word[GROUP_COUNT][GROUP_SIZE];
    private static int[] groupAddresses = new int[GROUP_COUNT];
    private static int nextCacheGroup = 0;

    /**
     * Reads the specified address in memory.
     * 
     * Costs 20 cycles on a cache hit, and 350 on a cache miss.
     * 
     * @param addressWord A Word containing the address in memory of the desired Word.
     * @return A copy of the Word stored in the specified address in memory.
     * @throws Exception
     */
    public static Word read(Word addressWord) throws Exception{
        // Divide into even sections so there isn't any overlap.
        int address = addressWord.getSigned() / GROUP_SIZE;

        Word[][] cachee = cache;     // Debugger won't show the cache unless I do this.
        int []  groupAddressess = groupAddresses;

        int index = searchCacheGroup(address);

        // The desired Word is not in L2.
        if (index == -1){
            Processor.clockCycles += 350;   // Fetching from Memory always costs 350.
            index = nextCacheGroup++ % GROUP_COUNT; // Cycle thru each cache.
            groupAddresses[index] = address;

            for(int i = 0; i<GROUP_SIZE && (address*GROUP_SIZE + i) < MainMemory.MEM_SIZE; i++){
                //Processor.clockCycles -= 300;   // Cancel out read cost of MainMemory.
                cache[index][i].copy(MainMemory.read(new Word((address * GROUP_SIZE) + i)));
            }
        }
        
        Processor.clockCycles += 20;    // Reading from L2 always costs 20.

        return cache[index][addressWord.getSigned() % GROUP_SIZE];
    }

    /**
     * Gets the specified address in memory, but adds 20 additional cycles to account for the 
     * extra steps from the instruction cache.
     * 
     * @param addressWord
     * @return
     * @throws Exception
     */
    public static Word readInstruction(Word addressWord) throws Exception{
        // Reading from L2 on an InstructionCache miss costs 50 cycles. This makes the numers work out.
        Processor.clockCycles += 20;    
        return read(addressWord);
    }

    /**
     * Writes a value to the specified address in memory.
     * 
     * Costs 50 cycles.
     * 
     * @param addressWord
     * @param value
     * @throws Exception
     */
    public static void write(Word addressWord, Word value) throws Exception{
        // Cancel out memory cost so that it only costs 50 to write.
        Processor.clockCycles +=50; 
        MainMemory.write(addressWord, value);

        // Check if it exists in the cache.
        int address = addressWord.getSigned() / GROUP_SIZE;

        int index = searchCacheGroup(address);

        // If it does exist, update it.
        if (index != -1){
            cache[index][address%GROUP_SIZE].copy(value);
        }
    }

    /**
     * Clears the cache for use by another process.
     */
    public static void clear(){
        for(int i = 0; i<GROUP_COUNT; i++){
            for(int j=0; j<GROUP_SIZE; j++){
                cache[i][j] = new Word();
            }
            groupAddresses[i] = -1;
        }
    }

    /**
     * Checks if there is a cache group for the passed address. Returns the index of the cache
     * group if found.
     * 
     * @param address The group address of the desired area in memory.
     * @return The index of the desired cache group, or -1 if not found.
     */
    private static int searchCacheGroup(int address){

        int index = -1;

        for(int i = 0; i<GROUP_COUNT; i++){
            if (address == groupAddresses[i]){
                index = i;
                break;
            }
        }

        return index;
    }
}
