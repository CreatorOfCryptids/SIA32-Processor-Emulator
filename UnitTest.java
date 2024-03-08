/**
 * Unit Tests for ICSI404 in Spring 2024.
 * 
 * @author Danny Peck (dpeck@albany.edu)
 */

import org.junit.*;

public class UnitTest {
    
    @Test
    public void Bit_init() throws Exception{
        // Constructor
        Bit b = new Bit(true);
        Assert.assertTrue(b.getValue());
        b = new Bit(false);
        Assert.assertFalse(b.getValue());

        // Set with value
        b.set(true);
        Assert.assertTrue(b.getValue());
        b.set(false);
        Assert.assertFalse(b.getValue());        

        // Set without value.
        b.set();
        Assert.assertTrue(b.getValue());
        b.set();
        Assert.assertTrue(b.getValue());

        // Toggle
        b.toggle();
        Assert.assertFalse(b.getValue());
        b.toggle();
        Assert.assertTrue(b.getValue());

        // Clear
        b.clear();
        Assert.assertFalse(b.getValue());
        b.clear();
        Assert.assertFalse(b.getValue());

        // toString.
        Assert.assertEquals("F", b.toString());
        b.toggle();
        Assert.assertEquals("T", b.toString());
    }

    @Test
    public void Bit_logic() throws Exception{

        Bit t1 = new Bit(true);
        Bit t2 = new Bit(true);
        Bit f1 = new Bit(false);
        Bit f2 = new Bit(false);

        // And
        Assert.assertTrue(t1.and(t2).getValue());
        Assert.assertFalse(t1.and(f1).getValue());
        Assert.assertFalse(f1.and(t1).getValue());
        Assert.assertFalse(f1.and(f2).getValue());

        // Or
        Assert.assertTrue(t1.or(t2).getValue());
        Assert.assertTrue(t1.or(f1).getValue());
        Assert.assertTrue(f1.or(t1).getValue());
        Assert.assertFalse(f1.or(f2).getValue());

        // Xor
        Assert.assertFalse(t1.xor(t2).getValue());
        Assert.assertTrue(t1.xor(f1).getValue());
        Assert.assertTrue(f1.xor(t1).getValue());
        Assert.assertFalse(f1.xor(f2).getValue());

        // Not
        Assert.assertTrue(f1.not().getValue());
        Assert.assertFalse(t1.not().getValue());
    }

    @Test 
    public void Word_init() throws Exception{
        // Constructor
        Word w = new Word();
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", w.toString());


        // getBit() with all false
        for(int i=0; i<Word.WORD_SIZE; i++)
            Assert.assertFalse(w.getBit(i).getValue());
        
        // setBit() with only true
        for(int i=0; i<Word.WORD_SIZE; i = i+2){
            w.setBit(i, new Bit(true));
        }

        // toString()
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", w.toString());

        // copy()
        Word w2 = new Word();
        w2.copy(w);
        Assert.assertEquals(w.toString(), w2.toString());

        // setBit() with false
        for(int i=0; i<Word.WORD_SIZE; i = i+2)
            w.setBit(i, new Bit(false));
        
        // set()
        w.set(0);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", w.toString());
        Assert.assertEquals(0, w.getUnsigned());
        Assert.assertEquals(0, w.getSigned());

        w.set(1);
        Assert.assertEquals("T,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", w.toString());
        Assert.assertEquals(1, w.getUnsigned());
        Assert.assertEquals(1, w.getSigned());


        w.set(5);
        Assert.assertEquals("T,F,T,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", w.toString());
        Assert.assertEquals(5, w.getUnsigned());
        Assert.assertEquals(5, w.getSigned());


        w.set(16);
        Assert.assertEquals("F,F,F,F,T,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", w.toString());
        Assert.assertEquals(16, w.getUnsigned());
        Assert.assertEquals(16, w.getSigned());


        w.set(44);
        Assert.assertEquals("F,F,T,T,F,T,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", w.toString());
        Assert.assertEquals(44, w.getUnsigned());
        Assert.assertEquals(44, w.getSigned());


        w.set(-1);
        Assert.assertEquals("T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T", w.toString());
        Assert.assertEquals(-1, w.getSigned());


        w.set(-5);
        Assert.assertEquals("T,T,F,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T", w.toString());
        Assert.assertEquals(-5, w.getSigned());


        w.set(-16);
        Assert.assertEquals("F,F,F,F,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T", w.toString());
        Assert.assertEquals(-16, w.getSigned());


        w.set(-44);
        Assert.assertEquals("F,F,T,F,T,F,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T", w.toString());
        Assert.assertEquals(-44, w.getSigned());


        //00001111011001011100101111111010
        w.set(258329594);
        Assert.assertEquals("F,T,F,T,T,T,T,T,T,T,F,T,F,F,T,T,T,F,T,F,F,T,T,F,T,T,T,T,F,F,F,F", w.toString());
        Assert.assertEquals(258329594, w.getUnsigned());
        Assert.assertEquals(258329594, w.getSigned());


        //01010001100001011110001000001100
        w.set(1367728652);
        Assert.assertEquals("F,F,T,T,F,F,F,F,F,T,F,F,F,T,T,T,T,F,T,F,F,F,F,T,T,F,F,F,T,F,T,F", w.toString());
        Assert.assertEquals(1367728652, w.getUnsigned());
        Assert.assertEquals(1367728652, w.getSigned());


        //-01110111111000100101111000010000
        w.set(-2011323920);
        Assert.assertEquals("F,F,F,F,T,T,T,T,T,F,F,F,F,T,F,T,T,F,T,T,T,F,F,F,F,F,F,T,F,F,F,T", w.toString());
        Assert.assertEquals(-2011323920, w.getSigned());



        //-00110111110111110111000010000100
        w.set(-937390212);
        Assert.assertEquals("F,F,T,T,T,T,T,F,T,T,T,T,F,F,F,T,F,F,F,F,F,T,F,F,F,F,F,T,F,F,T,T", w.toString());
        Assert.assertEquals(-937390212, w.getSigned());
    }

