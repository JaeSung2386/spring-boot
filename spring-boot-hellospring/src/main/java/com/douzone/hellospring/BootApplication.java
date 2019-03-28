package com.douzone.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootConfiguration
//@EnableAutoConfiguration
////@ComponentScan은 BootApplication이 존재하는 패키지부터 아래로 스캔하므로 경로를 지정해주어야함
//@ComponentScan("com.douzone.hellospring.controller")

@SpringBootApplication
public class BootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

}
