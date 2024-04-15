package Compiler;

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
     * 
     * 
     * @return
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
     * 
     * 
     * @return
     * @throws Exception
     */
    private Instruction parseInstruction() throws Exception{

        Instruction retInst;
        Token.Type type = th.swallow().get().getType();


        if (type == Token.Type.BRANCH){
            retInst = parseFormat(Instruction.OpCode.BRANCH).get();
        }
        else if (type == Token.Type.CALL){
            retInst = parseFormat(Instruction.OpCode.CALL).get();
        }
        else if (type == Token.Type.COPY){
            retInst = parseOneReg(Instruction.OpCode.MATH).get();

            Optional<Integer> imm = parseImmediate();

            if (imm.isPresent())
                retInst.setImm(imm.get());
            else
                throw new Exception("Expected an immediate at " + th.getErrorPosition() + ", but was " + th.peek().get().toString() + '.');
        }
        else if (type == Token.Type.HAULT){
            retInst = new NoReg(Instruction.OpCode.MATH);
        }
        else if (type == Token.Type.INTERRUPT){
            retInst = new NoReg(Instruction.OpCode.POP);
        }
        else if (type == Token.Type.JUMP){

            retInst = parseOneReg(Instruction.OpCode.BRANCH).get();
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

            if (!(retInst instanceof TwoReg || retInst instanceof ThreeReg)){  // Hierarchical inheritance. Catches anything with registerCount >= 2
                throw new Exception("Expected two or more registers near " + th.getErrorPosition());
            }
        }
        else if (type == Token.Type.PEEK){
            retInst = parseFormat(Instruction.OpCode.POP).get();

            if (!(retInst instanceof TwoReg)){  // Hierarchical inheritance. Catches anything with registerCount <2
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
            retInst = new NoReg(Instruction.OpCode.CALL);
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
     * 
     * 
     * @param op
     * @return
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
     * 
     * 
     * @param op
     * @return
     * @throws Exception
     */
    private Optional<Instruction> parseThreeReg(Instruction.OpCode op)throws Exception{
        // parseTwoReg() to get Rd, and Rs1
        Optional<Instruction> retInst = parseTwoReg(op);
        Optional<Integer> rs1 = parseRegister();

        if (rs1.isPresent()){
            return Optional.of(new ThreeReg((TwoReg)retInst.get(), rs1.get()));
        }
            return retInst;
    }

    private Optional<Instruction> parseTwoReg(Instruction.OpCode op) throws Exception{
        
        // Get Rs2 then try to parseThreeReg()
        Optional<Instruction> retInst = parseOneReg(op);
        Optional<Integer> rs2 = parseRegister();

        if (rs2.isPresent())
            return Optional.of(new TwoReg((OneReg)retInst.get(), rs2.get()));
        else 
            return retInst;
    }

    private Optional<Instruction> parseOneReg(Instruction.OpCode op) throws Exception{
        Optional<Instruction.Function> func = parseFunc();
        Optional<Integer> rd = parseRegister();

        if (rd.isPresent()){
            if (func.isPresent())
                return Optional.of(new OneReg(op, rd.get(), func.get()));
            else
                return Optional.of(new OneReg(op, rd.get(), Instruction.Function.EQ));
            //throw new Exception("Expected Register near " + th.getErrorPosition() + ". Instead was: " + th.peek().get().toString());
        }
        else
            return Optional.of(new NoReg(op));
    }

    private Optional<Instruction.Function> parseFunc() throws Exception{
        Token maybeFunc = th.swallow().get();

        // Check if it's ordinal is in the right range.
        if (maybeFunc.getType().ordinal() > 12 && maybeFunc.getType().ordinal() < 26){
            /*
             * The Instruction.Function enums and Token.Type function enums are in the same order. 
             * This means you can map them to each other by subtracting to the number of Token 
             * enums before the function enumbs and use that ordinal to map to the right enum in 
             * Instruction.Function
             */
            return Optional.of(Instruction.Function.values()[maybeFunc.getType().ordinal() - 12]);
        }
        else {
            return Optional.empty();
            //throw new Exception("Unexpected token " + th.peek().get().toString() + " at " + th.getErrorPosition());
        }
    }

    private Optional<Integer> parseRegister() throws Exception{
        if (th.peek().get().getType() == Token.Type.REGISTER)
            return Optional.of(th.matchAndRemove(Token.Type.REGISTER).get().getValue().get());
        else
            return Optional.empty();
            //throw new Exception("Expected Register token near " + th.getErrorPosition());
    }

    private Optional<Integer> parseImmediate() throws Exception {
        if (th.peek().get().getType() == Token.Type.IMMEDIATE)
            return Optional.of(th.matchAndRemove(Token.Type.IMMEDIATE).get().getValue().get());
        else 
            return Optional.empty();
            //throw new Exception("Expected Immediate token near " + th.getErrorPosition() + " was " + th.peek().get().toString());
    }
}
