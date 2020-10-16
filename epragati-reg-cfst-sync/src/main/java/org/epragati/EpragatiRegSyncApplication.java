package org.epragati;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;


@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@EnableMongoRepositories(basePackages= {"org.epragati.*"})
@EnableScheduling
@ComponentScan(basePackages={"org.epragati.*"})
public class EpragatiRegSyncApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(EpragatiRegSyncApplication.class, args);
	}
	@Bean(name = "restTemplate")
	public RestTemplate restTemplate() {
		return new RestTemplate(clientHttpRequestFactory());
	}
	
	@Bean
	public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(60000);
		clientHttpRequestFactory.setReadTimeout(60000);
		clientHttpRequestFactory.setHttpClient(httpClient());
		return clientHttpRequestFactory;
	}
	
	@Bean(name = "pooledClient")
	public CloseableHttpClient httpClient() {
		return HttpClientBuilder.create().build();
	}
}
