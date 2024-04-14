package Compiler;

public class NoReg extends Instruction{

    protected static final int IMM_SIZE = 27;

    public NoReg(Instruction.OpCode code, int imm){
        this.op = opToString[code.ordinal()];
        this.imm = immToString(imm, IMM_SIZE);
    }

    public NoReg(Instruction.OpCode code){
        this.op = opToString[code.ordinal()];
        this.imm = immToString(0, IMM_SIZE);
    }

    public String toInstruction() {
        return imm + op + "00";
    }
}
