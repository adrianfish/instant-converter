package com.example.batchprocessing;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.io.ObjectInputStream;
import java.time.Instant;

public class PostRowMapper implements RowMapper<Post> {

    public Post mapRow(ResultSet rs, int rowNum) {

        try {
            Post post = new Post();
            post.setId(rs.getString("POST_ID"));

            Blob blob = rs.getBlob("CREATED");
            Instant created = (Instant) (new ObjectInputStream(blob.getBinaryStream())).readObject();
            post.setCreated1(Timestamp.from(created));

            blob = rs.getBlob("MODIFIED");
            Instant modified = (Instant) (new ObjectInputStream(blob.getBinaryStream())).readObject();
            post.setModified1(Timestamp.from(modified));

            return post;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
