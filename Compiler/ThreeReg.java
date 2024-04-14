package Compiler;

public class ThreeReg extends TwoReg{

    protected static int IMM_SIZE = 8;
    private String rs1;

    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1, int imm){
        super(code, rd, func, rs2);
        this.rs1 = regMap[rs1];
        this.imm = immToString(imm, IMM_SIZE);  // Redo to make sure it's the right size.
    }

    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1){
        super(code, rd, func, rs2);
        this.rs1 = regMap[rs1];
        this.imm = "00000000";                  // Redo to make sure its the right size
    }

    public String toInstruction() {
        return imm + rs1 + rs2 + func + rd + op + "11";
    }
}
