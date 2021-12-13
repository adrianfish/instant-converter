package com.example.batchprocessing;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.io.ObjectInputStream;
import java.time.Instant;

public class PostStatusRowMapper implements RowMapper<PostStatus> {

    public PostStatus mapRow(ResultSet rs, int rowNum) {

        try {
            PostStatus status = new PostStatus();
            status.setId(rs.getString("ID"));

            Blob blob = rs.getBlob("VIEWED_DATE");
            if (blob != null) {
                Instant viewedDate = (Instant) (new ObjectInputStream(blob.getBinaryStream())).readObject();
                status.setViewedDate1(Timestamp.from(viewedDate));
            }

            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
