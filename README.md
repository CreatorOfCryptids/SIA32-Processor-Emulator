# SIA32 Chip Emulator
This is a chip emulator that I made for my ICSI404 Computer Architecture class. It is a computer chip emulator that simulates reading from Memory, caching, clock cycles, and an interface for the SIA32 programming language. Also included is a compiler for the language, and a test class that was used to test the efficiency benefits of caches.
## SIA32 Assembly Language
### Specifications:
The SIA32 processor has 32 general purpose registers, 3 special purpose registers (PC, SP, and CI) and 4 KB of Memory. 
The 0th register (R0) is always equal to 0 and cannot be changed.

### Register Formats:

3 Register:
| Immediate (8) | RS1 (5) | RS2 (5) | Function (4) | RD (5) | OpCode (5) |
| --- | --- | --- | --- | --- | --- |

2 Register:
| Immediate (13) | RS2 (5) | Function (4) | RD (5) | OpCode (5) |
| --- | --- | --- | --- | --- |

Destination Only:
| Immediate (18)  | Function (4) | RD (5) | OpCode (5) |
| --- | --- | --- | --- |

No Register:
| Immediate (27) | OpCode (5) |
| --- | --- |

### Instruction Definitions:
| | 3 Registers (11) | 2 Registers (10) | Destination Only (01) | No Register |
| :--- | :--- | :--- | :--- | :--- |
| MATH (000) | RD <- RS1 MOP RS2 | RD <- RD MOP RS2 | COPY: RD <- IMM | HAULT |
| BRANCH (001) | PC <- RS1 BOP RS2 ? PC + IMM : PC | PC <- RS2 BOP RD ? PC + IMM : PC | JUMP: PC <- PC + IMM | JUMP: PC <- IMM |
| CALL (010) | PC <- RS1 BOP RS2 ? PUSH pc; PC <- RD + IMM : PC | PC <- RS2 BOP RD ? PUSH PC; PC + IMM | push PC; PC <- RD + IMM | PUSH PC; PC <- IMM |
| PUSH (011) | MEM[--SP] <- RS1 MOP RS2 | MEM[--SP] <- RD MOP RS2 | MEM[--SP] <- RD MOP IMM | Unused |
| LOAD (100) | RD <- MEM[RS1 + RS2] | RD <- MEM[RS2 + IMM] | RD <- MEM [RD + IMM] | RETURN: (PC <- POP) |
| STORE (101) | MEM[RD + RS1] <- RS2 | MEM[RD + IMM] <- RS2 | MEM[RD] <- IMM | Unused |
| POP/INTERRUPT (110) | PEEK RD <- MEM[SP - RS1 + RS2] | PEEK: RD <- MEM[SP+ (RS + IMM)] | POP: RD <- MEM[SP++] | INTERRUPT: PUSH PC; PC <- intvec[IMM] Unimplemented |

PC - Process Counter, Stores the address of the currently running instruction.

SP - Stack Pointer

CI - Current Instruction.

IMM - Immediate

MEM - Main Memory

### MOPs (Math Opperations):
| Bit Pattern | Operator | Operation |
| :---: | :---: | :---|
| 1000 | AND | AND |
| 1001 | OR | OR |
| 1010 | XOR | XOR |
| 1011 | NOT | NOT (NOT Op1, ignore Op2) |
| 1100 | LEFT | Left Shift |
| 1101 | RIGHT | Right Shift |
| 1110 | ADD | Add |
| 1111 | SUB | Subtract |
| 0111 | MULT | Multiply |

### BOPs (Boolean Operations):

| Bit Pattern | Operator | Operation |
| :---: | :--: | :--- |
| 0000 | EQ | Equals |
| 0001 | NE | Not equal |
| 0010 | LT | Less than |
| 0011 | GE | Greater than or equal |
| 0100 | GT | Greater than |
| 0101 | LE | Less than or equal |

