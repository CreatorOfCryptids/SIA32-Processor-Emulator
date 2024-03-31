package Compiler;

public class TwoReg extends Instruction{

    protected static final int IMM_SIZE = 10;

    private String func;
    private String rd;
    private String rs2;

    public TwoReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int imm){
        this.op = opToString[code.ordinal()] + "10";
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
        this.rs2 = regMap[rs2];
        this.imm = immToString(imm, IMM_SIZE);
    }

    public TwoReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2){
        this.op = opToString[code.ordinal()] + "10";
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
        this.rs2 = regMap[rs2];
        this.imm = "0000000000";
    }

    public String toInstruction() {
        return imm + rs2 + func + rd + op;
    }
}
