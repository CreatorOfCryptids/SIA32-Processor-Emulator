package Compiler;

public class OneReg extends NoReg{

    protected static final int IMM_SIZE = 18;

    protected Instruction.Function func;
    protected int rd;

    public OneReg(Instruction.OpCode code, int rd, Instruction.Function func, int imm){
        super(code, imm);
        this.rd = rd;
        this.func = func;
        this.imm = imm;
    }

    public OneReg(Instruction.OpCode code, int rd, Instruction.Function func){
        super(code);
        this.rd = rd;
        this.func = func;
        this.imm = 0;
    }

    public OneReg(NoReg instruction, int rd, Instruction.Function func){
        super(instruction);
        this.rd = rd;
        this.func = func;
        this.imm = 0;
    }

    public OneReg(OneReg instruction){
        super(instruction.op, instruction.imm);
    }

    public String toInstruction() {
        return immToString(imm, IMM_SIZE) + funcToString[func.ordinal()] + regMap[rd] + opToString[op.ordinal()]  + "01";
    }
}
