package br.com.simoes.consultoria.auth.apis.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserCreateDTO(
        @JsonProperty("user-name") @NotNull @Valid String username,
        @JsonProperty("first-name") @NotNull @Valid String firstName,
        @JsonProperty("last-name") @NotNull @Valid String lastName,
        @JsonProperty("email") @NotNull @Valid String email
) { }
