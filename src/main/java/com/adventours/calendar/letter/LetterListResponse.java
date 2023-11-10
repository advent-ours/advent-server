package com.adventours.calendar.letter;

import com.adventours.calendar.letter.domain.Letter;

import java.util.List;

public record LetterListResponse(
        Long userId,
        String content,
        String createdAt,
        String updatedAt
) {
    public static List<LetterListResponse> of(List<Letter> letters) {
        return letters.stream()
                .map(letter -> new LetterListResponse(
                        letter.getFromUser().getId(),
                        letter.getContent(),
                        letter.getCreatedAt().toString(),
                        letter.getUpdatedAt().toString()
                ))
                .toList();
    }
}
