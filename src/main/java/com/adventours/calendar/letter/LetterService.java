package com.adventours.calendar.letter;

import com.adventours.calendar.calendar.domain.Calendar;
import com.adventours.calendar.calendar.persistence.CalendarRepository;
import com.adventours.calendar.exception.NotFoundCalendarException;
import com.adventours.calendar.exception.NotOwnerException;
import com.adventours.calendar.letter.domain.Letter;
import com.adventours.calendar.letter.domain.LetterRepository;
import com.adventours.calendar.user.domain.User;
import com.adventours.calendar.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;

    public void createLetter(Long userId, String calendarId, SendLetterRequest request) {
        User user = userRepository.getReferenceById(userId);
        Calendar calendar = calendarRepository.getReferenceById(UUID.fromString(calendarId));
        Letter letter = new Letter(calendar, user, request.content());
        letterRepository.save(letter);
    }

    public List<LetterListResponse> getLetter(Long userId, String calendarId) {
        User user = userRepository.getReferenceById(userId);
        Calendar calendar = calendarRepository.findById(UUID.fromString(calendarId)).orElseThrow(NotFoundCalendarException::new);
        if (!calendar.getUser().getId().equals(user.getId())) {
            throw new NotOwnerException();
        }
        List<Letter> letters = letterRepository.findAllByCalendar(calendar);
        return LetterListResponse.of(letters);
    }
}
