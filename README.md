![Heroku](https://heroku-badge.herokuapp.com/?app=sheltered-harbor-58480)

![ci](https://github.com/nhannht/web-quizz-angular-backend/actions/workflows/maven.yml/badge.svg)

View 
### Endpoint
View directly at https://sheltered-harbor-58480.herokuapp.com/swagger-ui.html

#### <a name="help1">How to create quiz</a>
You can use default user : email: nhanclassroom@gmail.com, password: password to test without regist, this app use basic auth method
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
  "answer": "[1,3]" 
}
```
