package compiler;

import java.util.LinkedList;
import java.util.Optional;

public class Assembler {
    
    private TokenHandler th;
    private LinkedList<Instruction> instructionList;

    /**
     * Constructor.
     * 
     * @param tokenList A list of Tokens to be assembled.
     */
    public Assembler(LinkedList<Token> tokenList){
        this.th = new TokenHandler(tokenList);
        this.instructionList = new LinkedList<Instruction>();
    }

    /**
     * Assembles the tokens into instructions.
     * 
     * @return A LinkedList of Instructions.
     * @throws Exception
     */
    public LinkedList<Instruction> assemble() throws Exception{
        th.acceptNewLines();
        while(th.moreTokens()){
            instructionList.add(parseInstruction());
            
            if(th.moreTokens() && th.acceptNewLines() == false)
                throw new Exception("Unexpected Token " + th.peek().get().toString() + " at " + th.getErrorPosition());
        }

        return instructionList;
    }

    /**
     * Parses an Instruction after a new line.
     * 
     * @return An Instruction containting the 
     * @throws Exception
     */
    private Instruction parseInstruction() throws Exception{

        Instruction retInst;
        // Swallow to make sure it's not still hanging around.
        Token.Type type = th.swallow().get().getType();


        if (type == Token.Type.BRANCH){
            retInst = parseFormat(Instruction.OpCode.BRANCH).get();
        }
        else if (type == Token.Type.CALL){
            if (th.peek().get().getType() == Token.Type.IMMEDIATE)
                retInst = new NoReg(Instruction.OpCode.CALL, th.matchAndRemove(Token.Type.IMMEDIATE).get().getValue().get());
            else 
                retInst = parseFormat(Instruction.OpCode.CALL).get();
        }
        else if (type == Token.Type.COPY){
            retInst = parseOneReg(Instruction.OpCode.MATH).get();

            // Copy can only have one reg, but also requires an imm.
            Optional<Integer> imm = parseImmediate();

            if (imm.isPresent())
                retInst.setImm(imm.get());
            else
                throw new Exception("Expected an immediate at " + th.getErrorPosition() + ", but was " + th.peek().get().toString() + '.');
        }
        else if (type == Token.Type.HAULT){
            retInst = new NoReg(Instruction.OpCode.MATH);
            // Adding an exit value is a debugging feature I added and now this is here optionally for backwards compatibilibty.
            Optional<Integer> imm = parseImmediate();

            if (imm.isPresent())
                retInst.setImm(imm.get());
        }
        else if (type == Token.Type.INTERRUPT){
            retInst = new NoReg(Instruction.OpCode.POP);
        }
        else if (type == Token.Type.JUMP){

            retInst = parseOneReg(Instruction.OpCode.BRANCH).get();
            // Jump can have up to one reg, but requires an imm.
            Optional<Integer> imm = parseImmediate();

            if (imm.isPresent())
                retInst.setImm(imm.get());
            else
                throw new Exception("Expected an immediate at " + th.getErrorPosition() + ", but was " + th.peek().get().toString() + '.');
        }
        else if (type == Token.Type.LOAD){
            retInst = parseFormat(Instruction.OpCode.LOAD).get();
            if (!(retInst instanceof OneReg)){  // Hierarchical inheritance. Catches no-reg.
                throw new Exception("Expected register near " + th.getErrorPosition());
            }
        }
        else if (type == Token.Type.MATH){
            retInst = parseFormat(Instruction.OpCode.MATH).get();

            if (!(retInst instanceof TwoReg || retInst instanceof ThreeReg)){  // Math is only valid in TwoReg and ThreeReg formats.
                throw new Exception("Expected two or more registers near " + th.getErrorPosition());
            }
        }
        else if (type == Token.Type.PEEK){
            retInst = parseFormat(Instruction.OpCode.POP).get();

            if (!(retInst instanceof TwoReg)){  // Peek is only valid in OneReg and NoReg formats.
                throw new Exception("Expected one or less registers near " + th.getErrorPosition());
            }
        }
        else if (type == Token.Type.POP){
            retInst = parseOneReg(Instruction.OpCode.POP).get();
        }
        else if (type == Token.Type.PUSH){
            retInst = parseFormat(Instruction.OpCode.PUSH).get();

            if (!(retInst instanceof OneReg)){  // Hierarchical inheritance. Catches no-reg.
                throw new Exception("Expected register near " + th.getErrorPosition());
            }
        }
        else if (type == Token.Type.RETURN){
            retInst = new NoReg(Instruction.OpCode.LOAD);
        }
        else if (type == Token.Type.STORE){
            retInst = parseFormat(Instruction.OpCode.STORE).get();

            if (!(retInst instanceof OneReg)){  // Hierarchical inheritance. Catches no-reg.
                throw new Exception("Expected register near " + th.getErrorPosition());
            }
        }
        else{
            throw new Exception("Unexpected token " + th.peek().get().toString() + " at " + th.getErrorPosition());
        }

        return retInst;

    }

