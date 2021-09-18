package io.github.nhannht.webquizzangular.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Map;

@Table(name = "user")
@Entity
public class User {
    @Id
    @JsonProperty
    @GeneratedValue
    Long id;

    @JsonProperty
    @Email
    @Column(unique = true)
    String email;

    @JsonProperty
    String password;

    @OneToMany
    @JsonProperty
    List<Quiz> quizzes;

    public Long getId() {
        return id;
    }

    @JsonProperty
    @ElementCollection
    Map<String,Long> idOfQuizCollection;

    public void addIdQuizSolve(String completedAt,Long id){
        this.idOfQuizCollection.put(completedAt,id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Long> getIdOfQuizCollection() {
        return idOfQuizCollection;
    }

    public void setIdOfQuizCollection(Map<String, Long> idOfQuizCollection) {
        this.idOfQuizCollection = idOfQuizCollection;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
