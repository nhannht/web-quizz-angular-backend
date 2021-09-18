package io.github.nhannht.webquizzangular.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizOnlyWithIdAndDateCompleted {
    @JsonProperty
    Long id;

    @JsonProperty
    String completedAt;

    public QuizOnlyWithIdAndDateCompleted(Long id, String completedAt) {
        this.id = id;
        this.completedAt = completedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}
