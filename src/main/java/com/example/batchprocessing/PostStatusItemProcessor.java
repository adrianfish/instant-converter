package com.example.batchprocessing;

import org.springframework.batch.item.ItemProcessor;

public class PostStatusItemProcessor implements ItemProcessor<PostStatus, PostStatus> {

    @Override
    public PostStatus process(final PostStatus status) throws Exception {
        return status;
    }
}
