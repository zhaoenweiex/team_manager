package com.zw.se2.self;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zw.se2.self.mappper")

public class TeamManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamManagerApplication.class, args);
	}
}
