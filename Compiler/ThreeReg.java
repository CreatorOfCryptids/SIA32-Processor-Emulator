package Compiler;

public class ThreeReg extends Instruction{

    protected static int IMM_SIZE = 8;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 94b3465 (Fixed visibility in ThreeReg)
    private String rd;
    private String func;
    private String rs2;
    private String rs1;
<<<<<<< HEAD

    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1, int imm){
=======
    String rd;
    String func;
    String rs2;
    String rs1;

    ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1, int imm){
>>>>>>> d35383a (Created the three instruction class.)
=======

    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1, int imm){
>>>>>>> 94b3465 (Fixed visibility in ThreeReg)
        this.op = opToString[code.ordinal()] + "11";
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
        this.rs2 = regMap[rs2];
        this.rs1 = regMap[rs1];
        this.imm = immToString(imm, IMM_SIZE);
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1){
=======
    ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1){
>>>>>>> d35383a (Created the three instruction class.)
=======
    public ThreeReg(Instruction.OpCode code, int rd, Instruction.Function func, int rs2, int rs1){
>>>>>>> 94b3465 (Fixed visibility in ThreeReg)
        this.op = opToString[code.ordinal()] + "11";
        this.rd = regMap[rd];
        this.func = funcToString[func.ordinal()];
        this.rs2 = regMap[rs2];
        this.rs1 = regMap[rs1];
<<<<<<< HEAD
        this.imm = "00000000";
=======
        this.imm = immToString(0, IMM_SIZE);
>>>>>>> d35383a (Created the three instruction class.)
    }

    public String toInstruction() {
        return imm + rs1 + rs2 + func + rd + op;
    }
}
