# Task 4 - Container Simulator

Implement a simple container simulator by native Java. Source code is under `/Simulator`.

### Usage

```
new Simulator(int producers, int consumers, int requestPerConsumer, int requestDelay, int responseDelay, double responseDelayVariance, int timeout, int threshold).simulate();
```
`producers`: Numbers of producers.

`consumers`: Numbers of consumers.

`requestPerConsumer`: Requests will be send by each consumer.

`requestDelay`: Time between two requests (cannot be 0).

`responseDelay`: Time from receive to response (cannot be 0).

`responseDelayVariance`: Variance of response time. For example, if response time is 100 ms, variance is 0.2, time will be random from 80 to 120 ms.

`timeout`: request's timeout.

`threshold`: a bound with which container will change strategy.

### Test

Case 1: 1 producer to 1 consumer

```
new Simulator(1, 1, 20, 20, 100, 0.2, 300, 5).simulate();

Output:
[0 ms] Consumer 1 sends Request #1
[0 ms] Producer 1 receives Request #1, will consuming 98 ms
[20 ms] Consumer 1 sends Request #2
[40 ms] Consumer 1 sends Request #3
[60 ms] Consumer 1 sends Request #4
[80 ms] Consumer 1 sends Request #5
[98 ms] Producer 1 responses
[98 ms] Producer 1 receives Request #2, will consuming 112 ms
[100 ms] Consumer 1 sends Request #6
[120 ms] Consumer 1 sends Request #7
Change strategy to stack...
[140 ms] Consumer 1 sends Request #8
[160 ms] Consumer 1 sends Request #9
[180 ms] Consumer 1 sends Request #10
[210 ms] Producer 1 responses
[210 ms] Producer 1 receives Request #10, will consuming 106 ms
[316 ms] Producer 1 responses
[316 ms] Producer 1 receives Request #9, will consuming 116 ms
[339 ms] Request #3 timed out
[359 ms] Request #4 timed out
[379 ms] Request #5 timed out
[399 ms] Request #6 timed out
[419 ms] Request #7 timed out
[432 ms] Producer 1 responses
[432 ms] Producer 1 receives Request #8, will consuming 94 ms
[526 ms] Producer 1 responses
[526 ms] All requests finished. 5 Handled, 5 Timeout
```

Case 2: 5 producer to 3 consumer

```
new Simulator(5, 3, 6, 20, 100, 0.2, 150, 5).simulate();

Output:
[0 ms] Consumer 1 sends Request #1
[0 ms] Consumer 2 sends Request #2
[0 ms] Consumer 3 sends Request #3
[0 ms] Producer 1 receives Request #1, will consuming 90 ms
[0 ms] Producer 2 receives Request #2, will consuming 104 ms
[0 ms] Producer 3 receives Request #3, will consuming 100 ms
[20 ms] Consumer 1 sends Request #4
[20 ms] Consumer 2 sends Request #5
[20 ms] Consumer 3 sends Request #6
[20 ms] Producer 4 receives Request #4, will consuming 109 ms
[20 ms] Producer 5 receives Request #5, will consuming 81 ms
[40 ms] Consumer 1 sends Request #7
[40 ms] Consumer 2 sends Request #8
[40 ms] Consumer 3 sends Request #9
[60 ms] Consumer 1 sends Request #10
Change strategy to stack...
[60 ms] Consumer 2 sends Request #11
[60 ms] Consumer 3 sends Request #12
[80 ms] Consumer 1 sends Request #13
[80 ms] Consumer 2 sends Request #14
[80 ms] Consumer 3 sends Request #15
[90 ms] Producer 1 responses
[90 ms] Producer 1 receives Request #13, will consuming 119 ms
[100 ms] Producer 3 responses
[100 ms] Consumer 1 sends Request #16
[100 ms] Consumer 2 sends Request #17
[100 ms] Consumer 3 sends Request #18
[100 ms] Producer 3 receives Request #16, will consuming 102 ms
[101 ms] Producer 5 responses
[101 ms] Producer 5 receives Request #17, will consuming 92 ms
[104 ms] Producer 2 responses
[104 ms] Producer 2 receives Request #18, will consuming 96 ms
[129 ms] Producer 4 responses
[129 ms] Producer 4 receives Request #14, will consuming 90 ms
[169 ms] Request #6 timed out
[189 ms] Request #7 timed out
[189 ms] Request #8 timed out
[189 ms] Request #9 timed out
[193 ms] Producer 5 responses
[193 ms] Producer 5 receives Request #15, will consuming 99 ms
[200 ms] Producer 2 responses
[200 ms] Producer 2 receives Request #10, will consuming 118 ms
[202 ms] Producer 3 responses
[202 ms] Producer 3 receives Request #11, will consuming 82 ms
[209 ms] Producer 1 responses
[209 ms] Producer 1 receives Request #12, will consuming 109 ms
[219 ms] Producer 4 responses
[284 ms] Producer 3 responses
[292 ms] Producer 5 responses
[318 ms] Producer 1 responses
[318 ms] Producer 2 responses
[318 ms] All requests finished. 14 Handled, 4 Timeout
```

