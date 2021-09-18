package io.github.nhannht.webquizzangular.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class QuizDTOWithoutAns {
    @JsonProperty
    String title;
    @JsonProperty
    String text;
    @JsonProperty
    List<String> options;
    @JsonProperty
    Long id;

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

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