    @Test
    public void Word_logic() throws Exception{

        Word w1 = new Word();
        Word w2 = new Word();
        Word result;

        for(int i = 0; i<Word.WORD_SIZE; i+=2)
            w1.setBit(i, true);
        
        for(int i = 2; i<Word.WORD_SIZE-1; i+=4){
            w2.setBit(i, true);
            w2.setBit(i+1, true);
        }

        /* For reference:
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", w1.toString());
        Assert.assertEquals("F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T", w2.toString());
        /**/

        // AND 
        result = w1.and(w2);
        Assert.assertEquals("F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F", result.toString());
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", w1.toString());
        Assert.assertEquals("F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T", w2.toString());

        // OR 
        result = w1.or(w2);
        Assert.assertEquals("T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T", result.toString());
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", w1.toString());
        Assert.assertEquals("F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T", w2.toString());
        
        // XOR
        result = w1.xor(w2);
        Assert.assertEquals("T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T", result.toString());
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", w1.toString());
        Assert.assertEquals("F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T", w2.toString());

        // NOT
        result = w1.not();
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T", result.toString());
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", w1.toString());
        Assert.assertEquals("F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T", w2.toString());

        result = w2.not();
        Assert.assertEquals("T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F", result.toString());
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", w1.toString());
        Assert.assertEquals("F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T", w2.toString());
        
        // rightShift
        result = w1.rightShift(0);
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", result.toString());

        result = w1.rightShift(1);
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T", result.toString());

        result = w1.rightShift(2);
        Assert.assertEquals("F,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", result.toString());

        result = w1.rightShift(5);
        Assert.assertEquals("F,F,F,F,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T", result.toString());

        result = w1.rightShift(Word.WORD_SIZE);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", result.toString());

        result = w1.rightShift(Word.WORD_SIZE + 300);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", result.toString());

        result = w1.rightShift(-1);
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,F", result.toString());

        result = w1.rightShift(-8);
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,F,F,F,F,F,F,F,F", result.toString());

        result = w1.rightShift(-Word.WORD_SIZE);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", result.toString());

        // leftShift
        result = w1.leftShift(0);
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", result.toString());

        result = w1.leftShift(1);
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,F", result.toString());

        result = w1.leftShift(2);
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,F,F", result.toString());

        result = w1.leftShift(5);
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,F,F,F,F,F", result.toString());

        result = w1.leftShift(Word.WORD_SIZE);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", result.toString());
        
        result = w1.leftShift(Word.WORD_SIZE + 300);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", result.toString());

        result = w1.leftShift(-1);
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T", result.toString());

        result = w1.leftShift(-8);
        Assert.assertEquals("F,F,F,F,F,F,F,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", result.toString());

        result = w1.leftShift(-Word.WORD_SIZE);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", result.toString());

        // Increment
        w1.set(0);
        w1.increment();
        Assert.assertEquals(1, w1.getSigned());

        w1.set(-7);
        w1.increment();
        Assert.assertEquals(-6, w1.getSigned());

        w1.set(-1025);
        for(int i = -1024; i<1024; i++){
            w1.increment();
            Assert.assertEquals(i, w1.getSigned());
        }
    }

