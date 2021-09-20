![Heroku](https://heroku-badge.herokuapp.com/?app=sheltered-harbor-58480)

![ci](https://github.com/nhannht/web-quizz-angular-backend/actions/workflows/maven.yml/badge.svg)

View 
### Endpoint
#### View directly at https://sheltered-harbor-58480.herokuapp.com/swagger-ui.html

#### In json form : https://sheltered-harbor-58480.herokuapp.com/v3/api-docs/

#### Or here
|endpoint|description|example|
|---|---|---|
|get /api/quizzes?page={number}|fetch max 10 quizzes of user from page with page number|
|post /api/quizzes|insert quizzes, require basic authentication |[here](#help1)|
|get /api/quizzes/completed?page={number}|fetch max 10 quizzes quized that are completed with page number |
|delete /api/quizzes/{id}|remove quiz with id, this quiz must be own by user|
|post /api/quizzes/{id}/solve|solve quiz that own by user|[here](#help2)|
|post /api/register |register require username and password to create new user|
#### <a name="help1">How to create quiz</a>
You can use default user : email: nhanclassroom@gmail.com, password: password to test without regist, this app use basic auth method
``` json
{
  "title" : "Title here",
  "text" : "Who is Nhan",
  "options" : ["Human","Animal","Bug"],
  "answer" : [1,3] => Can be more than one
}
```
#### <a name="help2">How to solve quiz </a>
You need login first
```json
{
  "answer": [1,2] 
}
```
