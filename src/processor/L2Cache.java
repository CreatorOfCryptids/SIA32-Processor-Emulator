package processor;

public class L2Cache{
    public static int CACHE_COUNT = 4;  // The amount of caches.
    public static int CACHE_SIZE = 8;   // The amount of Words in each cache.
    private static Word[][] cache = new Word[CACHE_COUNT][CACHE_SIZE];
    private static int[] addresses = new int[CACHE_COUNT];
    private static int nextCache = 0;   // Stores the index of the next cache.

    /**
     * Reads The designated address in memory. 
     * 
     * Costs 20 cycles on a cache hit, and 350 on a miss.
     * 
     * @param address The address of the desired memory.
     * @return The memory stored in the specified address.
     * @throws Exception
     */
    public static Word read(Word address) throws Exception{
        Processor.clockCycles += 20;

        int addr = address.getSigned();
        int index = searchAddresses(addr);

        if (index == -1){
            // Already added 20, so we only need to add 330 cycles.
            Processor.clockCycles += 330;

            // Get the next cache group in the rotation. Makes a loop from 0-3 infinitly.
            index = nextCache++ % CACHE_COUNT;
            addresses[index] = addr;

            for(int i = 0; i<CACHE_SIZE && addr + i < MainMemory.MEM_SIZE; i++){
                cache[index][i].copy(MainMemory.read(new Word(addr + i)));
            }
        }

        return cache[index][addr - addresses[index]];
    }

    /**
     * Writes a value to the specified address in memory.
     * 
     * Costs 50 cycles.
     * 
     * @param address The address to be writen to.
     * @param value The new value for the speficied address.
     * @throws Exception
     */
    public static void write(Word address, Word value) throws Exception{

        Processor.clockCycles += 50;

        int addr = address.getSigned();
        int index = searchAddresses(addr);

        // If it exists, update it.
        if (index != -1){
            cache[index][addr - addresses[index]] = value;
        }

        MainMemory.write(address, value);
    }

    /**
     * Updates the InstructionCache cache.
     * 
     * Costs 50 cycles on a hit and 400 on a miss.
     * 
     * @param address The address of the desired Instruction.
     * @return A tuple containing the 8 words, and the start address for the 8 words.
     * @throws Exception
     */
    public static Tuple getInstructions(Word address) throws Exception{
        // Only add 40 because InstructionCache already adds 10.
        Processor.clockCycles += 40;
        
        int addr = address.getSigned();
        int index = searchAddresses(addr);

        if (index == -1){
            // Add another 350 to get to 400.
            Processor.clockCycles += 350;

            // Get the next cache. Loops from 0-3 infinitly (or at least infintly enough).
            index = nextCache++ % CACHE_COUNT;
            addresses[index] = addr;

            for(int i = 0; i<CACHE_SIZE && addr + i < MainMemory.MEM_SIZE; i++){
                cache[index][i].copy(MainMemory.read(new Word(addr + i)));
            }
        }

        return new Tuple(cache[index], addresses[index]);
    }

    /**
     * Basicly a struct that I can pass back and forth.
     */
    public static class Tuple{
        public Word[] words;
        public int address;

        public Tuple(Word[] words, int address){
            this.words = words;
            this.address = address;
        }
    }

    /**
     * Checks if there is already a cache with the desired Memory.
     * 
     * @param address The address of the desired Word in memory.
     * @return The cache index of the desired Word, or -1 if it's not found.
     */
    public static int searchAddresses(int address){

        int index = -1;

        for(int i = 0; i<CACHE_COUNT; i++){
            // Check if the address is within 8 Words after a starting address.
            if (address >= addresses[i] && address < addresses[i] + CACHE_SIZE){
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * Clears the cache for use by another process.
     */
    public static void clear(){

        for(int i = 0; i<CACHE_COUNT; i++){
            // Empty caches
            for(int j=0; j<CACHE_SIZE; j++){
                cache[i][j] = new Word();
            }

            // Empty addresses.
            addresses[i] = -1 - CACHE_SIZE;
        }
    }
}