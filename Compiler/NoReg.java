package Compiler;

public class NoReg extends Instruction{

    protected static final int IMM_SIZE = 27;

    public NoReg(Instruction.OpCode code, int imm){
        this.op = code;
        this.imm = imm;
    }

    public NoReg(Instruction.OpCode code){
        this.op = code;
        this.imm = 0;
    }

    public NoReg(NoReg instruction){
        this.op = instruction.op;
        this.imm = instruction.imm;
    }

    public String toInstruction() {
        return immToString(imm, IMM_SIZE) + opToString[op.ordinal()] + "00";
    }
}
