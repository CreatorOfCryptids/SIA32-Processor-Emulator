compiler: compilerer
	java Compiler.Compilerer TestCompile.sia32 > TestCompile.out

#processor: comp_processor
#	java Processor.Processor

processor: Processor/*.java
	javac Processor/Processor.java

compilerer: Compiler/*.java
	javac Compiler/Compilerer.java

clean:
	rm Compiler/*.class Processor/*.class
