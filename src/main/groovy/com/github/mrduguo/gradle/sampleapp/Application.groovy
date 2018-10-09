package com.github.mrduguo.gradle.sampleapp

import com.github.mrduguo.gradle.samplelib.World
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable

@SpringBootApplication
@RestController
@EnableZuulProxy
public class Application {

	@RequestMapping('/')
	def index() {
		new World().sayHello()
	}

	@RequestMapping('/api/json')
	def json() {
		[
				foo:'FOO',
				bar:'BAR',
		]
	}

	@RequestMapping("/reverse/{stringToReverse}")
	def reverse(@PathVariable String stringToReverse) {
		return new StringBuilder(stringToReverse).reverse().toString()
	}



	public static void main(String[] args) {
		System.properties['info.app.start.time']=new Date().format('yyyy-MM-dd HH:mm:ss z')
		SpringApplication.run(Application.class, args)
	}

}
