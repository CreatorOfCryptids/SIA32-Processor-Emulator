import Compiler.*;
import Processor.*;
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

        for(int i : randoms){
            i = rand.nextInt(1024);
            sum += i;
            System.out.println(i);
        }

        System.out.println("Generated sum: " + sum + "\n");

        String[] sum20IntArray = new String[]{
            "",
            ""
        };
        runTest(sum20IntArray, "IntArray", sum);
        
        String[] sum20IntLinkedList = new String[]{
            "",
            ""
        };        
        runTest(sum20IntLinkedList, "IntArray", sum);

        String[] sum20IntReverse = new String[]{
            "",
            ""
        };
        runTest(sum20IntReverse, "IntArray", sum);
    }

    private static void runTest(String[] input, String testName, int sum) throws Exception{
        System.out.println("Testing "+ testName+"...\n");

        Compilerer comp = new Compilerer(input);
        Processor p = new Processor(comp.compile());
        p.run();
        
        int processorSum = p.TESTgetRegister(31).getSigned();

        System.out.println("Sum: " + processorSum);
        if (processorSum == sum)
            System.out.println("Great success!!! :D");
        else
            System.out.println("Failure :(");

        System.out.println("");
    }
}
