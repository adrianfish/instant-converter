package com.example.batchprocessing;

import org.springframework.batch.item.ItemProcessor;

public class CommentItemProcessor implements ItemProcessor<Comment, Comment> {

    @Override
    public Comment process(final Comment comment) throws Exception {
        return comment;
    }
}
