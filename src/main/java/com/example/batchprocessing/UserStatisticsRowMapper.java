package com.example.batchprocessing;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.io.ObjectInputStream;
import java.time.Instant;

public class UserStatisticsRowMapper implements RowMapper<UserStatistics> {

    public UserStatistics mapRow(ResultSet rs, int rowNum) {

        try {
            UserStatistics stats = new UserStatistics();
            stats.setId(rs.getString("ID"));

            Blob blob = rs.getBlob("LAST_POST_DATE");
            Instant lastPostDate = (Instant) (new ObjectInputStream(blob.getBinaryStream())).readObject();
            stats.setLastPostDate1(Timestamp.from(lastPostDate));

            return stats;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
