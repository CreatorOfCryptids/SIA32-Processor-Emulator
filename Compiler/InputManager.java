package Compiler;

public class InputManager {
    
    private String input;
    private int currentIndex;

    public InputManager(String input){
        this.input = input;
        currentIndex = 0;
    }

    /**
    * The peek() method.
    * @param i How far ahead the method looks.
    * @return The char i characters ahead of the index.
    * Looks “i” characters ahead and returns that character; doesn’t move the index.
    */
    public char peek(int i){
        // Make sure the index is not beond the end of the string.
        if((currentIndex+i < input.length()))
            return input.charAt(currentIndex+i);
        else
            return ' ';
    }

    /**
     * The peek() overloaded method.
     * Yes, I am not ashamed to admit that I made a whole extra method to get out of typing one single extra letter. Judge me all you want, but it works.
     * @return The next character
     */
    public char peek(){
        // Make sure the index is not beond the end of the string.
        if((currentIndex < input.length()))
            return input.charAt(currentIndex);
        else
            return ' ';
    }

    /**
    * The peekString() method. 
    * @param i How far ahead the method will look.
    * @return A string of the i characters in the file after the current index.
    * Returns a string of the next “i” characters but doesn’t move the index
    */
    public String peekString(int i){
        return input.substring(currentIndex, currentIndex+i);
    }

    /**
     * The getChar() method.
     * @return The next char
     * Returns the next character and moves the index
     */
    public char getChar(){
        char output = input.charAt(currentIndex);
        currentIndex++;
        //log(output);
        return output;   
    }

    /**
     * The swallow method.
     * @param i How far the index moves
     * Moves the index ahead “i” positions
     */
    public void swallow(int i) throws ArrayIndexOutOfBoundsException{
        if (i >=0)
            currentIndex += i;
        else 
            throw new ArrayIndexOutOfBoundsException();
        //log(fileData.substring(currentIndex-i, currentIndex));
    }

    /**
     * The isDone() method.
     * @return True if the index is at the end of the document. False if not.
     */
    public boolean isDone(){
        return (input.length() <= currentIndex);
    }

    /**
     * The remainder() method.
     * @return The rest of the input file as a single string.
     */
    public String remainder() {
        return input.substring(currentIndex);
    }

    /**
     * The remaingin
     * @return the amount of remaining character in the string
     */
    public int remaining(){
        return input.length() - currentIndex;
    }
}
