package ru.otus.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.dto.AuthorDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorsResponseDto {
    private List<AuthorDto> data;
}
