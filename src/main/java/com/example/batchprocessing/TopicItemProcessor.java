package com.example.batchprocessing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class TopicItemProcessor implements ItemProcessor<Topic, Topic> {

    @Override
    public Topic process(final Topic topic) throws Exception {
        return topic;
        //return topic.toBuilder().created1(topic.getCreated()).modified1(topic.getModified()).lastActivity1(topic.getLastActivity()).build();
    }
}
