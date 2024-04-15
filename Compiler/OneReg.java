package Compiler;

public class OneReg extends NoReg{

    protected static final int IMM_SIZE = 18;

    protected Instruction.Function func;
    protected int rd;

    /**
     * Constructor.
     * 
     * @param code The OpCode of the new Instruction.
     * @param rd The assigment register of the new Instruction.
     * @param func The function of the new Instruction.
     * @param imm The assigment register of the new Instruction.
     */
    public OneReg(Instruction.OpCode code, int rd, Instruction.Function func, int imm){
        super(code, imm);
        this.rd = rd;
        this.func = func;
        this.imm = imm;
    }

    /**
     * Constructor.
     * 
     * @param code The OpCode of the new Instruction.
     * @param rd The assigment register of the new Instruction.
     * @param func The function of the new Instruction.
     * @param imm The assigment register of the new Instruction.
     */
    public OneReg(Instruction.OpCode code, int rd, Instruction.Function func){
        super(code);
        this.rd = rd;
        this.func = func;
        this.imm = 0;
    }

    /**
     * Constructor.
     * 
     * @param instruction The old instruction to be copied.
     * @param func The function of the new Instruction.
     * @param imm The assigment register of the new Instruction.
     */
    public OneReg(NoReg instruction, int rd, Instruction.Function func){
        super(instruction);
        this.rd = rd;
        this.func = func;
        this.imm = 0;
    }

    /**
     * Constructor.
     * 
     * @param instruction The old instruction to be copied.
     */
    public OneReg(OneReg instruction){
        super(instruction.op, instruction.imm);
        this.func = instruction.func;
        this.rd = instruction.rd;
    }

    public String toInstruction() {
        return immToString(imm, IMM_SIZE) + funcToString[func.ordinal()] + regMap[rd] + opToString[op.ordinal()]  + "01";
    }
}
