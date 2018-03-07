package com.docu.sign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.docu.sign.controller",
	"com.docu.sign.common",
	"com.docu.sign.utils",
	"com.drop.box.controller", "com.drop.box"})
public class DocuSignExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocuSignExampleApplication.class, args);
	}
}
