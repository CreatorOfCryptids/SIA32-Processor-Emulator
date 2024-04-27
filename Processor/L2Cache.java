package Processor;

public class L2Cache {
    
    private static final int GROUP_COUNT = 4;
    private static final int GROUP_SIZE = 8;
    private static Word[][] cache = new Word[GROUP_COUNT][GROUP_SIZE];
    private static int[] groupAddresses = new int[]{-9,-9,-9,-9};
    private static int nextCacheGroup = 0;

    public static Word read(Word addressWord) throws Exception{
        int address = addressWord.getSigned();

        int index = -1;
        for(int i = 0; i<GROUP_COUNT; i++){
            if (address <= groupAddresses[i] && address < groupAddresses[i] + 8){
                index = i;
                break;
            }
        }

        // The desired Word is not in L2.
        if (index == -1){
            Processor.clockCycles += 350;   // Fetching from Memory always costs 350.
            int nextGroup = nextCacheGroup++ % GROUP_COUNT; // Cycle thru each cache.
            groupAddresses[nextGroup] = address;

            for(int i = 0; i<GROUP_SIZE; i++){
                Processor.clockCycles -= 300;   // Cancel out read cost of MainMemory.
                cache[nextGroup][i] = MainMemory.read(new Word(address + 1));
            }
        }
        
        Processor.clockCycles += 20;    // Reading from L2 always costs 20.

        return cache[index][address - groupAddresses[index]];
    }

    public static Word readInstruction(Word addressWord) throws Exception{
        // Reading from L2 on an InstructionCache miss costs 50 cycles. This makes the numers work out.
        Processor.clockCycles += 20;    
        return read(addressWord);
    }
}
