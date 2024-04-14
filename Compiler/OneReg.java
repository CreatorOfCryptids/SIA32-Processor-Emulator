package Compiler;

public class OneReg extends NoReg{

    protected static final int IMM_SIZE = 18;

    protected String func;
    protected String rd;

    public OneReg(Instruction.OpCode code, int rd, Instruction.Function func, int imm){
        super(code, imm);
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
    }

    public OneReg(Instruction.OpCode code, int rd, Instruction.Function func){
        super(code);
        this.op = opToString[code.ordinal()];
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
        this.imm = "000000000000000000";
    }

    public String toInstruction() {
        return imm + func + rd + op  + "01";
    }    
}
