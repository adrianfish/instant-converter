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
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;

import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public DataSource dataSource(@Value("${db.url}") String dbUrl,
                                    @Value("${db.user}") String dbUser,
                                    @Value("${db.pw}") String dbPassword) {

        return DataSourceBuilder.create()
            .url(dbUrl)
            .driverClassName("com.mysql.jdbc.Driver")
            .username(dbUser)
            .password(dbPassword)
            .build();
    }

    @Bean
    public ItemReader<Topic> topicReader(DataSource dataSource) {

        return new JdbcCursorItemReaderBuilder<Topic>()
            .name("topicReader")
            .dataSource(dataSource)
            .sql("SELECT * from CONV_TOPICS")
            .rowMapper(new TopicRowMapper())
            .build();
    }

    @Bean
    public ItemReader<Post> postReader(DataSource dataSource) {

        return new JdbcCursorItemReaderBuilder<Post>()
            .name("postReader")
            .dataSource(dataSource)
            .sql("SELECT * from CONV_POSTS")
            .rowMapper(new PostRowMapper())
            .build();
    }

    @Bean
    public ItemReader<PostStatus> postStatusReader(DataSource dataSource) {

        return new JdbcCursorItemReaderBuilder<PostStatus>()
            .name("postReader")
            .dataSource(dataSource)
            .sql("SELECT * from CONV_POST_STATUS")
            .rowMapper(new PostStatusRowMapper())
            .build();
    }

    @Bean
    public ItemReader<Comment> commentReader(DataSource dataSource) {

        return new JdbcCursorItemReaderBuilder<Comment>()
            .name("commentReader")
            .dataSource(dataSource)
            .sql("SELECT * from CONV_COMMENTS")
            .rowMapper(new CommentRowMapper())
            .build();
    }

    @Bean
    public ItemReader<UserStatistics> statsReader(DataSource dataSource) {

        return new JdbcCursorItemReaderBuilder<UserStatistics>()
            .name("statsReader")
            .dataSource(dataSource)
            .sql("SELECT * from CONV_USER_STATISTICS")
            .rowMapper(new UserStatisticsRowMapper())
            .build();
    }


    @Bean
    public ItemProcessor<Topic, Topic> topicProcessor() {
        return new TopicItemProcessor();
    }

    @Bean
    public ItemProcessor<Post, Post> postProcessor() {
        return new PostItemProcessor();
    }

    @Bean
    public ItemProcessor<PostStatus, PostStatus> postStatusProcessor() {
        return new PostStatusItemProcessor();
    }

    @Bean
    public ItemProcessor<Comment, Comment> commentProcessor() {
        return new CommentItemProcessor();
    }

    @Bean
    public ItemProcessor<UserStatistics, UserStatistics> statsProcessor() {
        return new UserStatisticsItemProcessor();
    }

    @Bean
    public ItemWriter<Topic> topicWriter(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<Topic>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Topic>())
            .sql("UPDATE CONV_TOPICS SET CREATED1 = :created1, MODIFIED1 = :modified1, LAST_ACTIVITY1 = :lastActivity1 WHERE TOPIC_ID = :id")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public ItemWriter<Post> postWriter(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<Post>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Post>())
            .sql("UPDATE CONV_POSTS SET CREATED1 = :created1, MODIFIED1 = :modified1 WHERE POST_ID = :id")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public ItemWriter<PostStatus> postStatusWriter(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<PostStatus>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<PostStatus>())
            .sql("UPDATE CONV_POST_STATUS SET VIEWED_DATE1 = :viewedDate1 WHERE ID = :id")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public ItemWriter<Comment> commentWriter(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<Comment>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Comment>())
            .sql("UPDATE CONV_COMMENTS SET CREATED1 = :created1, MODIFIED1 = :modified1 WHERE COMMENT_ID = :id")
            .dataSource(dataSource)
            .build();
    }

    @Bean
    public ItemWriter<UserStatistics> statsWriter(DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<UserStatistics>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<UserStatistics>())
            .sql("UPDATE CONV_USER_STATISTICS SET LAST_POST_DATE = :lastPostDate1 WHERE ID = :id")
            .dataSource(dataSource)
            .build();
    }


    @Bean
    public Job convertTopicDatesJob(JobCompletionNotificationListener listener, Step step1) {

        return jobBuilderFactory.get("convertTopicDatesJob")
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

    @Bean
    public Job convertPostDatesJob(JobCompletionNotificationListener listener, Step step2) {

        return jobBuilderFactory.get("convertPostDatesJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step2)
            .end()
            .build();
    }

    @Bean
    public Step step2(ItemReader<Post> reader, ItemProcessor<Post, Post> processor, ItemWriter<Post> writer) {

        return stepBuilderFactory.get("step2")
            .<Post, Post> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public Job convertCommentDatesJob(JobCompletionNotificationListener listener, Step step3) {

        return jobBuilderFactory.get("convertCommentDatesJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step3)
            .end()
            .build();
    }

    @Bean
    public Step step3(ItemReader<Comment> reader, ItemProcessor<Comment, Comment> processor, ItemWriter<Comment> writer) {

        return stepBuilderFactory.get("step3")
            .<Comment, Comment> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public Job convertUserStatisticsDatesJob(JobCompletionNotificationListener listener, Step step4) {

        return jobBuilderFactory.get("convertUserStatisticsDatesJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step4)
            .end()
            .build();
    }

    @Bean
    public Step step4(ItemReader<UserStatistics> reader, ItemProcessor<UserStatistics, UserStatistics> processor, ItemWriter<UserStatistics> writer) {

        return stepBuilderFactory.get("step4")
            .<UserStatistics, UserStatistics> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    public Job convertPostStatusJob(JobCompletionNotificationListener listener, Step step5) {

        return jobBuilderFactory.get("convertPostStatusJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step5)
            .end()
            .build();
    }

    @Bean
    public Step step5(ItemReader<PostStatus> reader, ItemProcessor<PostStatus, PostStatus> processor, ItemWriter<PostStatus> writer) {

        return stepBuilderFactory.get("step5")
            .<PostStatus, PostStatus> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }
}