Case 3: 5 producer to 3 consumer, but change thershold to 10

```
new Simulator(5, 3, 6, 20, 100, 0.2, 150, 20).simulate();
Output:
[0 ms] Consumer 1 sends Request #1
[0 ms] Consumer 2 sends Request #2
[0 ms] Consumer 3 sends Request #3
[0 ms] Producer 1 receives Request #1, will consuming 89 ms
[0 ms] Producer 2 receives Request #2, will consuming 97 ms
[0 ms] Producer 3 receives Request #3, will consuming 89 ms
[20 ms] Consumer 1 sends Request #4
[20 ms] Consumer 2 sends Request #5
[20 ms] Consumer 3 sends Request #6
[20 ms] Producer 4 receives Request #4, will consuming 111 ms
[20 ms] Producer 5 receives Request #5, will consuming 104 ms
[40 ms] Consumer 1 sends Request #7
[40 ms] Consumer 2 sends Request #8
[40 ms] Consumer 3 sends Request #9
[60 ms] Consumer 1 sends Request #10
[60 ms] Consumer 2 sends Request #11
[60 ms] Consumer 3 sends Request #12
[80 ms] Consumer 1 sends Request #13
[80 ms] Consumer 2 sends Request #14
[80 ms] Consumer 3 sends Request #15
[89 ms] Producer 1 responses
[89 ms] Producer 3 responses
[89 ms] Producer 1 receives Request #6, will consuming 107 ms
[89 ms] Producer 3 receives Request #7, will consuming 102 ms
[97 ms] Producer 2 responses
[97 ms] Producer 2 receives Request #8, will consuming 84 ms
[100 ms] Consumer 1 sends Request #16
[100 ms] Consumer 2 sends Request #17
[100 ms] Consumer 3 sends Request #18
[124 ms] Producer 5 responses
[124 ms] Producer 5 receives Request #9, will consuming 80 ms
[131 ms] Producer 4 responses
[131 ms] Producer 4 receives Request #10, will consuming 95 ms
[181 ms] Producer 2 responses
[181 ms] Producer 2 receives Request #11, will consuming 109 ms
[191 ms] Producer 3 responses
[191 ms] Producer 3 receives Request #12, will consuming 99 ms
[196 ms] Producer 1 responses
[196 ms] Producer 1 receives Request #13, will consuming 101 ms
[204 ms] Producer 5 responses
[204 ms] Producer 5 receives Request #14, will consuming 102 ms
[226 ms] Producer 4 responses
[226 ms] Producer 4 receives Request #15, will consuming 95 ms
[249 ms] Request #16 timed out
[249 ms] Request #17 timed out
[249 ms] Request #18 timed out
[290 ms] Producer 2 responses
[290 ms] Producer 3 responses
[297 ms] Producer 1 responses
[306 ms] Producer 5 responses
[321 ms] Producer 4 responses
[321 ms] All requests finished. 15 Handled, 3 Timeout
```

We can learn that from Case 2 and Case 3:

*When threshold is bigger, missed request will be fewer,* ***but newer request will miss.***