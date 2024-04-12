package Compiler;

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
        if(inLineIndex < currentLine.length && currentLine[inLineIndex].length() != 0)
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
        if(inLineIndex + i < currentLine.length && currentLine[inLineIndex + i].length() != 0)
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
        if(inLineIndex < currentLine.length && currentLine[inLineIndex].length() != 0)
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
    public boolean moreWords(){
        // Make sure to still return false when spit returns {""} on an empty line.
        return (inLineIndex<currentLine.length && currentLine[inLineIndex].length() != 0);
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
}
