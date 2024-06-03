package com.example.camelProject;

import com.example.camelProject.components.JobRunner;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelProjectApplication implements CommandLineRunner  {

//	@Autowired
//	private JobLauncher jobLauncher;
//
//	@Autowired
//	private Job job;

	@Autowired
	private JobRunner jobRunner;

	public static void main(String[] args) {
		SpringApplication.run(CamelProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		jobRunner.runJob();
	}

//	@Override
//	public void run(String... args) throws Exception {
//		jobLauncher.run(job, new JobParametersBuilder()
//				.addString("filter", "filter")
//				.toJobParameters());
//	}

}
