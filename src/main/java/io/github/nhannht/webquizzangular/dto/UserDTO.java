package io.github.nhannht.webquizzangular.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UserDTO {
    @JsonProperty
    @Email
    @Pattern(regexp = ".*@.*\\..*")
            @NotEmpty
            @NotBlank
    String email;

    @JsonProperty
    @NotEmpty
    @NotBlank
    @Length(min=5)
    String password;

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
}