    @Test
    public void ALU_logic() throws Exception{

        ALU alu = new ALU();
        Bit[] code = new Bit[4];

        for(int i=0; i<4; i++)
            code[i] = new Bit();

        for(int i = 0; i<Word.WORD_SIZE; i+=2)
            alu.op1.setBit(i, true);
        
        for(int i = 2; i<Word.WORD_SIZE-1; i+=4){
            alu.op2.setBit(i, true);
            alu.op2.setBit(i+1, true);
        }
        //1000 – and
        code[0].set();

        alu.doOperation(code);
        Assert.assertEquals("F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F,F,F,T,F", alu.result.toString());

        //1001 – or
        code[3].set();

        alu.doOperation(code);
        Assert.assertEquals("T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T,T,F,T,T", alu.result.toString());

        //1010 – xor
        code[3].clear();
        code[2].set();

        alu.doOperation(code);
        Assert.assertEquals("T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T", alu.result.toString());

        //1011 – not (not “op1”; ignore op2)
        code[3].set();

        alu.doOperation(code);
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T", alu.result.toString());

        //1100 – left shift (“op1” is the value to shift, “op2” is the amount to shift; ignore all but the lowest 5 bits)
        code[1].set();
        code[2].clear();
        code[3].clear();

        alu.doOperation(code);


        //1101 – right shift (“op1” is the value to shift, “op2” is the amount to shift; ignore all but the lowest 5 bits)

    }

    @Test
    public void ALU_Math() throws Exception{

        ALU alu = new ALU();
        Bit[] code = new Bit[4];
        for(int i =0; i<4; i++) code[i] = new Bit(true);

        // add4()
            Word op1 = new Word();
            Word op2 = new Word();
            Word op3 = new Word();
            Word op4 = new Word();

            int num1=0, num2=0, num3=0, num4=0;

            // Identity
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            // Increasing by one
            num1 = 1;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            num2 = 1;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            num3 = 1;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            num4 = 1;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            // More than one
            num1 = 56;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            num2 = 871;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            num3 = 31415;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            num4 = 1024;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            // Negatives
            num3 = -314159;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

            num2 = -420;
            op1.set(num1);
            op2.set(num2);
            op3.set(num3);
            op4.set(num4);
            Assert.assertEquals(num1+num2+num3+num4, alu.TESTadd4(op1, op2, op3, op4).getSigned());

        // 1110 – add
        code[3].clear();
        
            // 0 and 0
            alu.op1.set(0);
            alu.op2.set(0);
            alu.doOperation(code);
            Assert.assertEquals(0, alu.result.getSigned());

            // Identity
            alu.op1.set(1);
            alu.op2.set(0);
            alu.doOperation(code);
            Assert.assertEquals(1, alu.result.getSigned());

            alu.op1.set(0);
            alu.op2.set(1);
            alu.doOperation(code);
            Assert.assertEquals(1, alu.result.getSigned());

            // one negative
            alu.op1.set(-1);
            alu.op2.set(3);
            alu.doOperation(code);
            Assert.assertEquals(2, alu.result.getSigned());

            alu.op1.set(5);
            alu.op2.set(-10);
            alu.doOperation(code);
            Assert.assertEquals(-5, alu.result.getSigned());

            // two negative
            alu.op1.set(-20);
            alu.op2.set(-100);
            alu.doOperation(code);
            Assert.assertEquals(-120, alu.result.getSigned());

            // Rand ints (Yes, I was too lazy to calculate the expected value, thank you for noticing.)
            alu.op1.set(-1400289207);
            alu.op2.set(1623331231);
            alu.doOperation(code);
            Assert.assertEquals(1623331231-1400289207, alu.result.getSigned());

            alu.op1.set(1975149539);
            alu.op2.set(6618590);
            alu.doOperation(code);
            Assert.assertEquals(1975149539+6618590, alu.result.getSigned());
        
        // 1111 – subtract
        code[3].set();

            // 0 and 0
            alu.op1.set(0);
            alu.op2.set(0);
            alu.doOperation(code);
            Assert.assertEquals(0, alu.result.getSigned()); 

            // Identity
            alu.op1.set(1);
            alu.op2.set(0);
            alu.doOperation(code);
            Assert.assertEquals(1, alu.result.getSigned()); 

            // first 0
            alu.op1.set(0);
            alu.op2.set(1);
            alu.doOperation(code);
            Assert.assertEquals(-1, alu.result.getSigned());

            // output 0
            alu.op1.set(1);
            alu.op2.set(1);
            alu.doOperation(code);
            Assert.assertEquals(0, alu.result.getSigned());

            alu.op1.set(31415);
            alu.op2.set(31415);
            alu.doOperation(code);
            Assert.assertEquals(0, alu.result.getSigned());

            // one negative
            alu.op1.set(-1);
            alu.op2.set(3);
            alu.doOperation(code);
            Assert.assertEquals(-4, alu.result.getSigned());

            alu.op1.set(5);
            alu.op2.set(-10);
            alu.doOperation(code);
            Assert.assertEquals(15, alu.result.getSigned());

            // two negative
            alu.op1.set(-20);
            alu.op2.set(-100);
            alu.doOperation(code);
            Assert.assertEquals(80, alu.result.getSigned());

            // Rand ints (Yes, I was too lazy to calculate the expected value, thank you for noticing.)
            alu.op1.set(2027887846);
            alu.op2.set(573393827);
            alu.doOperation(code);
            Assert.assertEquals(2027887846-573393827, alu.result.getSigned());

            alu.op1.set(1975149539);
            alu.op2.set(6618590);
            alu.doOperation(code);
            Assert.assertEquals(1975149539-6618590, alu.result.getSigned());

        // 0111 - multiply
        code[0].clear();

            // 0 and 0
            alu.op1.set(0);
            alu.op2.set(0);
            alu.doOperation(code);
            Assert.assertEquals(0, alu.result.getSigned()); 

            // Erasure
            alu.op1.set(1);
            alu.op2.set(0);
            alu.doOperation(code);
            Assert.assertEquals(0, alu.result.getSigned()); 

            alu.op1.set(0);
            alu.op2.set(1);
            alu.doOperation(code);
            Assert.assertEquals(0, alu.result.getSigned());

            // Identity rule
            alu.op1.set(1);
            alu.op2.set(31415);
            alu.doOperation(code);
            Assert.assertEquals(31415, alu.result.getSigned());

            // Positive
            alu.op1.set(31415);
            alu.op2.set(23);
            alu.doOperation(code);
            Assert.assertEquals(31415*23, alu.result.getSigned());

            alu.op1.set(-1);
            alu.op2.set(3);
            alu.doOperation(code);
            Assert.assertEquals(-3, alu.result.getSigned());

            alu.op1.set(5);
            alu.op2.set(-10);
            alu.doOperation(code);
            Assert.assertEquals(-50, alu.result.getSigned());

            // two negative
            alu.op1.set(-20);
            alu.op2.set(-100);
            alu.doOperation(code);
            Assert.assertEquals(2000, alu.result.getSigned());

            // Rand ints (Yes, I was too lazy to calculate the expected value, thank you for noticing.)
            alu.op1.set(239780);
            alu.op2.set(833);
            alu.doOperation(code);
            Assert.assertEquals(239780*833, alu.result.getSigned());

            alu.op1.set(64);
            alu.op2.set(64);
            alu.doOperation(code);
            Assert.assertEquals(64*64, alu.result.getSigned());
    }

