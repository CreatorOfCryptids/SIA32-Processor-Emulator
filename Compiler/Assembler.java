package Compiler;

import java.util.LinkedList;

public class Assembler {
    
    private LinkedList<Token> tokenList;
    private LinkedList<Instruction> instructionList;

    public Assembler(LinkedList<Token> tokenList){
        this.tokenList = tokenList;
        this.instructionList = new LinkedList<Instruction>();
    }

    public LinkedList<Instruction> assemble(){
        return null;
    }
}
