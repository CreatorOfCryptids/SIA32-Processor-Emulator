package Compiler;

public class TwoReg extends OneReg{

    protected static final int IMM_SIZE = 10;

    protected String rs2;

    public TwoReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int imm){
        super(code, rd, func, imm);
        this.rs2 = regMap[rs2];
        this.imm = immToString(imm, IMM_SIZE);  // Redo, to make sure it's the right size.
    }

    public TwoReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2){
        super(code, rd, func);
        this.rs2 = regMap[rs2];
        this.imm = "0000000000";
    }

    public String toInstruction() {
        return imm + rs2 + func + rd + op + "10";
    }
}
