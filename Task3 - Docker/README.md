# Task 3 - Docker

## Environment

```
$ cat /proc/version
Linux version 3.10.0-693.21.1.el7.x86_64 (builder@kbuilder.dev.centos.org) (gcc version 4.8.5 20150623 (Red Hat 4.8.5-16) (GCC) ) #1 SMP Wed Mar 7 19:03:37 UTC 2018
$ cat /etc/redhat-release
CentOS Linux release 7.4.1708 (Core)
```

## JDK Mirror

```
docker pull openjdk:8-jdk-alpine
```

## Dockerfile

```
FROM openjdk:8-jdk-alpine
MAINTAINER Diving-Fish
EXPOSE 8080
COPY ./wordladder.jar /wordladder.jar
ENTRYPOINT [ "java", "-jar", "/wordladder.jar" ]
```

## Run Docker

```
docker run wordladder:v1.0 -p 8080:8080
```

![2][]

## Push Docker

```
docker tag wordladder:v1.0 divingfish/wordladder:v1.0
docker push divingfish/wordladder:v1.0
```

![1][]

Link: https://hub.docker.com/r/divingfish/wordladder

[1]: ./1.png
[2]: ./2.png

