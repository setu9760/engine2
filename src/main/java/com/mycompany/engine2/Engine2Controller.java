package com.mycompany.engine2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mycompany.app.common.model.ModelA;
import com.mycompany.app.common.model.ModelB;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class Engine2Controller implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(Engine2Controller.class);
	
	private RestTemplate restTemplate;
	
	@Value("${engine1.protocol:http}")
	private String engine1Protocol;
	@Value("${engine1.host:localhost}")
	private String engine1Host;
	@Value("${engine1.port:8081}")
	private int engine1Port;
	
	private String engine1Url;
	
	@Autowired
	public Engine2Controller(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		engine1Url = String.format("%s://%s:%s/engine1/api/", engine1Protocol, engine1Host, engine1Port);
		LOGGER.info("Using '{}' for making rest requests to engine1", engine1Url);
	}
	
	@GetMapping("/hello/{name}")
	public ResponseEntity<String> hello(@PathVariable String name) {
		String s = restTemplate.getForObject(engine1Url + "hello/" + name, String.class);
		return new ResponseEntity<>(s, HttpStatus.OK) ;
	}

	@GetMapping("/modela/{name}")
	public ResponseEntity<ModelA> getModelA(@PathVariable String name) throws Exception {
		ResponseEntity<ModelA> entity = restTemplate.getForEntity(engine1Url + "modela/" + name, ModelA.class);
		return entity;
	}

	@GetMapping("/modelb/{name}")
	public ResponseEntity<ModelB> getModelB(@PathVariable String name) throws Exception {
		ResponseEntity<ModelB> entity = restTemplate.getForEntity(engine1Url + "modelb/" + name, ModelB.class);
		return entity;
	}
}
