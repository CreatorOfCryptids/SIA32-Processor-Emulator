package Compiler;

public class ThreeReg extends Instruction{

    protected static int IMM_SIZE = 8;

    private String rd;
    private String func;
    private String rs2;
    private String rs1;

    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1, int imm){
        this.op = opToString[code.ordinal()] + "11";
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
        this.rs2 = regMap[rs2];
        this.rs1 = regMap[rs1];
        this.imm = immToString(imm, IMM_SIZE);
    }

    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1){
        this.op = opToString[code.ordinal()] + "11";
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
        this.rs2 = regMap[rs2];
        this.rs1 = regMap[rs1];
        this.imm = "00000000";
    }

    public String toInstruction() {
        return imm + rs1 + rs2 + func + rd + op;
    }
}
