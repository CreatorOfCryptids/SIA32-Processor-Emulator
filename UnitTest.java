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
        Assert.assertEquals("T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F,T,F", w.toString());

        // copy()
        Word w2 = new Word();
        w2.Copy(w);
        Assert.assertEquals(w.toString(), w2.toString());

        // setBit() with false
        for(int i=0; i<w.WORD_SIZE; i = i+2)
            w.setBit(i, new Bit(false));
        
        // TODO: set()

        // TODO: getUnsigned()

        // TODO: getSigned()

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
