package com.example.batchprocessing;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.io.ObjectInputStream;
import java.time.Instant;

public class TopicRowMapper implements RowMapper<Topic> {

    public Topic mapRow(ResultSet rs, int rowNum) {

        try {
            Topic topic = new Topic();
            topic.setId(rs.getString("TOPIC_ID"));
            System.out.println(rs.getString("TITLE"));

            Blob blob = rs.getBlob("CREATED");
            Instant created = (Instant) (new ObjectInputStream(blob.getBinaryStream())).readObject();
            System.out.println(created);
            topic.setCreated1(Timestamp.from(created));

            blob = rs.getBlob("MODIFIED");
            Instant modified = (Instant) (new ObjectInputStream(blob.getBinaryStream())).readObject();
            System.out.println(modified);
            topic.setModified1(Timestamp.from(modified));

            blob = rs.getBlob("LAST_ACTIVITY");
            if (blob != null) {
                Instant lastActivity = (Instant) (new ObjectInputStream(blob.getBinaryStream())).readObject();
                System.out.println(lastActivity);
                topic.setLastActivity1(Timestamp.from(lastActivity));
            }

            return topic;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
