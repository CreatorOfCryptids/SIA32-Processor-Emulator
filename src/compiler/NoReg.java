package compiler;

public class NoReg extends Instruction{

    protected static final int IMM_SIZE = 27;

    /**
     * Constructor.
     * 
     * @param code The OpCode of the Instruction.
     * @param imm The immediate value of the instruction.
     */
    public NoReg(Instruction.OpCode code, int imm){
        this.op = code;
        this.imm = imm;
    }

    /**
     * Constructor.
     * 
     * @param code The OpCode of the Instruction.
     */
    public NoReg(Instruction.OpCode code){
        this.op = code;
        this.imm = 0;
    }

    /**
     * Copy Constructor.
     * 
     * @param instruction The Old Instruction.
     */
    public NoReg(NoReg instruction){
        this.op = instruction.op;
        this.imm = instruction.imm;
    }

    public String toInstruction() {
        return immToString(imm, IMM_SIZE) + opToString[op.ordinal()] + "00";
    }
}
