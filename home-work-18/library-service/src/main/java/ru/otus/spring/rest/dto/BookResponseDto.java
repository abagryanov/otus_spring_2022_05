package ru.otus.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.dto.BookDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private BookDto data;
}