    @Test
    public void Memory() throws Exception {

        Word address = new Word();
        Word writen = new Word();

        // Load
        String[] data = new String[]{"11111111111111111111111111111111","00000000000000000000000000000000","01010101010101010101010101010101"};
        MainMemory.load(data);

        address.set(0);
        Assert.assertEquals("T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T", MainMemory.read(address).toString());

        address.set(1);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", MainMemory.read(address).toString());

        address.set(2);         // input is little endian, output is big endian.
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T", MainMemory.read(address).toString());

        // Read
        address.set(3);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", MainMemory.read(address).toString());

        address.set(1023);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", MainMemory.read(address).toString());

        // Write
        address.set(5);

        for(int i = 0; i<Word.WORD_SIZE; i+=2)
            writen.setBit(i, true);

        MainMemory.write(address, writen);

        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", MainMemory.read(address).toString());

        address.set(5);
        for(int i = 0; i<Word.WORD_SIZE; i++){
            writen.setBit(i, false);
        }
        for(int i = 2; i<Word.WORD_SIZE-1; i+=4){
            writen.setBit(i, true);
            writen.setBit(i+1, true);
        }

        MainMemory.write(address, writen);

        Assert.assertEquals("F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T,F,F,T,T", MainMemory.read(address).toString());

        // Load 2: Electric Boogaloo (seeing if it will overwrite data as expected).
        address.set(0);
        for(int i = 0; i<Word.WORD_SIZE; i++){
            writen.setBit(i, false);
        }
        for(int i = 0; i<Word.WORD_SIZE; i+=2)
            writen.setBit(i, true);

        MainMemory.write(address, writen);

        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", MainMemory.read(address).toString());

        MainMemory.load(data);

        address.set(0);
        Assert.assertEquals("T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T", MainMemory.read(address).toString());

        address.set(1);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", MainMemory.read(address).toString());

        address.set(2);         // input is little endian, output is big endian.
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T", MainMemory.read(address).toString());
    }

    @Test
    public void CPU_1() throws Exception{
        // TODO: Next assignment.
    }
}
