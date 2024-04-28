package Processor;

public class L2Cache{
    public static int CACHE_COUNT = 4;
    public static int CACHE_SIZE = 8;
    private static Word[][] cache = new Word[CACHE_COUNT][CACHE_SIZE];
    private static int[] addresses = new int[CACHE_COUNT];
    private static int nextCache = 0;

    public static Word read(Word address) throws Exception{

        int addr = address.getSigned();

        Processor.clockCycles += 20;

        int index = searchAddresses(addr);

        if (index == -1){

            Processor.clockCycles += 330;

            index = nextCache++ % CACHE_COUNT;
            addresses[index] = addr;

            for(int i = 0; i<CACHE_SIZE && addr + i < MainMemory.MEM_SIZE; i++){
                cache[index][i].copy(MainMemory.read(new Word(addr + i)));
            }
        }

        return cache[index][addr- addresses[index]];
    }

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

    public static Tuple getInstructions(Word address) throws Exception{
        int addr = address.getSigned();

        Processor.clockCycles += 40;

        int index = searchAddresses(addr);

        if (index == -1){
            Processor.clockCycles += 350;

            index = nextCache++ % CACHE_COUNT;

            addresses[index] = addr;

            for(int i = 0; i<CACHE_SIZE && addr + i < MainMemory.MEM_SIZE; i++){
                cache[index][i].copy(MainMemory.read(new Word(addr + i)));
            }
        }

        return new Tuple(cache[index], addresses[index]);
    }

    public static class Tuple{
        public Word[] words;
        public int address;

        public Tuple(Word[] words, int address){
            this.words = words;
            this.address = address;
        }
    }

    public static int searchAddresses(int address){

        int index = -1;

        for(int i = 0; i<CACHE_COUNT; i++){
            if (address >= addresses[i] && address < addresses[i] + CACHE_SIZE){
                index = i;
                break;
            }
        }

        return index;
    }

    public static void clear(){
        for(int i = 0; i<CACHE_COUNT; i++){
            for(int j=0; j<CACHE_SIZE; j++){
                cache[i][j] = new Word();
            }
            addresses[i] = -9;
        }
    }
}