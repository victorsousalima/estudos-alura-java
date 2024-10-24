package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarTutorDto(
        @NotNull
        Long id,
        String nome,
        String telefone,
        String email
) {
}
