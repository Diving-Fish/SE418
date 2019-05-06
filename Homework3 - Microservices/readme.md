# Homework 3 - Microservices

## Dockers:

Login: https://cloud.docker.com/repository/docker/divingfish/login

WordLadder: https://cloud.docker.com/u/divingfish/repository/docker/divingfish/wordladder

## Directory:

`Login/` : source code of login service.

`WordLadder/` : source code of wordladder service.

`Login-docker/` : docker builder of login service.

`WordLadder-docker/` : docker builder of wordladder service.

## Run docker

Run `login` on the background:

```
docker run -itd -P --name=login divingfish/login
```

Run `wordladder` on the foreground & link `login`

```
// On server, so use 0.0.0.0
docker run -p 0.0.0.0:8080:8080 --link=login:login divingfish/wordladder
```

## Test (With Postman)

With `x-www-form-urlencoded` body
```
username: user,
password: user
```

*Post* `http://www.diving-fish.com:8080/login`, returns

```
{
    "status": 200,
    "message": "login success",
    "username": "user"
}
```

then *Get* `http://www.diving-fish.com:8080/generate?start=code&end=data`, returns

```
{
    "status": 200,
    "wordladder": [
        "code",
        "cade",
        "cate",
        "date",
        "data"
    ]
}
```

If we *Post* `http://www.diving-fish.com:8080/logout`, then *Get* `http://www.diving-fish.com:8080/generate?start=code&end=data`, returns

```
{
    "status": 401,
    "message": "You need login to do this"
}
```

The service is still running now, TA can try the test by yourselves :)