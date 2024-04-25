import Compiler.*;
//import Processor.*;
import java.util.Random;

public class ClockCycleTest {

    private static int sum = 0;

    /**
     * Runs three tests to see the difference in clock cycles.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception{

        System.out.println("Testing the processor by summing 20 random integers:");
        System.out.println("Generating random integers:");
        
        Random rand = new Random();
        int[] randoms = new int[20];

        for(int i = 0; i<randoms.length; i++){
            randoms[i] = rand.nextInt(1024);
            sum += randoms[i];
            System.out.println(randoms[i]);
        }

        System.out.println("Generated sum: " + sum);

        String[] sum20IntArray = new String[]{

            "Copy R7 700",
            "Copy R1 1",
            // Store in memory.
            "Store R7 " + randoms[0],
            "Math ADD R7 R1",
            "Store R7 " + randoms[1],
            "Math ADD R7 R1",
            "Store R7 " + randoms[2],
            "Math ADD R7 R1",
            "Store R7 " + randoms[3],
            "Math ADD R7 R1",
            "Store R7 " + randoms[4],
            "Math ADD R7 R1",
            "Store R7 " + randoms[5],
            "Math ADD R7 R1",
            "Store R7 " + randoms[6],
            "Math ADD R7 R1",
            "Store R7 " + randoms[7],
            "Math ADD R7 R1",
            "Store R7 " + randoms[8],
            "Math ADD R7 R1",
            "Store R7 " + randoms[9],
            "Math ADD R7 R1",
            "Store R7 " + randoms[10],
            "Math ADD R7 R1",
            "Store R7 " + randoms[11],
            "Math ADD R7 R1",
            "Store R7 " + randoms[12],
            "Math ADD R7 R1",
            "Store R7 " + randoms[13],
            "Math ADD R7 R1",
            "Store R7 " + randoms[14],
            "Math ADD R7 R1",
            "Store R7 " + randoms[15],
            "Math ADD R7 R1",
            "Store R7 " + randoms[16],
            "Math ADD R7 R1",
            "Store R7 " + randoms[17],
            "Math ADD R7 R1",
            "Store R7 " + randoms[18],
            "Math ADD R7 R1",
            "Store R7 " + randoms[19],  // 41
            // Loop.
            "Copy R7 700",              // 42 Reset the start address.
            "Copy r10 20",              // 43 Store the arrayLength.
            "Copy R2 0",                // 44 Store the current array index.
            "Branch EQ R2 R10 4",       // 45 If index > length, jump NUM Spots
            "Load R30 R7 R2",           // 46 Load temp <- mem[address + index]
            "Math Add R31 R30",         // 47 sum += temp
            "Math add R2 R1",           // 48 index++
            "Jump 44",                  // 49 continue;
            "Hault"                     // 50 Hault
        };

        runTest(sum20IntArray, "IntArray");
        
        String[] sum20IntLinkedList = new String[]{

            "Copy R7 700",              // List start.
            "Copy R1 1",                // increment.
            "Copy R8 705",              // Next pointer.
            "Copy R2 5",
            // Store in memory.
            "Store R7 " + randoms[0],   
            "Store R7 R8 R1",
            "Math Add R7 R2",
            "Math Add R8 R2",
            "Store R7 " + randoms[1],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[2],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[3],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[4],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[5],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[6],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[7],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[8],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[9],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[10],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[11],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[12],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[13],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[14],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[15],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[16],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[17],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[18],
            "Store R7 R8 R1",
            "Math ADD R7 R2",
            "MATH ADD R8 R2",
            "Store R7 " + randoms[19],
            "Store R7 R0 R1",

            // Sum
            "",

            "Hault"
        };        
        runTest(sum20IntLinkedList, "IntList");

        String[] sum20IntReverse = new String[]{
            "Copy R7 700",
            "Copy R1 1",
            // Store in memory.
            "Store R7 " + randoms[0],
            "Math ADD R7 R1",
            "Store R7 " + randoms[1],
            "Math ADD R7 R1",
            "Store R7 " + randoms[2],
            "Math ADD R7 R1",
            "Store R7 " + randoms[3],
            "Math ADD R7 R1",
            "Store R7 " + randoms[4],
            "Math ADD R7 R1",
            "Store R7 " + randoms[5],
            "Math ADD R7 R1",
            "Store R7 " + randoms[6],
            "Math ADD R7 R1",
            "Store R7 " + randoms[7],
            "Math ADD R7 R1",
            "Store R7 " + randoms[8],
            "Math ADD R7 R1",
            "Store R7 " + randoms[9],
            "Math ADD R7 R1",
            "Store R7 " + randoms[10],
            "Math ADD R7 R1",
            "Store R7 " + randoms[11],
            "Math ADD R7 R1",
            "Store R7 " + randoms[12],
            "Math ADD R7 R1",
            "Store R7 " + randoms[13],
            "Math ADD R7 R1",
            "Store R7 " + randoms[14],
            "Math ADD R7 R1",
            "Store R7 " + randoms[15],
            "Math ADD R7 R1",
            "Store R7 " + randoms[16],
            "Math ADD R7 R1",
            "Store R7 " + randoms[17],
            "Math ADD R7 R1",
            "Store R7 " + randoms[18],
            "Math ADD R7 R1",
            "Store R7 " + randoms[19],
            // Loop.
            "Copy R7 699",              // 42 Reset the start address.
            "Copy r10 0",              // 43 Store the arrayLength.
            "Copy R2 20",                // 44 Store the current array index.
            "Branch EQ R2 R10 4",       // 45 If index > length, jump NUM Spots
            "Load R30 R7 R2",           // 46 Load temp <- mem[address + index]
            "Math Add R31 R30",         // 47 sum += temp
            "Math SUB R2 R1",           // 48 index++
            "Jump 44",                  // 49 continue;
            "Hault"                     // 50 Hault
        };
        runTest(sum20IntReverse, "ReverseIntArray");
    }

    /**
     * Runs the provided program, and prints the necessary outputs.
     * 
     * @param input The program file.
     * @param testName The title of the program.
     * @param sum 
     * @throws Exception
     */
    private static void runTest(String[] input, String testName) throws Exception{
        System.out.println("\nTesting "+ testName+"...\n");

        Compilerer comp = new Compilerer(input);
        Processor p = new Processor(comp.compile());
        p.run();
        
        int processorSum = p.TESTgetRegister(31).getSigned();

        System.out.println("Sum: " + processorSum);
        if (processorSum == sum)
            System.out.println("Great success!!! :D");
        else
            System.out.println("Failure :(");

        System.out.println("Clock cycles: " + Processor.clockCycles);
        Processor.clockCycles = 0;
    }
}
