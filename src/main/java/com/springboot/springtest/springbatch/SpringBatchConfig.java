package com.springboot.springtest.springbatch;

import com.springboot.springtest.bean.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.Validator;
import org.springframework.batch.support.DatabaseType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {


        /**
         * ItemReader定义,用来读取数据
         * 1，使用FlatFileItemReader读取文件
         * 2，使用FlatFileItemReader的setResource方法设置csv文件的路径
         * 3，对此对cvs文件的数据和领域模型类做对应映射
         * @return
         * @throws Exception
         */
        @Bean
        public ItemReader<Person> reader()throws Exception {
            FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
            reader.setResource(new ClassPathResource("person.csv"));
            reader.setLineMapper(new DefaultLineMapper<Person>(){
                {
                    setLineTokenizer(new DelimitedLineTokenizer(){
                        {
                            setNames(new String[]{"name","age","school","address"});
                        }
                    });
                    setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>(){{
                        setTargetType(Person.class);
                    }});
                }
            });
            return reader;
        }

        /**
         * ItemProcessor定义，用来处理数据
         * @return
         */
        @Bean
        public ItemProcessor<Person,Person> processor(){
            //使用我们自定义的ItemProcessor的实现CsvItemProcessor
            CsvItemProcessor processor = new CsvItemProcessor();
            //为processor指定校验器为CsvBeanValidator()
            processor.setValidator(csvBeanValidator());
            return processor;
        }

        /**
         * ItemWriter定义，用来输出数据
         * spring能让容器中已有的Bean以参数的形式注入，Spring Boot已经为我们定义了dataSource
         * @param dataSource
         * @return
         */
        @Bean
        public ItemWriter<Person> writer(@Qualifier("dataSource") DataSource dataSource){
            JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
            //我们使用JDBC批处理的JdbcBatchItemWriter来写数据到数据库
            writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
            String sql = "insert into person "+" (id,name,age,school,address) "
                    +" values(:id,:name,:age,:school,:address)";
            //在此设置要执行批处理的SQL语句
            writer.setSql(sql);
            writer.setDataSource(dataSource);
            return writer;
        }

        /**
         * JobRepository，用来注册Job的容器
         * jobRepositor的定义需要dataSource和transactionManager，Spring Boot已为我们自动配置了
         * 这两个类，Spring可通过方法注入已有的Bean
         * @param dataSource
         * @param transactionManager
         * @return
         * @throws Exception
         */
        @Bean
        public JobRepository jobRepository(@Qualifier("dataSource") DataSource dataSource, PlatformTransactionManager transactionManager)throws Exception{

            JobRepositoryFactoryBean jobRepositoryFactoryBean =
                    new JobRepositoryFactoryBean();
            jobRepositoryFactoryBean.setDataSource(dataSource);
            jobRepositoryFactoryBean.setTransactionManager(transactionManager);
            jobRepositoryFactoryBean.setDatabaseType(DatabaseType.MYSQL.name());
            return jobRepositoryFactoryBean.getObject();
        }

        /**
         * JobLauncher定义，用来启动Job的接口
         * @param dataSource
         * @param transactionManager
         * @return
         * @throws Exception
         */
        @Bean
        public SimpleJobLauncher jobLauncher(@Qualifier("dataSource") DataSource dataSource, PlatformTransactionManager transactionManager)throws Exception{
            SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
            jobLauncher.setJobRepository(jobRepository(dataSource, transactionManager));
            return jobLauncher;
        }

        /**
         * Job定义，我们要实际执行的任务，包含一个或多个Step
         * @param jobBuilderFactory
         * @param s1
         * @return
         */
        @Bean
        public Job importJob(JobBuilderFactory jobBuilderFactory, Step s1){
            return jobBuilderFactory.get("importJob")
                    .incrementer(new RunIdIncrementer())
                    .flow(s1)//为Job指定Step
                    .end()
                    .listener(csvJobListener())//绑定监听器csvJobListener
                    .build();
        }

        /**
         *step步骤，包含ItemReader，ItemProcessor和ItemWriter
         * @param stepBuilderFactory
         * @param reader
         * @param writer
         * @param processor
         * @return
         */
        @Bean
        public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader, ItemWriter<Person> writer,
                          ItemProcessor<Person,Person> processor){
            return stepBuilderFactory
                    .get("step1")
                    .<Person,Person>chunk(5000)//批处理每次提交2000条数据
                    .reader(reader)//给step绑定reader
                    .processor(processor)//给step绑定processor
                    .writer(writer)//给step绑定writer
                    .build();
        }

        @Bean
        public CsvJobListener csvJobListener(){
            return new CsvJobListener();
        }

        @Bean
        public Validator<Person> csvBeanValidator(){
            return new CsvBeanValidator<Person>();
        }
}
