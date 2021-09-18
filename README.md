![Heroku](https://heroku-badge.herokuapp.com/?app=sheltered-harbor-58480)

![ci](https://github.com/nhannht/web-quizz-angular-backend/actions/workflows/maven.yml/badge.svg)
### Endpoint
|endpoint|description|example|
|---|---|---|
|get /api/quizzes?page={number}|fetch max 10 quizzes of user from page with page number|
|post /api/quizzes|insert quizzes, require basic authentication |[here](#help1)|
|get /api/quizzes/completed?page={number}|fetch max 10 quizzes quized that are completed with page number |
|delete /api/quizzes/{id}|remove quiz with id, this quiz must be own by user|
|post /api/quizzes/{id}/solve|solve quiz that own by user|[here](#help2)|
|post /api/register |register require username and password to create new user|

#### <a name="help1">How to create quiz</a>
You need login first
```json 
{
  "title" : "Title here",
  "text" : "Who is Nhan",
  "options" : ["Human","Animal","Bug"],
  "answer" : [1,2] => Can be more than one
}

```
#### <a name="help2">How to solve quiz </a>
You need login first
```json
{
  "answer": "[1,2]" 
}
```
