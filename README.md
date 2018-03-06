#Comments App
Following are examples of the calls supported by the application. For more information a swagger documentation can be accessed at:

`http://localhost:8080/swagger-ui.html`

##Registration example:

`POST http://localhost:8080/register`
```
{
	"username": "test",
	"password": "password"
}
```

##Login example

`POST http://localhost:8080/login`
```
{
	"username": "test",
	"password": "password"
}
```

In response of a login call you'll receive a token (JWT), use that in the Authorization header (Authorization: Bearer \<token\>) for any calls to endpoints which are protected. 

##Get all comments

`GET http://localhost:8080/comment`

###Filter comments by author
`GET http://localhost:8080/comment?author=test`

##Create new comment

`POST http://localhost:8080/comment`

```
{
    "value": "Some random content"
}
```

##Update a comment

`PUT http://localhost:8080/comment/1`

```
{
    "value": "Some random and edited content"
}
```

##Delete a comment

`DELETE http://localhost:8080/comment/1`