    /**
     * Parses the registers and immediates.
     * 
     * @param op The OpCode of the instruction.
     * @return An instruction containing the parsed Registers and immediates.
     * @throws Exception
     */
    private Optional<Instruction> parseFormat(Instruction.OpCode op) throws Exception{
        Optional<Instruction> retInst = parseThreeReg(op);
        Optional<Integer> imm = parseImmediate();

        if (imm.isPresent()){
            retInst.get().setImm(imm.get());
            return retInst;
        }
        else
            return retInst;
    }

    /**
     * Parses up to three registers.
     * 
     * @param op The OpCode of the instruction.
     * @return An instruction containing up to three parsed registers.
     * @throws Exception
     */
    private Optional<Instruction> parseThreeReg(Instruction.OpCode op)throws Exception{

        Optional<Instruction> retInst = parseTwoReg(op);
        Optional<Integer> rs1 = parseRegister();

        if (rs1.isPresent()){
            return Optional.of(new ThreeReg((TwoReg)retInst.get(), rs1.get()));
        }
            return retInst;
    }

    /**
     * Parses up to two registers.
     * 
     * @param op The OpCode of the instruction.
     * @return An instruction containing up to two parsed registers.
     * @throws Exception
     */
    private Optional<Instruction> parseTwoReg(Instruction.OpCode op) throws Exception{

        Optional<Instruction> retInst = parseOneReg(op);
        Optional<Integer> rs2 = parseRegister();

        if (rs2.isPresent())
            return Optional.of(new TwoReg((OneReg)retInst.get(), rs2.get()));
        else 
            return retInst;
    }

    /**
     * Parses a regitster and function, if it exists.
     * 
     * @param op The OpCode of the instruction.
     * @return An instruction containing up a parsed registers and function if applicable..
     * @throws Exception
     */
    private Optional<Instruction> parseOneReg(Instruction.OpCode op) throws Exception{
        
        Optional<Instruction.Function> func = parseFunc();
        Optional<Integer> rd = parseRegister();

        if (rd.isPresent()){
            if (func.isPresent())
                return Optional.of(new OneReg(op, rd.get(), func.get()));
            else
                return Optional.of(new OneReg(op, rd.get(), Instruction.Function.EQ)); // If no register is provided, just use "0000" (EQ).
        }
        else
            return Optional.of(new NoReg(op));
    }

    /**
     * Checks if the next token is a function are returns the function value.
     * 
     * @return An Optional containing the parsed function.
     * @throws Exception
     */
    private Optional<Instruction.Function> parseFunc() throws Exception{
        Token maybeFunc = th.peek().get();

        // Check if it's ordinal is in the right range.
        if (maybeFunc.getType().ordinal() > 12 && maybeFunc.getType().ordinal() <= 26){
            th.swallow();
            /*
             * The Instruction.Function enums and Token.Type function enums are in the same order. 
             * This means you can map them to each other by subtracting to the number of Token 
             * enums before the function enumbs and use that ordinal to map to the right enum in 
             * Instruction.Function
             */
            return Optional.of(Instruction.Function.values()[maybeFunc.getType().ordinal() - 13]);
        }
        else {
            return Optional.empty();
            //throw new Exception("Unexpected token " + th.peek().get().toString() + " at " + th.getErrorPosition());
        }
    }

    /**
     * Parses a register.
     * 
     * @return An Optional<Integer> containing the register number of the parsed register.
     * @throws Exception
     */
    private Optional<Integer> parseRegister() throws Exception{
        if (th.peek().get().getType() == Token.Type.REGISTER)
            return Optional.of(th.matchAndRemove(Token.Type.REGISTER).get().getValue().get());
        else
            return Optional.empty();
    }

    /**
     * Parses an immediate value.
     * 
     * @return An Optional<Integer> containing the value of the parsed immediate.
     * @throws Exception
     */
    private Optional<Integer> parseImmediate() throws Exception {
        if (th.peek().get().getType() == Token.Type.IMMEDIATE)
            return Optional.of(th.matchAndRemove(Token.Type.IMMEDIATE).get().getValue().get());
        else 
            return Optional.empty();
    }
}
