package com.adventours.calendar.gift.persistence;

import com.adventours.calendar.gift.domain.GiftPersonalState;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GiftPersonalStateRepositoryCustomImpl implements GiftPersonalStateRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllBulk(List<GiftPersonalState> giftPersonalStateList) {
        jdbcTemplate.batchUpdate("INSERT INTO gift_personal_state (is_opened,is_reacted,gift_id,user_id) " +
                        "values (?,?,?,?)",
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(final PreparedStatement ps, final int i) throws SQLException {
                        ps.setBoolean(1, giftPersonalStateList.get(i).getIsOpened());
                        ps.setBoolean(2, giftPersonalStateList.get(i).getIsReacted());
                        ps.setInt(3, giftPersonalStateList.get(i).getGift().getId());
                        ps.setInt(4, giftPersonalStateList.get(i).getUser().getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return giftPersonalStateList.size();
                    }
                });
    }
}
