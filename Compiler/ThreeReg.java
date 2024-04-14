package Compiler;

public class ThreeReg extends TwoReg{

    protected static int IMM_SIZE = 8;

    private int rs1;

    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1, int imm){
        super(code, rd, func, rs2);
        this.rs1 = rs1;
        this.imm = imm;  // Redo to make sure it's the right size.
    }

    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1){
        super(code, rd, func, rs2);
        this.rs1 = rs1;
        this.imm = 0;                  // Redo to make sure its the right size
    }

    public ThreeReg(TwoReg instruction, int rs1){
        super(instruction);
        this.rs1 = rs1;
    }

    public String toInstruction() {
        return immToString(imm, IMM_SIZE) + regMap[rs1] + regMap[rs2] + funcToString[func.ordinal()] + regMap[rd] + opToString[op.ordinal()] + "11";
    }
}
