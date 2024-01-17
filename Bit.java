

public class Bit{

    private boolean value;

    public Bit(boolean value){
        this.value = value;
    }

    public void set(boolean value){
        this.value = value;
    }

    public void toggle(){
        value = !value;
    }

    public void set(){
        value = true;
    }

    public void clear(){
        value = false;
    }

    public boolean getValue(){
        return value;
    }

    public Bit and(Bit other){
        if(value && other.getValue() )
            return new Bit(true);
        else
            return new Bit(false);
    }

    public Bit or(Bit other){
        if(value || other.getValue())
            return new Bit(true);
        else
            return new Bit(false);
    }

    public Bit xor(Bit other){
        if((value || other.getValue()) && !(value && other.getValue()))
            return new Bit(true);
        else
            return new Bit(false);
    }

    public Bit not(){
        return new Bit(!value);
    }

    public String toString(){
        if(value)
            return "T";
        else    
            return "F";
    }
}