## Packages:
### Processor:
The Processor Package stores the programs that emulate the different parts of an physical Processor.
#### Bit
This class just stores a boolean value, with an easier API.
#### Word
This is a 32 Bit array with an easier API.
#### ALU
This is where the Bit math happens in the processor. It has three public Word members: op1, op2, and result. It performs the specified operation when ALU.doOperation() is called. All operations cost 2 cycles except the multiplication operation, which costs 10 cycles.
#### Main Memory
This is the main memory of the processor. It is an array of 1024 Words (4 kB) that stores the processes' instructions and stack.
#### Instruction Cache
This is a cache that specifically holds Instructions to reduce the amount of clock cycles. Access cost 10 cycles on a cache miss, or 350 cycles on a cache miss.
#### L2 Cache
This cache stores 4 sets of 8 Words that have recently been used by the process. At this level, a cache hit costs 20 cycles, and a cache miss costs 350 cycles.
#### Processor
This class is where the instructions are processed. Instructions are processed in four phases: fetch(), decode(), execute(), and store(). In the fetch phase, the instructions are read into the processor. Next the instructions are decoded to find the opcode, registers, function, and immediate value. Then, in the execute phase, the specified instructions are executed. Finally, in the store phase, the outcomes of the execute phase are updated in the registers, and/or in MainMemory.

### Compiler:
The compiler is a recursive decent compiler that takes human readable source code and returns the specified instruction in bytes. (More specifically in an array of 32 char long Strings filled with '0's and '1's).
#### Lexer
This class takes in the source file input as an array of Strings, and returns a LinkedList of Tokens. This is done by passing the input into the InputHandler, then looping through each word to check for keywords, registers, immediate values, or comments.
#### InputHandler
This class takes in the input file as an array of Strings, then separates each array by spaces into easily parse-able words.
#### Token
This class just stores a type and an Optional value. The type specifies the type of the token, and the value contains the register number, if its a register specifier, or the immediate value if it's an immediate.
#### Assembler
This class takes a LinkedList of Tokens, and returns a LinkedList of Instructions. It performs this task by recursively descending down each level of instruction to create the specified instruction.
#### TokenHandler
This class takes a LinkedList of Tokens and outputs them in an easier to use API.
#### Instruction
This abstract class specifies the behavior of each subclass of Instruction.
#### Compilerer
This class takes in either the name of an input file, or an array of String (separated by newlines), and outputs either an array of String, or a file containing the compiled instructions.

MAIN: Takes in a target file containing SIA32 source code and outputs a file containing the compiled code.

## Testing Caching's Effects on the Clock Cycle count of an SIA32 Processor
### Introduction:
Since the dawn of computing, the most important question on every computer architect,
computer scientist and electrical engineer’s mind has been: “ But what if faster???” At first electrical
engineers increased the speed of their computers by making it smaller, and increasing the clock cycle.
This worked well, until the speed of the the CPU surpassed the speed of memory. To fix this, we made
different layers of memory, with some being smaller, but faster, and others being larger, but slower.
These different layers are called caches.

### Methods:
To examine the effect of caching on a SIA32 Processor, I implemented a SIA32 Processor in
Java, then tested the expected clock cycles of three different programs. The three programs consisted
of: summing 20 integers in an array, summing 20 integers in a linked list, and summing an array of 20
integers in reverse. I then tested those same three programs three times. One round without any caches,
one round with an instruction cache, and one round with both an instruction cache and an L2 Cache.

### Results:
The results shown in Table 1, show that there was a dramatic decrease in clock speeds with each
successive cache.
| Test | No Cache | Instruction Cache | L2 Cache |
| :--- | :---: | :---: | :---: |
| Forward Array | 56000 | 16380 | 8450 |
| Linked List | 79778 | 30208 | 12908 |
| Reverse Array | 56000 | 16380 | 12940 |

### Discussion:
Each of the three programs were vastly improved by each successive layer of caching. The forward Array 
had the most dramatic decrease in cycles, with a 6.63x faster time in its L2Cache test compared with 
it’s no cache test. The other tests had similar results, with the Linked List test being 6.18x faster 
and the Reverse Array being 4.33x faster.

One interesting outcome was the difference between the Forward Array and Reverse Array L2Cache tests. 
In the prior two tests They had the same speed, however the L2 Cache had a greater performance increase 
on the Forward Array. This is due to the fact that each read loads the desired space in memory as well as 
the 7 spaces AFTER it. This put the reverse array at a disadvantage, as it had an L2 Cache miss each time 
it added a new number, while the Forward Array only had a cache miss every 8 reads.

### Conclusion:
Caching vastly improved the performance of the three tested functions, and it is likely that Caching would 
improve the performance of most other programs. However, the use of caching makes it faster to read arrays 
forward rather than backwards, as the caches only load the spaces after the read spot in memory.
