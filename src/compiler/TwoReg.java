package compiler;

public class TwoReg extends OneReg{

    protected static final int IMM_SIZE = 13;

    protected int rs2;

    /**
     * Constructor.
     * 
     * @param code The OpCode of the new Instruction.
     * @param rd The assigment register of the new Instruction.
     * @param func The function of the new Instruction.
     * @param rs2 The secondary register of the new Instruction.
     * @param imm The immediate value of the new Instruction.
     */
    public TwoReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int imm){
        super(code, rd, func, imm);
        this.rs2 = rs2;
        this.imm = imm;  // Redo, to make sure it's the right size.
    }

    /**
     * Constructor.
     * 
     * @param code The OpCode of the new Instruction.
     * @param rd The assigment register of the new Instruction.
     * @param func The function of the new Instruction.
     * @param rs2 The secondary register of the new Instruction.
     */
    public TwoReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2){
        super(code, rd, func);
        this.rs2 = rs2;
        this.imm = 0;
    }

    /**
     * Constructor.
     * 
     * @param instruction The old instruction to be copied.
     * @param rs2 The secondary register of the new Instruction.
     */
    public TwoReg(OneReg instruction, int rs2){
        super(instruction);
        this.rs2 = rs2;
    }

    /**
     * Constructor.
     * 
     * @param instruction The old instruction to be copied.
     */
    public TwoReg(TwoReg instruction){
        super(instruction.op, instruction.rd, instruction.func,  instruction.imm);
        rs2 = instruction.rs2;
    }

    public String toInstruction() {
        return immToString(imm, IMM_SIZE) + regMap[rs2] + funcToString[func.ordinal()] + regMap[rd] + opToString[op.ordinal()] + "10";
    }
}
