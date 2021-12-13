package com.example.batchprocessing;

import org.springframework.batch.item.ItemProcessor;

public class UserStatisticsItemProcessor implements ItemProcessor<UserStatistics, UserStatistics> {

    @Override
    public UserStatistics process(final UserStatistics stats) throws Exception {
        return stats;
    }
}
