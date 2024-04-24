package com.projeto.demo.dtos;

import jakarta.validation.constraints.NotBlank;

public record PostsDto(@NotBlank String title, @NotBlank String content) {

}
