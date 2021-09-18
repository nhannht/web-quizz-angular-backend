package io.github.nhannht.webquizzangular.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Quiz {
    @Id
    @JsonProperty
    @GeneratedValue
    @Column
    Long id;

    @JsonProperty
    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    String title;

    @JsonProperty
    @Column(nullable = false)
    @NotBlank
    @NotEmpty
    String text;

    @JsonProperty
    @Column(nullable = false)
    @ElementCollection
    @Size(min = 2)
    List<String> options;

    @Column(nullable = false)
    @JsonProperty
    @ElementCollection
    @Size(min = 0)
    @NotNull
    List<Integer> answer;

    @JsonProperty
    @Column(name="completedAt")
    String completedAt;



    @ManyToOne(optional = false)
    @JoinColumn(name = "user_ID", nullable = false, referencedColumnName = "id")
    User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCompletedAt() {
        return completedAt;
    }



    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }


}
