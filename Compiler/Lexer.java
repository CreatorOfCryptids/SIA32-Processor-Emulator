package Compiler;

import java.util.LinkedList;
import java.util.HashMap;

public class Lexer {
    
    InputHandler ih;
    HashMap<String, Token.Type> keyWordMap;
    LinkedList<Token> tokenList;

    /**
     * Constructor.
     * 
     * @param input The String[] from the file input.
     */
    public Lexer(String[] input){
        ih = new InputHandler(input);
        keyWordMap = new HashMap<String, Token.Type>();
        tokenList = new LinkedList<Token>();

        // Init keywords:
        keyWordMap.put("MATH", Token.Type.MATH);
        keyWordMap.put("BRANCH", Token.Type.BRANCH);
        keyWordMap.put("HAULT", Token.Type.HAULT);
        keyWordMap.put("COPY", Token.Type.COPY);
        keyWordMap.put("JUMP", Token.Type.JUMP);
        keyWordMap.put("CALL", Token.Type.PUSH);
        keyWordMap.put("POP", Token.Type.POP);
        keyWordMap.put("LOAD", Token.Type.LOAD);
        keyWordMap.put("STORE", Token.Type.STORE);
        keyWordMap.put("RETURN", Token.Type.RETURN);
        keyWordMap.put("PEEK", Token.Type.PEEK);
        keyWordMap.put("INTERRUPT", Token.Type.INTERRUPT);

        keyWordMap.put("AND", Token.Type.AND);
        keyWordMap.put("OR", Token.Type.OR);
        keyWordMap.put("XOR", Token.Type.XOR);
        keyWordMap.put("NOT", Token.Type.NOT);
        keyWordMap.put("LEFT", Token.Type.LEFT_SHIFT);
        keyWordMap.put("RIGHT", Token.Type.RIGHT_SHIFT);
        keyWordMap.put("ADD", Token.Type.ADD);
        keyWordMap.put("SUB", Token.Type.SUBTRACT);
        keyWordMap.put("MULT", Token.Type.MULTIPLY);

        keyWordMap.put("EQ", Token.Type.EQ);
        keyWordMap.put("NE", Token.Type.NE);
        keyWordMap.put("LT", Token.Type.LT);
        keyWordMap.put("GE", Token.Type.GE);
        keyWordMap.put("GT", Token.Type.GT);
        keyWordMap.put("LE", Token.Type.LE);
    }

    /**
     * Lexes the passed input.
     * 
     * @return A LinkedList<Token> containing the tokens from the input.
     * @throws Exception If there is an input that cannot be lexed properly.
     */
    public LinkedList<Token> lex() throws Exception{

        do{

            while(ih.moreWords()){

                String currentWord = ih.getWord().get().toUpperCase();

                // Check if it's in the map.
                if(keyWordMap.containsKey(currentWord)){
                    tokenList.add(new Token(keyWordMap.get(currentWord)));
                }
                // Check if it's a register.
                else if (currentWord.charAt(0) == 'R'){
                    // Make sure the reg index parses to an int.
                    try{
                        int registerIndex = Integer.parseInt(currentWord.substring(1));
                        tokenList.add(new Token(Token.Type.REGISTER, registerIndex));
                    }
                    catch (NumberFormatException e){
                        lexException("Unexpected register index \"" + currentWord.substring(1) + "\"");
                    }
                }
                // Check if it's a number.
                else if (Character.isDigit(currentWord.charAt(0))){
                    // Make sure it parses to an int.
                    try{
                        int number = Integer.parseInt(currentWord);
                        tokenList.add(new Token(Token.Type.IMMEDIATE, number));
                    }
                    catch (NumberFormatException e){
                        lexException("Unexpected register index \"" + currentWord.substring(1) + "\"");
                    }
                }
                else{
                    lexException("Unexpected phrase: \"" + currentWord +"\"");
                }
            }
            tokenList.add(new Token(Token.Type.NEW_LINE));
        } while(ih.nextLine());

        return tokenList;
    }

    /**
     * Throws a formatted exception specifying where the error occured in the passed input.
     * 
     * @param message Useful debugging information.
     * @throws Exception
     */
    private void lexException(String message) throws Exception{
        throw new Exception("LEX ERROR: " + message + " at line: " + ih.getLineCount() + " index: " + ih.getInLineIndex() + '.');
    }
}
