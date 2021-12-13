package com.example.batchprocessing;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public DataSource dataSource(){

       return DataSourceBuilder.create()
                //.url(env.getProperty("db.url"))
                .url("jdbc:mysql://127.0.0.1:3306/sakai_20x?useUnicode=true&characterEncoding=UTF-8")
                .driverClassName("com.mysql.jdbc.Driver")
                .username("sakaiuser")
                .password("c00k1t")
                .build();
    }

    @Bean
    public ItemReader<Topic> reader(DataSource dataSource) {

        return new JdbcCursorItemReaderBuilder<Topic>()
            .name("topicItemReader")
            .dataSource(dataSource)
            .sql("SELECT * from CONV_TOPICS")
            //.rowMapper(new BeanPropertyRowMapper<>(Topic.class))
            .rowMapper(new TopicRowMapper())
            .build();
    }

    @Bean
    public ItemProcessor<Topic, Topic> processor() {
        return new TopicItemProcessor();
    }

    @Bean
    public ItemWriter<Topic> writer(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<Topic>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Topic>())
            .sql("UPDATE CONV_TOPICS SET CREATED1 = :created1, MODIFIED1 = :modified1, LAST_ACTIVITY1 = :lastActivity1 WHERE TOPIC_ID = :id")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public Job convertdTopicDatesJob(JobCompletionNotificationListener listener, Step step1) {

        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(ItemReader<Topic> reader, ItemProcessor<Topic, Topic> processor, ItemWriter<Topic> writer) {

        return stepBuilderFactory.get("step1")
            .<Topic, Topic> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }
}
