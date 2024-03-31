package Compiler;

public class OneReg extends Instruction{

    protected static final int IMM_SIZE = 18;

    private String func;
    private String rd;

    public OneReg(Instruction.OpCode code, int rd, Instruction.Function func, int imm){
        this.op = opToString[code.ordinal()] + "01";
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
        this.imm = immToString(imm, IMM_SIZE);
    }

    public OneReg(Instruction.OpCode code, int rd, Instruction.Function func){
        this.op = opToString[code.ordinal()] + "01";
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
        this.imm = "000000000000000000";
    }

    public String toInstruction() {
        return imm + func + rd + op;
    }    
}
