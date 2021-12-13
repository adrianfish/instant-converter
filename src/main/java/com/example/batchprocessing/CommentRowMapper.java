package com.example.batchprocessing;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.io.ObjectInputStream;
import java.time.Instant;

public class CommentRowMapper implements RowMapper<Comment> {

    public Comment mapRow(ResultSet rs, int rowNum) {

        try {
            Comment comment = new Comment();
            comment.setId(rs.getString("COMMENT_ID"));

            Blob blob = rs.getBlob("CREATED");
            Instant created = (Instant) (new ObjectInputStream(blob.getBinaryStream())).readObject();
            System.out.println(created);
            comment.setCreated1(Timestamp.from(created));

            blob = rs.getBlob("MODIFIED");
            Instant modified = (Instant) (new ObjectInputStream(blob.getBinaryStream())).readObject();
            System.out.println(modified);
            comment.setModified1(Timestamp.from(modified));

            return comment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
