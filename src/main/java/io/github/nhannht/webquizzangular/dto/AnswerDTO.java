package io.github.nhannht.webquizzangular.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AnswerDTO {
    @JsonProperty
    @NotNull
    List<Integer> answer;


    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }
}
