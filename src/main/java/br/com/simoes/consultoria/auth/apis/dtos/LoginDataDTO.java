package br.com.simoes.consultoria.auth.apis.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record LoginDataDTO (
        @JsonProperty("username") @NotNull @Valid String user,
        @JsonProperty("password") @NotNull @Valid String password
) { }
