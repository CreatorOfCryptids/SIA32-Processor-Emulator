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

        // Decrement
        w1.set(0);
        w1.decrement();
        Assert.assertEquals(-1, w1.getSigned());

        w1.set(4);
        w1.decrement();
        Assert.assertEquals(3, w1.getSigned());

        w1.set(-7);
        w1.decrement();
        Assert.assertEquals(-8, w1.getSigned());

        w1.set(1025);
        for(int i = 1024; i>-1024; i--){
            w1.decrement();
            Assert.assertEquals(i, w1.getSigned());
        }

        // Equals
        w1.set(0);
        w2.set(0);
        Assert.assertTrue(w1.equals(w2));
        Assert.assertTrue(w2.equals(w1));

        w1.set(31415);
        w2.set(-31414);
        Assert.assertFalse(w1.equals(w2));
        Assert.assertFalse(w2.equals(w1));

        w1.set(-31415);
        Assert.assertFalse(w1.equals(w2));
        Assert.assertFalse(w2.equals(w1));

        w1.set(31415);
        w2.set(31414);
        Assert.assertFalse(w1.equals(w2));
        Assert.assertFalse(w2.equals(w1));

        w1.set(-31415);
        w2.set(-31414);
        Assert.assertFalse(w1.equals(w2));
        Assert.assertFalse(w2.equals(w1));

        w1.set(-31415);
        w2.set(-31415);
        Assert.assertTrue(w1.equals(w2));
        Assert.assertTrue(w2.equals(w1));

        w1.set(7980987);
        w2.set(7980987);
        Assert.assertTrue(w1.equals(w2));
        Assert.assertTrue(w2.equals(w1));
    }

    @Test
    public void Word_Comparison() throws Exception{

        Word w1 = new Word();
        Word w2 = new Word();

        // Equals
        w1.set(0);
        w2.set(0);
        Assert.assertTrue(w1.equals(w2));
        Assert.assertTrue(w2.equals(w1));

        w1.set(31415);
        w2.set(-31414);
        Assert.assertFalse(w1.equals(w2));
        Assert.assertFalse(w2.equals(w1));

        w1.set(31415);
        w2.set(31414);
        Assert.assertFalse(w1.equals(w2));
        Assert.assertFalse(w2.equals(w1));

        w1.set(-31415);
        w2.set(-31414);
        Assert.assertFalse(w1.equals(w2));
        Assert.assertFalse(w2.equals(w1));

        w1.set(-31415);
        w2.set(-31415);
        Assert.assertTrue(w1.equals(w2));
        Assert.assertTrue(w2.equals(w1));

        w1.set(7980987);
        w2.set(7980987);
        Assert.assertTrue(w1.equals(w2));
        Assert.assertTrue(w2.equals(w1));

        // Not Equals
        w1.set(0);
        w2.set(0);
        Assert.assertFalse(w1.notEqual(w2));
        Assert.assertFalse(w2.notEqual(w1));

        w1.set(31415);
        w2.set(-31414);
        Assert.assertTrue(w1.notEqual(w2));
        Assert.assertTrue(w2.notEqual(w1));

        w1.set(31415);
        w2.set(31414);
        Assert.assertTrue(w1.notEqual(w2));
        Assert.assertTrue(w2.notEqual(w1));

        w1.set(-31415);
        w2.set(-31414);
        Assert.assertTrue(w1.notEqual(w2));
        Assert.assertTrue(w2.notEqual(w1));

        w1.set(-31415);
        w2.set(-31415);
        Assert.assertFalse(w1.notEqual(w2));
        Assert.assertFalse(w2.notEqual(w1));

        w1.set(7980987);
        w2.set(7980987);
        Assert.assertFalse(w1.notEqual(w2));
        Assert.assertFalse(w2.notEqual(w1));

        // Less than
        w1.set(0);
        w2.set(0);
        Assert.assertFalse(w1.lessThan(w2));
        Assert.assertFalse(w2.lessThan(w1));

        w1.set(31415);
        w2.set(-31414);
        Assert.assertFalse(w1.lessThan(w2));
        Assert.assertTrue(w2.lessThan(w1));

        w1.set(31415);
        w2.set(31414);
        Assert.assertFalse(w1.lessThan(w2));
        Assert.assertTrue(w2.lessThan(w1));

        w1.set(-31415);
        w2.set(-31414);
        Assert.assertTrue(w1.lessThan(w2));
        Assert.assertFalse(w2.lessThan(w1));

        w1.set(-31415);
        w2.set(-31415);
        Assert.assertFalse(w1.lessThan(w2));
        Assert.assertFalse(w2.lessThan(w1));

        w1.set(7980987);
        w2.set(7980987);
        Assert.assertFalse(w1.lessThan(w2));
        Assert.assertFalse(w2.lessThan(w1));

        // Less Equals
        w1.set(0);
        w2.set(0);
        Assert.assertTrue(w1.lessEquals(w2));
        Assert.assertTrue(w2.lessEquals(w1));

        w1.set(31415);
        w2.set(-31414);
        Assert.assertFalse(w1.lessEquals(w2));
        Assert.assertTrue(w2.lessEquals(w1));

        w1.set(31415);
        w2.set(31414);
        Assert.assertFalse(w1.lessEquals(w2));
        Assert.assertTrue(w2.lessEquals(w1));

        w1.set(-31415);
        w2.set(-31414);
        Assert.assertTrue(w1.lessEquals(w2));
        Assert.assertFalse(w2.lessEquals(w1));

        w1.set(-31415);
        w2.set(-31415);
        Assert.assertTrue(w1.lessEquals(w2));
        Assert.assertTrue(w2.lessEquals(w1));

        w1.set(7980987);
        w2.set(7980987);
        Assert.assertTrue(w1.lessEquals(w2));
        Assert.assertTrue(w2.lessEquals(w1));

        // Greater than
        w1.set(0);
        w2.set(0);
        Assert.assertFalse(w1.greaterThan(w2));
        Assert.assertFalse(w2.greaterThan(w1));

        w1.set(31415);
        w2.set(-31414);
        Assert.assertTrue(w1.greaterThan(w2));
        Assert.assertFalse(w2.greaterThan(w1));

        w1.set(31415);
        w2.set(31414);
        Assert.assertTrue(w1.greaterThan(w2));
        Assert.assertFalse(w2.greaterThan(w1));

        w1.set(-31415);
        w2.set(-31414);
        Assert.assertFalse(w1.greaterThan(w2));
        Assert.assertTrue(w2.greaterThan(w1));

        w1.set(-31415);
        w2.set(-31415);
        Assert.assertFalse(w1.greaterThan(w2));
        Assert.assertFalse(w2.greaterThan(w1));

        w1.set(7980987);
        w2.set(7980987);
        Assert.assertFalse(w1.greaterThan(w2));
        Assert.assertFalse(w2.greaterThan(w1));

        // Greater Equals
        w1.set(0);
        w2.set(0);
        Assert.assertTrue(w1.greaterEquals(w2));
        Assert.assertTrue(w2.greaterEquals(w1));

        w1.set(31415);
        w2.set(-31414);
        Assert.assertTrue(w1.greaterEquals(w2));
        Assert.assertFalse(w2.greaterEquals(w1));

        w1.set(31415);
        w2.set(31414);
        Assert.assertTrue(w1.greaterEquals(w2));
        Assert.assertFalse(w2.greaterEquals(w1));

        w1.set(-31415);
        w2.set(-31414);
        Assert.assertFalse(w1.greaterEquals(w2));
        Assert.assertTrue(w2.greaterEquals(w1));

        w1.set(-31415);
        w2.set(-31415);
        Assert.assertTrue(w1.greaterEquals(w2));
        Assert.assertTrue(w2.greaterEquals(w1));

        w1.set(7980987);
        w2.set(7980987);
        Assert.assertTrue(w1.greaterEquals(w2));
        Assert.assertTrue(w2.greaterEquals(w1));
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
        Processor process;

        // Provided test
        String[] data = new String[]{ //        Immediate         -RD3-   OP-01
                                      // 0123456789012345678901   23456   78901
                                        "1010000000000000000000"+"10000"+"00001",           // MATH DestOnly 5, r1
                                      // Immediate  -RS1-   -RS2-   FUN    -RD3-   OP-11
                                      // 01234567   89012   34567   8901   23456   78901
                                        "00000000"+"10000"+"10000"+"1110"+"01000"+"00011",  // MATH ADD R1 R1 R2
                                      //   Immediate     -RS2-   FUN    -RD3-   OP-10
                                      // 0123456789012   34567   8901   23456   78901
                                        "0000000000000"+"01000"+"1110"+"01000"+"00010",     // MATH ADD R2 R2
                                      // Immediate  -RS1-   -RS2-   FUN    -RD3-   OP-11
                                      // 01234567   89012   34567   8901   23456   78901
                                        "00000000"+"10000"+"01000"+"1110"+"11000"+"00011",  // MATH ADD R2 R1 R3
                                      //          Immediate            OP-00
                                      // 012345678901234567890123456   78901
                                        "000000000000000000000000000"+"00000",              // HALT
                                    };
        MainMemory.load(data);
        process = new Processor();

        process.run();
        Assert.assertEquals(25, process.TESTgetRegister(3).getSigned());

        // Other Math Operations Test:
        process = new Processor();
        data = new String[]{  //        Immediate         -RD3-   OP-01
                              // 0123456789012345678901   23456   78901
                                "0101100101000000000000"+"00000"+"00001",           // R0 <- 666 // SHOULDN'T WORK!
                                "0010010110000000000000"+"10000"+"00001",           // R1 <- 420
                                "1010001000000000000000"+"01000"+"00001",           // R2 <- 69
                                "1010101100011100100000"+"11000"+"00001",           // R3 <- 80085
                              // Immediate  -RS1-   -RS2-   FUN    -RD3-   OP-11
                              // 01234567   89012   34567   8901   23456   78901
                                "00000000"+"10000"+"01000"+"0111"+"00100"+"00011",  // R4 <- R1 * R2
                              //   Immediate     -RS2-   FUN    -RD3-   OP-10
                              // 0123456789012   34567   8901   23456   78901
                                "0000000000000"+"11000"+"1111"+"00100"+"00010",     // R4 <- R4 - R3
                              //          Immediate            OP-00
                              // 012345678901234567890123456   78901
                                "000000000000000000000000000"+"00000",              // HALT
                            };
        
        MainMemory.load(data);
        process = new Processor();
        
        process.run();
        Assert.assertEquals(0,     process.TESTgetRegister(0).getSigned());
        Assert.assertEquals(420,   process.TESTgetRegister(1).getSigned());
        Assert.assertEquals(69,    process.TESTgetRegister(2).getSigned());
        Assert.assertEquals(80085, process.TESTgetRegister(3).getSigned());
        Assert.assertEquals((420*69)-80085, process.TESTgetRegister(4).getSigned());
    }

    @Test
    public void CPU_2() throws Exception{

        String[] reg = new String[]{
            "00000","10000","01000","11000","00100","10100","01100","11100",
            "00010","10010","01010","11010","00110","10110","01110","11110",
            "00001","10001","01001","11001","00101","10101","01101","11101",
            "00011","10011","01011","11011","00111","10111","01111","11111",
        };

        Processor process = new Processor();
        String[] instructions;

        /*
            //   Immediate -RS1- -RS2-  FUN   -RD3-  OP-11
            //   01234567               8901         78901
                "00000000"+reg[]+reg[]+"0000"+reg[]+"00011",
            //     Immediate    -RS1-  FUN   -RD3-  OP-10
            //   0123456789012         8901         78901
                "0000000000000"+reg[]+"0000"+reg[]+"00010",
            //          Immediate     FUN   -RD3-   OP-01
            //   012345678901234567   8901          78901
                "000000000000000000"+"0000"+reg[ ]+"00001",
            //             Immediate           OP-00
            //   012345678901234567890123456   78901
                "000000000000000000000000000"+"00000"
        */

        //* Branch:
        instructions = new String[]{
            //          Immediate                 OP-01
            //   0123456789012345678901           78901
                "1010001000000000000000"+reg[14]+"00001",           // 1 Set r14 69
            //          Immediate                 OP-01
            //   0123456789012345678901           78901
                "0010010110000000000000"+reg[15]+"00001",           // 2 Set r15 420
            //          Immediate                 OP-01
            //   0123456789012345678901           78901 
                "0101100101000000000000"+reg[31]+"00001",           // 3 Set r31 666
            //            Immediate            OP-00
            //   012345678901234567890123456   78901
                "011000000000000000000000000"+"00100",              // 4 Jump to 6
            //          Immediate                 OP-01
            //   0123456789012345678901           78901 
                "1011001000000000000000"+reg[14]+"00001",           // 5 Set r14 77   // Skipped
            //          Immediate                 OP-01
            //   0123456789012345678901           78901 
                "1011010000000000000000"+reg[15]+"00001",           // 6 Set r15 45   // Skipped
            //          Immediate                 OP-01
            //   0123456789012345678901           78901
                "0010010110000000000000"+reg[20]+"00001",           //7 Set r20 420  // 6
            //          Immediate                OP-01
            //   0123456789012345678901          78901
                "0100000000000000000000"+reg[0]+"00101",            //8 jump pc + 2      // Jump forward 2
            //          Immediate                 OP-01
            //   0123456789012345678901           78901 
                "0011110110000000000000"+reg[31]+"00001",           //9 Set r31 444  // Skipped
            //          Immediate                 OP-01
            //   0123456789012345678901           78901 
                "1011010000000000000000"+reg[20]+"00001",           //10   Set r20 45   // Skipped
            //    Immediate              FUN            OP-10
            //  0123456789012            8901           78901
                "1000000000000"+reg[14]+"0000"+reg[15]+"00110",     //11   if R14 == R15 1  // Should be false
            //          Immediate                OP-01
            //   0123456789012345678901          78901
                "1000000000000000000000"+reg[1]+"00001",            //12   Set R1 1
            //   Immediate                 FUN           OP-11
            //   01234567                  8901          78901
                "01000000"+reg[15]+reg[0]+"0001"+reg[0]+"00111",    // 13   if R15 != R0 2  // Should be true
            //          Immediate        -RD3-   OP-01
            //   0123456789012345678901          78901
                "1000000000000000000000"+reg[2]+"00001",            // 14 Set R2 1     // Skipped
            //          Immediate        -RD3-   OP-01
            //   0123456789012345678901          78901
                "1111111111111111111111"+reg[30]+"00001",           // 15 Set R30 -1   // Skipped
            //          Immediate        -RD3-   OP-01
            //   0123456789012345678901          78901
                "1001100000000000000000"+reg[3]+"00001",            // 16 Set R3 25
            //            Immediate            OP-00
            //   012345678901234567890123456   78901
                "000000000000000000000000000"+"00000"               // 17 HALT
        };

        MainMemory.load(instructions);
        process = new Processor();
        process.run();
        

        // Jump test 1
        Assert.assertEquals(69, process.TESTgetRegister(14).getUnsigned());
        Assert.assertEquals(420, process.TESTgetRegister(15).getUnsigned());
        // Jump test 2
        Assert.assertEquals(666, process.TESTgetRegister(31).getUnsigned());
        Assert.assertEquals(420, process.TESTgetRegister(20).getUnsigned());
        // If Equals 1
        Assert.assertEquals(1, process.TESTgetRegister(1).getUnsigned());
        // If not equal 2
        Assert.assertEquals(25, process.TESTgetRegister(3).getUnsigned());
        /**/
        
        //* Call
        instructions = new String[]{
            //          Immediate                 OP-01
            //   0123456789012345678901           78901
                "1010000000000000000000"+reg[1]+"00001",        // 0    Set R1 = 5
            //          Immediate              OP-00
            //   012345678901234567890123456   78901
                "000100000000000000000000000"+"01000",          // 1    Call 8  // Call function that sets R2 to 4 and R3 to 69
            //          Immediate                 OP-01
            //   0123456789012345678901           78901
                "1001000000000000000000"+reg[4]+"00001",        // 2    Set R4 = 9
            //          Immediate                OP-01
            //   0123456789012345678901          78901
                "0001000000000000000000"+reg[2]+"01001",        // 3    Call R2 + 8 // Call funciton that sets R5 to 404
            //   Immediate                FUN            OP-11
            //   01234567                 8901           78901
                "00000000"+reg[5]+reg[3]+"0111"+reg[31]+"00011",// 4    Set R31 = R3 * R5
            //   Immediate  -RS1-   -RS2-   FUN    -RD3-   OP-11
            //   01234567   89012   34567  8901          78901
                "10110000"+reg[4]+reg[30]+"0011"+reg[1]+"01011",// 5    Call R4 >= R30 ? pc <- R1 + 13 : pc // Call function that sets R29 to 420
            //     Immediate     -RS2-   FUN    -RD3-   OP-10
            //   0123456789012   34567   8901   23456   78901
                "0001000000000"+reg[1]+"0011"+reg[2]+"01010",   // 6    Call R1 > R2 ? pc <- pc + 8 // Call function that sets R30 to 12
            //            Immediate            OP-00
            //   012345678901234567890123456   78901
                "000000000000000000000000000"+"00000",          // 7    Halt 0
            //          Immediate                OP-01
            //   0123456789012345678901          78901
                "0010000000000000000000"+reg[2]+"00001",        // 8    R2 = 4  // First function
            //          Immediate                 OP-01
            //   0123456789012345678901           78901
                "1010001000000000000000"+reg[3]+"00001",        // 9    R3 = 69
            //          Immediate              OP-00
            //   012345678901234567890123456   78901
                "000000000000000000000000000"+"10000",          // 10   RETURN
            //            Immediate            OP-00
            //   012345678901234567890123456   78901
                "100000000000000000000000000"+"00000",          // 11   Halt 1
            //          Immediate                OP-01
            //   0123456789012345678901          78901
                "0010100110000000000000"+reg[5]+"00001",        // 12   R5 = 404     // Second function
            //           Immediate             OP-00
            //   012345678901234567890123456   78901
                "000000000000000000000000000"+"10000",          // 13   Return
            //            Immediate            OP-00
            //   012345678901234567890123456   78901
                "010000000000000000000000000"+"00000",          // 14   Halt 2
            //          Immediate                 OP-01
            //   0123456789012345678901           78901
                "0011000000000000000000"+reg[30]+"00001",       // 15   R30 = 12    // Third function
            //           Immediate             OP-00
            //   012345678901234567890123456   78901
                "000000000000000000000000000"+"10000",          // 16   Return
            //            Immediate            OP-00
            //   012345678901234567890123456   78901
                "110000000000000000000000000"+"00000",          // 17   Halt 3
            //          Immediate                 OP-01
            //   0123456789012345678901           78901
                "0010010110000000000000"+reg[29]+"00001",       // 18   R29 = 420   // Fourth function
            //           Immediate              OP-00
            //   012345678901234567890123456    78901
                "000000000000000000000000000"+"10000",          // 19   Return
            //            Immediate            OP-00
            //   012345678901234567890123456   78901
                "001000000000000000000000000"+"00000",          // 20   Halt 4
            
        };

        MainMemory.load(instructions);
        process = new Processor();
        process.run();

        Assert.assertEquals(0, process.getExitCode());
        Assert.assertEquals(5, process.TESTgetRegister(1).getSigned());
        Assert.assertEquals(4, process.TESTgetRegister(2).getSigned());
        Assert.assertEquals(69, process.TESTgetRegister(3).getSigned());
        Assert.assertEquals(9, process.TESTgetRegister(4).getSigned());
        Assert.assertEquals(404, process.TESTgetRegister(5).getSigned());
        Assert.assertEquals(69*404, process.TESTgetRegister(31).getSigned());
        Assert.assertEquals(420, process.TESTgetRegister(29).getSigned());
        Assert.assertEquals(12, process.TESTgetRegister(30).getSigned());
        /**/

        //* Push 
        instructions = new String[]{
            //          Immediate                 OP-01
            //   0123456789012345678901           78901
                "0010010110000000000000"+reg[1]+"00001",            // 1 Set R1 = 420
            //          Immediate                OP-01
            //   0123456789012345678901          78901
                "1010001000000000000000"+reg[2]+"00001",            // 2 Set R2 = 69
            //          Immediate                OP-01
            //   0123456789012345678901          78901 
                "0101100101000000000000"+reg[3]+"00001",            // 3 Set R3 = 666

            //        Immediate       FUN           OP-01
            //   012345678901234567   8901          78901
                "011011110000000000"+"1110"+reg[1]+"01101",         // 4 push R1 + 246      
            //     Immediate            FUN           OP-10
            //   0123456789012          8901          78901
                "0000000000000"+reg[3]+"0111"+reg[2]+"01110",       // 5 push R2 * R3
            //   Immediate                FUN           OP-11
            //   01234567                 8901          78901
                "00000000"+reg[1]+reg[2]+"1111"+reg[0]+"01111",      // push R1 - R2

            //        Immediate       FUN           OP-01
            //   012345678901234567   8901          78901
                "001100000000000000"+"0111"+reg[3]+"01101",         // 7 push R3 * 12 
            //     Immediate            FUN           OP-10
            //   0123456789012          8901          78901
                "0000000000000"+reg[2]+"0111"+reg[0]+"01110",       // 8 push R2 * R0
            //   Immediate                FUN           OP-11
            //   01234567                 8901          78901
                "00000000"+reg[2]+reg[1]+"1110"+reg[0]+"01111",  // push R2 + R1

            //          Immediate            OP-00
            // 012345678901234567890123456   78901
            "000000000000000000000000000"+"00000"               // HALT
        };

        MainMemory.load(instructions);
        process = new Processor();
        process.run();

        Assert.assertEquals(420+246, MainMemory.TEST_Read(1023).getUnsigned());
        Assert.assertEquals(69*666, MainMemory.TEST_Read(1022).getUnsigned());
        Assert.assertEquals(420-69, MainMemory.TEST_Read(1021).getUnsigned());
        Assert.assertEquals(666*12, MainMemory.TEST_Read(1020).getUnsigned());
        Assert.assertEquals(0, MainMemory.TEST_Read(1019).getUnsigned());
        Assert.assertEquals(420+69, MainMemory.TEST_Read(1018).getUnsigned());
        /**/

        // TODO: Load
        instructions = new String[]{

            //          Immediate            OP-00
            // 012345678901234567890123456   78901
            "000000000000000000000000000"+"00000"               // HALT
        };

        MainMemory.load(instructions);
        process = new Processor();
        process.run();

        Assert.assertTrue(false);

        // TODO: Store
        instructions = new String[]{



            //          Immediate            OP-00
            // 012345678901234567890123456   78901
            "000000000000000000000000000"+"00000"               // HALT
        };

        MainMemory.load(instructions);
        process = new Processor();
        process.run();

        Assert.assertTrue(false);

        // TODO: Pop
        instructions = new String[]{
            "",
            //          Immediate            OP-00
            // 012345678901234567890123456   78901
            "000000000000000000000000000"+"00000"               // HALT
        };

        MainMemory.load(instructions);
        process = new Processor();
        process.run();

        Assert.assertTrue(false);

        // TODO: Factoral
        instructions = new String[]{
            //          Immediate                OP-01
            //   0123456789012345678901          78901
                "1010000000000000000000"+reg[1]+"00001",        // 1 set R1 = 5
            //          Immediate                OP-01
            //   0123456789012345678901          78901
                "1000000000000000000000"+reg[11]+"00001",       // 2 set R11 = 1
            //          Immediate                OP-01
            //   0123456789012345678901          78901
                "1000000000000000000000"+reg[12]+"00001",       // 3 set R12 = 2
            //   Immediate -RS1- -RS2-  FUN   -RD3-  OP-11
            //   01234567               8901         78901
                "00000000"+reg[1]+reg[11]+"1111"+reg[2]+"00011",// 4 set R2 = R1 - R11
            //   Immediate -RS1- -RS2-  FUN   -RD3-  OP-11
            //   01234567               8901         78901
                "00000000"+reg[1]+reg[2]+"0111"+reg[3]+"00011", // 5 set R3 = R1 * R2
            //     Immediate    -RS1-   FUN   --RD3--  OP-10
            //   0123456789012          8901           78901
                "1100000000000"+reg[2]+"0010"+reg[12]+"00110",  // 6 if R2 >= R12 ? jump pc + 3
            //   Immediate      --RS2--  FUN   -RD3-   OP-11
            //   0123456789012           8901          78901
                "0000000000000"+reg[11]+"1111"+reg[2]+"00011",  // 7 set r2 = r2 - R11
            //   Immediate      -RS2-   FUN   -RD3-   OP-11
            //   0123456789012          8901          78901
                "0000000000000"+reg[2]+"0111"+reg[3]+"00011",   // 8 set R3 = R3 *R2
            //             Immediate           OP-00
            //   012345678901234567890123456   78901
                "011000000000000000000000000"+"00100",          // 9 Jump 6
            //          Immediate            OP-00
            //   012345678901234567890123456   78901
                "000000000000000000000000000"+"00000"           // 10 HALT
        };

        MainMemory.load(instructions);
        process = new Processor();
        process.run();

        Assert.assertEquals(120, process.TESTgetRegister(3));

    }
}
