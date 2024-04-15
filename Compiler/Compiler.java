package Compiler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Compiler {

    private String[] input;

    /**
     * Compiles the assembly source code in the passed file.
     * 
     * @param args 
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{

        Compiler comp;

        if (args.length == 1)
            comp = new Compiler(args[0]);
        else
            throw new Exception("INCORRECT ARGS: Missing inputFileName.");
        
        for(String s : comp.compile())
            System.out.println(s);
    }

    /**
     * Constructor.
     * 
     * @param inputFileName The name of the file that contains the assembly source code.
     * @throws Exception
     */
    public Compiler(String inputFileName) throws Exception{
        Path myPath = Paths.get(inputFileName);
        input = ((String[])Files.readAllLines(myPath).toArray());
    }

    /**
     * Compiles the input data into SIA32 binary instructions.
     * 
     * @return An array of String containing the compiled SIA32 bits in '0's and '1's
     * @throws Exception
     */
    public String[] compile() throws Exception{
        Lexer lexer = new Lexer(input);

        Assembler asb = new Assembler(lexer.lex());

        LinkedList<Instruction> instructionList = asb.assemble();

        String[] retval = new String[instructionList.size()];

        for(int i = 0; i< input.length; i++)
            retval[i] = instructionList.get(i).toInstruction();

        return retval;
    }
}
