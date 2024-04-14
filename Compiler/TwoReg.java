package Compiler;

public class TwoReg extends OneReg{

    protected static final int IMM_SIZE = 10;

    protected int rs2;

    public TwoReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int imm){
        super(code, rd, func, imm);
        this.rs2 = rs2;
        this.imm = imm;  // Redo, to make sure it's the right size.
    }

    public TwoReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2){
        super(code, rd, func);
        this.rs2 = rs2;
        this.imm = 0;
    }

    public TwoReg(OneReg instruction, int rs2){
        super(instruction);
        this.rs2 = rs2;
    }

    public TwoReg(TwoReg instruction){
        super(instruction.op, instruction.rd, instruction.func,  instruction.imm);
        rs2 = instruction.rs2;
    }

    public String toInstruction() {
        return immToString(imm, IMM_SIZE) + regMap[rs2] + func + regMap[rd] + opToString[op.ordinal()] + "10";
    }
}
