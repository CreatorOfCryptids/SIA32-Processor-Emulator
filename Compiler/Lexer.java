package Compiler;

import java.util.HashMap;
import java.util.LinkedList;

public class Lexer {
    
    private InputManager input;
    private LinkedList<Token> tokens;
    private HashMap<String, Token.Type> tokenMap;

    public Lexer(String fileInput){
        this.input = new InputManager(fileInput);
        this.tokens = new LinkedList<Token>();
        this.tokenMap = new HashMap<String, Token.Type>();
    }

}
