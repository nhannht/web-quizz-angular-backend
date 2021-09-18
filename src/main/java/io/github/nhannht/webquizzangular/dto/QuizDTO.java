package io.github.nhannht.webquizzangular.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class QuizDTO {
    @JsonProperty
    @NotEmpty(message = "title cannot be empty")
    @NotBlank
    String title;

    @JsonProperty
    @NotBlank
    @NotEmpty(message = "description cannot be empty")
    String text;

    @JsonProperty
    @NotNull(message="options cannot be null")
    @Size(min = 1)
    List<String> options;

    @JsonProperty
    @Size(min = 0)
    List<Integer> answer;

    @JsonProperty
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
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

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
