# SIA32 Chip Emulator

## Packages:
### Processor
The Processor Package stores the programs that emulate the different parts of an IRL Processor.
#### Bit
This class just stores a boolean value, with an easier API.
#### Word
This is a 32 Bit long array with an easier API.
#### ALU
This is where the Bit math happens in the processor. It has three public Word members: op1, op2, and result. It performs the specified opperation only when ALU.doOperation() is called. 
#### Main Memory
This is the main memory of the processor. It is an array of 1024 Words (4 kB) that stores the processes instructions and stack.
#### Instruction Cache
This is a cache that specificly holds Instructions to reduce the amount of clock cycles. Acces costs 
#### L2 Cache

#### Processor

### Compiler
The compiler is a recursive decent compiler that takes Human readable source code and returns the specifed instruction in bytes. (More specificly in 32 char long Strings filled with '0's and '1's).

## SIA32 Assembly Language
### Specifications:
The SIA32 processor has 32 general purpose registers, 2 special purpose registers (PC, and SP) and 4 kB of RAM. 
The 0th register (R0) is always equal to 0 and cannot be changed.

### Register Formats:

3 Register:
| Immediate (8) | RS1 (5) | RS2 (5) | Function (4) | RD (5) | OpCode (5) |
| --- | --- | --- | --- | --- | --- |

2 Register:
| Immediate (13) | RS2 (5) | Function (4) | RD (5) | OpCode (5) |
| --- | --- | --- | --- | --- |

Dest Only:
| Immediate (18)  | Function (4) | RD (5) | OpCode (5) |
| --- | --- | --- | --- |

No Register:
| Immediate (27) | OpCode (5) |
| --- | --- |

### Instruction Definitions:
| | 3 Registers (11) | 2 Registers (10) | Destination Only (01) | No Register |
| :--- | :--- | :--- | :--- | :--- |
| MATH (000) | RD <- RS1 MOP RS2 | RD <- RD MOP RS2 | COPY: RD <- IMM | HAULT |
| BRANCH (001) | PC <- RS1 BOP RS2 ? PC + IMM : PC | PC <- RS2 BOP RD ? PC + IMM : PC | JUMP: PC <- PC + IMM | JUMP PC <- IMM |
| CALL (010) | PC <- RS1 BOP RS2 ? PUSH pc; PC <- RD + IMM : PC | PC <- RS2 BOP RD ? PUSH PC; PC + IMM | push PC; PC <- RD + IMM | PUSH PC; PC <- IMM |
| PUSH (011) | MEM[--SP] <- RS1 MOP RS2 | MEM[--SP] <- RD MOP RS2 | MEM[--SP] <- RD MOP IMM | Unused |
| LOAD (100) | RD <- MEM[RS1 + RS2] | RD <- MEM[RS2 + IMM] | RD <- MEM [RD + IMM] | RETURN (PC <- POP) |
| STORE (101) | MEM[RD + RS1] <- RS2 | MEM[RD + IMM] <- RS2 | MEM[RD] <- IMM | Unused |
| POP/INTERRUPT (110) | PEEK RD <- MEM[SP - RS1 + RS2] | PEEK: RD <- MEM[SP+ (RS + IMM)] | POP: RD <- MEM[SP++] | INTERRUPT: PUSH PC; PC <- intvec[IMM] Unimplemented |

PC - Process Counter, Stores the address of the currently running instruction.

SP - Stack Pointer

IMM - Immediate

MEM - Main Memory

### MOPs (Math Opperations):
| Bit Pattern | Operation |
| :--- | :--- |
| 1000 | AND |
| 1001 | OR |
| 1010 | XOR |
| 1011 | NOT (NOT Op1, ignore Op2) |
| 1100 | Left Shift |
| 1101 | Right Shift |
| 1110 | Add |
| 1111 | Subtract |
| 0111 | Multiply |

### BOPs (Boolean Operations):

| Bit Pattern | Operation |
| :--- | :--- |
| 0000 | EQ |
| 0001 | NE |
| 0010 | LT |
| 0011 | GE |
| 0100 | GT |
| 0101 | LE |

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
improve the performane of most other programs. However, the use of caching makes it faster to read arrays 
forward rather than backwards, as the caches only load the spaces after the read spot in memory.
