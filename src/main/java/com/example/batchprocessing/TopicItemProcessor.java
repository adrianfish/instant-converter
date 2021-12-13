package com.example.batchprocessing;

import org.springframework.batch.item.ItemProcessor;

public class TopicItemProcessor implements ItemProcessor<Topic, Topic> {

    @Override
    public Topic process(final Topic topic) throws Exception {
        return topic;
    }
}
