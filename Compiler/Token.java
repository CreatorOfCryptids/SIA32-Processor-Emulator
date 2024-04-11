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

    public Token(Type type, int value){
        this.type = type;
        this.value = Optional.of(value);
    }

    public Token(Type type){
        this.type = type;
        this.value = Optional.empty();
    }

    public Type getType(){
        return type;
    }

    public Optional<Integer> getValue(){
        return value;
    }

    public String toString(){
        return type.toString() + "" + (value.isPresent() ? "<" + value.get() + ">" : "");
    }
}
