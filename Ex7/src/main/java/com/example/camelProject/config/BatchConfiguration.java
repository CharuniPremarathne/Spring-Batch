package com.example.camelProject.config;


import com.example.camelProject.model.StudentNew;
import com.example.camelProject.partition.ColumnRangePartitioner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//
//    private StudentRepo studentRepo;
//
//    private EntityManagerFactory entityManagerFactory;
//
//    @Bean
//    public ColumnRangePartitioner columnRangePartitioner(){
//        return new ColumnRangePartitioner();
//    }
//
//    @Bean
//    public PartitionHandler partitionHandler(){
//        TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
//        taskExecutorPartitionHandler.setGridSize(2);
//        taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
//        taskExecutorPartitionHandler.setStep(slaveStep());
//        return taskExecutorPartitionHandler;
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor(){
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setMaxPoolSize(4);
//        taskExecutor.setCorePoolSize(4);
//        taskExecutor.setQueueCapacity(4);
//        return taskExecutor;
//    }
//
//    @Bean
//    public Step slaveStep(){
//        return stepBuilderFactory.get("slaveStep").<Student, Student> chunk(5)
//                .reader(reader(entityManagerFactory))
//                .processor(processor())
//                .writer(writer(studentRepo))
//                .build();
//    }
//
//    @Bean
//    public Step masterStep(){
//        return stepBuilderFactory.get("masterStep")
//                .partitioner(slaveStep().getName(), columnRangePartitioner())
//                .partitionHandler(partitionHandler())
//                .build();
//    }
//
//    @Bean
//    public JpaPagingItemReader<Student> reader(EntityManagerFactory entityManagerFactory){
//        JpaPagingItemReader<Student> itemReader = new JpaPagingItemReader<>();
//        itemReader.setEntityManagerFactory(entityManagerFactory);
//        itemReader.setQueryString("select s from Student s");
//        itemReader.setPageSize(10);
//        return itemReader;
//    }
//
//    @Bean
//    public ProjectItemProcessor processor(){
//        return new ProjectItemProcessor();
//    }
//
//    @Bean
//    public ItemWriter<Student> writer(StudentRepo studentRepo){
//        return items->{
//            for (Student item: items){
//                studentRepo.save(item);
//            }
//        };
//    }
//
//    @Bean
//    public Step step1(){
//        return stepBuilderFactory.get("step1").<Student, Student> chunk(1)
//                .reader(reader(entityManagerFactory))
//                .processor(processor())
//                .writer(writer(studentRepo))
//                .faultTolerant()
//                .skipLimit(5)
//                .skip(NullPointerException.class)
//                .allowStartIfComplete(true)
//                .build();
//    }
//
//
//    @Bean
//    public Job job(){
//        return jobBuilderFactory.get("job1")
//                .start(step1())
//                .build();
//    }

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CustomItemWriter customItemWriter;

    @Autowired
    private SlaveItemReader slaveItemReader;

    @Autowired
    private CustomStepExecutionAgregator customStepExecutionAgregator;

    @Bean
    public CustomItemProcessor processor(){
        return new CustomItemProcessor();
    }

    @Bean
    public Partitioner columnRangePartitioner(){
        return new ColumnRangePartitioner();
    }

    @Bean
    public PartitionHandler partitionHandler() {
        TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
        handler.setStep(slaveStep());
        handler.setTaskExecutor(taskExecutor());
        handler.setGridSize(10);
        return handler;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(10);
        return taskExecutor;
    }

    @Bean
    public Step slaveStep() {
        return stepBuilderFactory.get("slaveStep").<StudentNew, StudentNew>chunk(10)
                .reader(slaveItemReader)
                .processor(processor())
                .writer(customItemWriter)
                .faultTolerant()
                .skipPolicy((throwable, skipCount) -> {
                    logger.error("Skipping due to error: {}", throwable.getMessage());
                    return true;
                })
                .build();
    }

    @Bean
    public Step masterStep() {
        return stepBuilderFactory.get("masterStep")
                .partitioner(slaveStep().getName(), columnRangePartitioner())
                .partitionHandler(partitionHandler())
                .build();
    }

    @Bean
    public Job partitionedJob(){
        return jobBuilderFactory.get("partitionedJob")
                .start(masterStep())
                .next(aggregatorStep())
                .build();
    }

    @Bean
    public Step aggregatorStep() {
        return stepBuilderFactory.get("aggregatorStep")
                .tasklet((stepContribution, chunkContext) -> {
                    StepExecution stepExecution = stepContribution.getStepExecution();
                    customStepExecutionAgregator.aggregate(
                            stepExecution,
                            stepExecution.getJobExecution().getStepExecutions()
                    );
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
