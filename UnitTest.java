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

        // getBit() with all false
        for(int i=0; i<w.WORD_SIZE; i++)
            Assert.assertFalse(w.getBit(i).getValue());
        
        // setBit() with only true
        for(int i=0; i<w.WORD_SIZE; i = i+2){
            w.setBit(i, new Bit(true));
        }

        // toString()
        Assert.assertEquals("F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T", w.toString());

        // copy()
        Word w2 = new Word();
        w2.Copy(w);
        Assert.assertEquals(w.toString(), w2.toString());

        // setBit() with false
        for(int i=0; i<w.WORD_SIZE; i = i+2)
            w.setBit(i, new Bit(false));
        
        // set()
        w.set(0);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F", w.toString());
        Assert.assertEquals(0, w.getUnsigned());
        Assert.assertEquals(0, w.getSigned());

        w.set(1);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,T", w.toString());
        Assert.assertEquals(1, w.getUnsigned());
        Assert.assertEquals(1, w.getSigned());


        w.set(5);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,T,F,T", w.toString());
        Assert.assertEquals(5, w.getUnsigned());
        Assert.assertEquals(5, w.getSigned());


        w.set(16);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,T,F,F,F,F", w.toString());
        Assert.assertEquals(16, w.getUnsigned());
        Assert.assertEquals(16, w.getSigned());


        w.set(44);
        Assert.assertEquals("F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,F,T,F,T,T,F,F", w.toString());
        Assert.assertEquals(44, w.getUnsigned());
        Assert.assertEquals(44, w.getSigned());


        w.set(-1);
        Assert.assertEquals("T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T", w.toString());
        Assert.assertEquals(-1, w.getSigned());


        w.set(-5);
        Assert.assertEquals("T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,F,T,T", w.toString());
        Assert.assertEquals(-5, w.getSigned());


        w.set(-16);
        Assert.assertEquals("T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,F,F,F,F", w.toString());
        Assert.assertEquals(-16, w.getSigned());


        w.set(-44);
        Assert.assertEquals("T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,F,T,F,T,F,F", w.toString());
        Assert.assertEquals(-44, w.getSigned());


        //00001111011001011100101111111010
        w.set(258329594);
        Assert.assertEquals("F,F,F,F,T,T,T,T,F,T,T,F,F,T,F,T,T,T,F,F,T,F,T,T,T,T,T,T,T,F,T,F", w.toString());
        Assert.assertEquals(258329594, w.getUnsigned());
        Assert.assertEquals(258329594, w.getSigned());


        //01010001100001011110001000001100
        w.set(1367728652);
        Assert.assertEquals("F,T,F,T,F,F,F,T,T,F,F,F,F,T,F,T,T,T,T,F,F,F,T,F,F,F,F,F,T,T,F,F", w.toString());
        Assert.assertEquals(1367728652, w.getUnsigned());
        Assert.assertEquals(1367728652, w.getSigned());


        //-01110111111000100101111000010000
        w.set(-2011323920);
        Assert.assertEquals("T,F,F,F,T,F,F,F,F,F,F,T,T,T,F,T,T,F,T,F,F,F,F,T,T,T,T,T,F,F,F,F", w.toString());
        Assert.assertEquals(-2011323920, w.getSigned());



        //-00110111110111110111000010000100
        w.set(-937390212);
        Assert.assertEquals("T,T,F,F,T,F,F,F,F,F,T,F,F,F,F,F,T,F,F,F,T,T,T,T,F,T,T,T,T,T,F,F", w.toString());
        Assert.assertEquals(-937390212, w.getSigned());
    }

    @Test
    public void Word_logic() throws Exception{
        // TODO: AND 

        // TODO: OR 
        
        // TODO: NOT
        
        // TODO: XOR
        
        // TODO: rightShift
        
        // TODO: leftShift

    }
}
