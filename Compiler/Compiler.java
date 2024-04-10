package Compiler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Compiler {

    private String[] input;

    public static void main(String[] args) throws Exception{

        Compiler comp;

        if (args.length >0 )
            comp = new Compiler(args[0]);
        else
            throw new Exception("INCORRECT ARGS: Missing inputFileName.");
        
        for(String s : comp.compile()){
            System.out.println(s);
        }
    }

    public Compiler(String inputFileName) throws Exception{
        Path myPath = Paths.get(inputFileName);
        input = ((String[])Files.readAllLines(myPath).toArray());

    }

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
