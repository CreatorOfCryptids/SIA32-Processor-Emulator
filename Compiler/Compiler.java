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
<<<<<<< HEAD
=======

>>>>>>> 5308431 (Started on the compiler.)
        Path myPath = Paths.get(inputFileName);
        input = ((String[])Files.readAllLines(myPath).toArray());

    }

<<<<<<< HEAD
    public String[] compile() throws Exception{
=======
    public String[] compile(){
>>>>>>> 5308431 (Started on the compiler.)
        Lexer lexer = new Lexer(input);

        Assembler asb = new Assembler(lexer.lex());

<<<<<<< HEAD
        LinkedList<Instruction> instructionList = asb.assemble();

        String[] retval = new String[instructionList.size()];

        for(int i = 0; i< input.length; i++)
            retval[i] = instructionList.get(i).toInstruction();
=======
        Instruction[] instructionList = asb.assemble();

        String[] retval = new String[input.length];

        for(int i = 0; i< input.length; i++)
            retval[i] = instructionList[i].toInstruction();
>>>>>>> 5308431 (Started on the compiler.)

        return retval;
    }
}
