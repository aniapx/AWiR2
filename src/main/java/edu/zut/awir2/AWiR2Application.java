package edu.zut.awir2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("edu.zut.awir2.Models")
public class AWiR2Application {

    public static void main(String[] args) {
        SpringApplication.run(AWiR2Application.class, args);
    }

}
