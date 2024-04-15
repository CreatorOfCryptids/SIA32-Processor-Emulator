package Compiler;

import java.util.Optional;

public class Token {
    
    public enum Type{
        MATH, BRANCH, HAULT, COPY, JUMP, CALL, PUSH, POP, LOAD, STORE, RETURN, PEEK, INTERRUPT, 
        AND, OR, XOR, NOT, LEFT_SHIFT, RIGHT_SHIFT, ADD, SUBTRACT, MULTIPLY, 
        EQ, NE, LT, GE, GT, LE,
        REGISTER, IMMEDIATE, NEW_LINE, 
    }

    private Type type;
    private Optional<Integer> value;

    /**
     * Constructor
     * 
     * @param type The Token.Type of the new Token.
     * @param value The integer value of the new Token (Only used for registers and immediate)
     */
    public Token(Type type, int value){
        this.type = type;
        this.value = Optional.of(value);
    }

    /**
     * Value free Constructor.
     * 
     * @param type The Token.Type of the new Token.
     */
    public Token(Type type){
        this.type = type;
        this.value = Optional.empty();
    }

    /**
     * Gets the Token's Type
     * 
     * @return The type of the Token.
     */
    public Type getType(){
        return type;
    }

    /**
     * Gets the value of the Token
     * 
     * @return An Optional<Integer> containing the value of the Token.
     */
    public Optional<Integer> getValue(){
        return value;
    }

    /**
     * Returns a String to view the tokens for easy debugging.
     * 
     * @return A String containing the important information in the Token
     */
    public String toString(){
        return type.toString() + "" + (value.isPresent() ? "<" + value.get() + ">" : "");
    }
}
