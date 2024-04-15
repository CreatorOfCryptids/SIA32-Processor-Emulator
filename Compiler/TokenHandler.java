package Compiler;

import java.util.LinkedList;
import java.util.Optional;

public class TokenHandler {
    
    private LinkedList<Token> tokens;
    private int lineNumber;
    private int index;

    public TokenHandler(LinkedList<Token> tokens){
        this.tokens = tokens;
        this.lineNumber = 0;
        this.index = 0;
    }

    /**
     * The peek() method.
     * 
     * @param i 
     * @return Optional with the i'th token in the list.
     */
    Optional<Token> peek(int i){
        if (tokens.size() > i)
            return Optional.of(tokens.get(i));
        else
            return Optional.empty();
    }

    /**
     * The peek() method.
     * 
     * @return Optional with the top token in the list.
     */
    Optional<Token> peek(){
        if (tokens.size() > 0)
            return Optional.of(tokens.get(0));
        else
            return Optional.empty();
    }

    /**
     * The moreTokens() method.
     * 
     * @return True if the list has more tokens. False otherwize.
     */
    boolean moreTokens(){
        // See if the Linked List has entries.
        if (tokens.size() > 0)
            return true;
        else 
            return false;
    }

    /**
     * The matchAndRemove() method.
     * @param type A type of token to compare to the top token in the list.
     * @return Optional with the top token if the types match. 
     *         Empty Optional if else.
     */
    Optional<Token> matchAndRemove(Token.Type type){
        if (tokens.size() > 0 && tokens.get(0).getType() == type){
            // move lineNumber and index along for better debugging.
            if(tokens.get(0).getType() == Token.Type.NEW_LINE){
                lineNumber++;
                index = 0;
            }
            else
                index++;
            
            return Optional.of(tokens.pop());
        }
        else{
            return Optional.empty();
        }
    }

    /**
     * Swallows NEW_LINE tokens until a different token is found.
     * 
     * @return True if there is at least one seperator, false otherwize.
     */
    public boolean acceptNewLines(){
        boolean foundSeperators = matchAndRemove(Token.Type.NEW_LINE).isPresent();
        while(matchAndRemove(Token.Type.NEW_LINE).isPresent());
        return foundSeperators;
    }

    /**
     * Removes and returns the next token.
     * 
     * @return An Optional with the top token in the list.
     */
    public Optional<Token> swallow(){
        if (tokens.size() > 0){
            index++;
            return Optional.of(tokens.pop());
        }
            
        else 
            return Optional.empty();
    }

    /**
     * Returns a string containing position information to allow for easier debuging for future users of this compiler :)
     * 
     * @return A string containing the line and index of the last processed token.
     */
    String getErrorPosition(){
        return "Line: " + lineNumber + " Index: " + index;
    }
}
