package Compiler;

import java.util.Optional;

public class Token {
    
    public enum Type{
        BRANCH,     CALL,       LOAD,       MATH,       POP,        PUSH,       STORE,
        COPY,       HAULT,      JUMP,       RETURN,     PEEK,       INTERRUPT,

        ADD,        SUBT,       MULT,       AND,        OR,         XOR,        NOT,        
        EQ,         NE,         GT,         LT,         GE,         LE,
        RIGHT_SHIFT,LEFTSHIFT,

        REGISTER,   NUMER
    }

    private Type type;
    private Optional<Integer> value;

    public Token(Type type){
        this.type = type;
        this.value = Optional.empty();
    }

    public Token(Type type, int value){
        this.type = type;
        this.value = Optional.of(value);
    }
}
