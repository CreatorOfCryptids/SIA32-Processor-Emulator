Clock cycle data:
| Test | No cache | Instruction Cache | Instruction and L2 Caches |
| :--- | :---: | :---: | :---: |
| Forward Array | 56000 | 16380 | 8450 |
| Forward Array init | 18638 | 8498 | 3798 |
| LinkedList | 79778 | 30208 | 12908 |
| LinkedList init | 37016 | 16686 | 7236 |
| Reverse Array | 56000 | 16380 | 12940 |
| Reverse Array init | 18638 | 8498 | 3798 |

Before Caches:
```
Generated sum: 10340

Testing IntArray...

Sum: 10340
Great success!!! :D
Clock cycles: 56000         // 18638 Cycles to Init

Testing IntList...

Sum: 10340
Great success!!! :D
Clock cycles: 79778         // 37016 Cycles to Init

Testing ReverseIntArray...

Sum: 10340
Great success!!! :D
Clock cycles: 56000         // 18638 Cycles to Init
```

After adding InstructionCache:
```
Generated sum: 11568

Testing IntArray...

Sum: 11568
Great success!!! :D
Clock cycles: 16380         // 8498 init cycles.

Testing IntList...

Sum: 11568
Great success!!! :D
Clock cycles: 30208         // 16686 init cycles.

Testing ReverseIntArray...

Sum: 11568
Great success!!! :D
Clock cycles: 16380         // 8498 init cycles.
```

After adding L2Cache:
```
Generated sum: 12632

Testing IntArray...

Sum: 12632
Great success!!! :D
Clock cycles: 8450          // 3798 init cycles.

Testing IntList...

Sum: 12632
Great success!!! :D
Clock cycles: 12908         // 7236 init cycles.

Testing ReverseIntArray...

Sum: 12632
Great success!!! :D
Clock cycles: 12940         // 3798 init cycles.
```
