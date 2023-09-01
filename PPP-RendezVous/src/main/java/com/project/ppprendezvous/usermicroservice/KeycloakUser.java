package com.project.ppprendezvous.usermicroservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeycloakUser {
        @NotNull(message = "username must not be null")
        @NotBlank(message = "username can't be blank")
        @NotEmpty(message = "username can't be empty")
        @Column(unique = true)
        @Length(min = 5,max = 12,message = "username can't be more than 12 character or less than 5")
        private String username;
        @NotNull(message = "Email must not be null")
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email can't be blank")
        @NotEmpty(message = "Email can't be empty")
        private String email;
}
