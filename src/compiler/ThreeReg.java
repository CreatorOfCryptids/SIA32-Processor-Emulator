package compiler;

public class ThreeReg extends TwoReg{

    protected static int IMM_SIZE = 8;

    private int rs1;

    /**
     * Constructor.
     * 
     * @param code The OpCode of the new Instruction.
     * @param rd The assigment register of the new Instruction.
     * @param func The function of the new Instruction.
     * @param rs2 The secondary register of the new Instruction.
     * @param rs1 The third register of the new Instruction.
     * @param imm The immediate value of the new Instruction.
     */
    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1, int imm){
        super(code, rd, func, rs2);
        this.rs1 = rs1;
        this.imm = imm;
    }

    /**
     * Constructor.
     * 
     * @param code The OpCode of the new Instruction.
     * @param rd The assigment register of the new Instruction.
     * @param func The function of the new Instruction.
     * @param rs2 The secondary register of the new Instruction.
     * @param rs1 The third register of the new Instruction.
     */
    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1){
        super(code, rd, func, rs2);
        this.rs1 = rs1;
        this.imm = 0;
    }

    /**
     * Constructor.
     * 
     * @param instruction The old instruction to be copied.
     * @param rs1 The third register of the new Instruction.
     */
    public ThreeReg(TwoReg instruction, int rs1){
        super(instruction);
        this.rs1 = rs1;
    }

    public String toInstruction() {
        return immToString(imm, IMM_SIZE) + regMap[rs1] + regMap[rs2] + funcToString[func.ordinal()] + regMap[rd] + opToString[op.ordinal()] + "11";
    }
}
