package Compiler;

<<<<<<< HEAD
import java.util.Optional;

public class InputHandler {

    private String[] fileData;      // Each line of the input
    private String[] currentLine;   // Each word in the current line.
    private int inLineIndex;        // The index of the current word in the current line
    private int lineCount;          // The current line's index.

    /**
     * Constructor
     * 
     * @param file The String[] containing the information in the file.
     */
    public InputHandler(String[] file){
        this.fileData = file;
        inLineIndex = 0;
        lineCount = 0;
        currentLine = fileData[lineCount++].split(" ");
    }

    /**
    * Looks at the next word in the line.
    * 
    * @return An Optional containing the next word in the current line.
    */
    public Optional<String> peek(){
        if(inLineIndex < currentLine.length)
            return Optional.of(currentLine[inLineIndex]);
        else
            return Optional.empty();
    }

    /**
     * Looks at the word i places ahead of the current index.
     * 
     * @param i The amount of skipped words.
     * @return An Optional containing the speficied word in the line.
     */
    public Optional<String> peek(int i){
        // Make sure the index is not beyond the end of the line.
        if(inLineIndex + i < currentLine.length)
            return Optional.of(currentLine[inLineIndex + 1]);
        else
            return Optional.empty();
    }

    /**
     * Returns the next word in the current line and increments to the next word. 
     * 
     * @return An Optional containing the next word in the current line.
     */
    public Optional<String> getWord(){
        if(inLineIndex < currentLine.length)
            return Optional.of(currentLine[inLineIndex++]);
        else
            return Optional.empty();
    }

    /**
     * Moves to the next line.
     * 
     * @return True if successful. False otherwize.
     */
    public boolean nextLine(){
        if (lineCount < fileData.length){
            currentLine = fileData[lineCount++].split(" ");
            inLineIndex = 0;
            return true;
        }
        else 
            return false;
    }

    /**
     * Returns true while there is still more information
     * 
     * @return True if there are more words in this line. False otherwize.
     */
    public boolean isLineDone(){
        // Make sure to still return false when spit returns {""} on an empty line.
        return (inLineIndex < currentLine.length && !currentLine[0].equals(""));
    }

    /**
     * Checks if there are more lines in the input file.
     * 
     * @return True if there are more lines. False otherwize.
     */
    public boolean hasMoreLines(){
        return (lineCount < fileData.length);
    }

    /**
     * DEBUGGING HELPER!!!
     * 
     * @return The current inlineIndex.
     */
    public int getInLineIndex(){
        return inLineIndex;
    }

    /**
     * DEBUGGING HELPER!!!
     * 
     * @return The current lineCount
     */
    public int getLineCount(){
        return lineCount;
    }
=======
public class InputHandler {

    private String fileData;
    private int currentIndex;

    /**
     * Constructor
     * @param file The sting containing the information in the file.
     */
    InputHandler(String file){
        this.fileData = file;
    }

    /**
    * Looks “i” characters ahead and returns that character; doesn’t move the index.
    * 
    * @param i How far ahead the method looks.
    * @return The char i characters ahead of the index.
    */
    public char peek(int i){
        // Make sure the index is not beond the end of the string.
        if((currentIndex+i < fileData.length()))
            return fileData.charAt(currentIndex+i);
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
        if((currentIndex < fileData.length()))
            return fileData.charAt(currentIndex);
        else
            return ' ';
    }

    /**
    * The peekString() method. 
    * @param i How far ahead the method will look.
    * @return A string of the next “i” characters but doesn’t move the index
    */
    public String peekString(int i){
        return fileData.substring(currentIndex, currentIndex+i);
    }

    /**
     * The getChar() method.
     * @return The next character and moves the index
     */
    public char getChar(){
        char output = fileData.charAt(currentIndex++);
        return output;
    }

    /**
     * Moves the index ahead “i” positions
     * 
     * @param i How far the index moves
     */
    public void swallow(int i) throws Exception{
        if (i >=0)
            currentIndex += i;
        else 
            throw new Exception("The inputed index " + i + " is not a valid index.");
    }

    /**
     * Returns flase while there is still more information
     * 
     * @return True if the index is at the end of the document. False if not.
     */
    public boolean isDone(){
        return (fileData.length() <= currentIndex);
    }

    /**
     * Returns the remainder of the string.
     * 
     * @return The rest of the input file as a single string.
     */
    public String remainder() {
        return fileData.substring(currentIndex);
    }

    /**
     * Shhhhh! This is just for testing pourposes. 
     * The getCurrentIndex() method.
     * @return The current index of the finger.
     *
    public int getCurrentIndex(){
        return currentIndex;
    }/**/
>>>>>>> 5308431 (Started on the compiler.)
}
