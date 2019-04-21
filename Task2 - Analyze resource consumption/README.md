# Analyze resource consumption

## Environment

- CPU: Intel(R) Core(TM) i7-7700HQ CPU @ 2.80GHz
- Memory: 8GB RAM
- Java version: 1.8.0_201
- REST Service: SpringBoot 2.1.4 RELEASE
- Monitor: Prometheus 2.9.1


## Run Prometheus

![1][]

![2][]

When starting service with no requests, CPU and memory runs stably with 18% using or under 100MB per area.


## Sending Request

### Few Requests

Use Postman to send two requests:

code->data:

![4][]

apple->catch:

![3][]

Obviously, longer word ladder will comsume more time.

After sending these requests, JVM's memory spaces alter:

![5][]

The service creates new objects and store them in **Eden Space** and takes about 60M memory spaces.

### Request Queue

Using postman to send 100 request (with apple->catch) continously:

![6][]

It runs well with about 500ms requesting time in average.

![7][]

Promethus shows CPU and memory status:

![8][]

In memory, just **Eden Space** alters. Maybe objects created by service needn't be used again, so they are just discarded.

![9][]

CPU using increase to about 50%.

### Parallel Request Queues

With sending 4 queue with 100 requests parallelly (open 4 postman runners), CPU and memory graph:

![11][]

CPU using is about 100%.

![10][]

**Eden Space** still handle a lot of work. When requesting, some objects accessed frequently are added into **Old Gen**, and remain until the Old Gen is full (Gerbage Collection).

![12][]

When requesting parallelly, response time increase above 1000ms. It reveals REST service will handle requests more concurrently and slowly when receiving a lot of requests.


[1]: ./1.png
[2]: ./2.png
[3]: ./3.png
[4]: ./4.png
[5]: ./5.png
[6]: ./6.png
[7]: ./7.png
[8]: ./8.png
[9]: ./9.png
[10]: ./10.png
[11]: ./11.png
[12]: ./12.png