package com.adventours.calendar.gift.persistence;

import com.adventours.calendar.gift.domain.Gift;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GiftRepositoryCustomImpl implements GiftRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllBulk(final List<Gift> gifts) {
        jdbcTemplate.batchUpdate("INSERT INTO gift(calendar_id,content_url,gift_type,open_at,text_body,title,created_at,updated_at) " +
                "values (?,?,?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {

            @Override
            public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                ps.setObject(1, gifts.get(i).getCalendar().getId());
                ps.setString(2, gifts.get(i).getContentUrl());
                ps.setString(3, gifts.get(i).getGiftType().toString());
                ps.setString(4, gifts.get(i).getOpenAt().toString());
                ps.setString(5, gifts.get(i).getTextBody());
                ps.setString(6, gifts.get(i).getTitle());
                ps.setObject(7, LocalDateTime.now());
                ps.setObject(8, LocalDateTime.now());
            }

            @Override
            public int getBatchSize() {
                return gifts.size();
            }
        });
    }
}
