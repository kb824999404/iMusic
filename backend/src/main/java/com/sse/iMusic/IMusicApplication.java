package com.sse.iMusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.sse.iMusic.Mappers")
public class IMusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(IMusicApplication.class, args);
	}




}
