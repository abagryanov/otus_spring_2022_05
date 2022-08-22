package ru.otus.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.dto.CommentDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsResponseDto {
    private List<CommentDto> data;
}
