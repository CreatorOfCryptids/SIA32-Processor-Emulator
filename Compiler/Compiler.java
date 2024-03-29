package Compiler;

public class Compiler {
    
    public static void main(String[] args) throws Exception{
        if(args.length != 2){
            throw new Exception("Wrong number of arguments.");
        }

        String inputFile = args[1];
        String outputFile = args[2];

    }
}
