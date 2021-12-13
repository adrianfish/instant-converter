package com.example.batchprocessing;

import org.springframework.batch.item.ItemProcessor;

public class PostItemProcessor implements ItemProcessor<Post, Post> {

    @Override
    public Post process(final Post post) throws Exception {
        return post;
    }
}
