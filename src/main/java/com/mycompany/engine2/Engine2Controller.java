package com.mycompany.engine2;

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
public class Engine2Controller {

	private RestTemplate restTemplate;
	@Value("${engine1.url:http://localhost:8081/}")
	private String engine1Url;
	
	@Autowired
	public Engine2Controller(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@GetMapping("/hello/{name}")
	public ResponseEntity<String> hello(@PathVariable String name) {
		String s = restTemplate.getForObject(engine1Url + "engine1/api/hello/" + name, String.class);
		return new ResponseEntity<>(s, HttpStatus.OK) ;
	}

	@GetMapping("/modela/{name}")
	public ResponseEntity<ModelA> getModelA(@PathVariable String name) throws Exception {
		ResponseEntity<ModelA> entity = restTemplate.getForEntity(engine1Url + "engine1/api/modela/" + name, ModelA.class);
		return entity;
	}

	@GetMapping("/modelb/{name}")
	public ResponseEntity<ModelB> getModelB(@PathVariable String name) throws Exception {
		ResponseEntity<ModelB> entity = restTemplate.getForEntity(engine1Url + "engine1/api/modelb/" + name, ModelB.class);
		return entity;
	}
}
