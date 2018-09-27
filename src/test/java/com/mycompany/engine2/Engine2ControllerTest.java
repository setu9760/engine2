package com.mycompany.engine2;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.mycompany.app.common.model.ModelA;
import com.mycompany.app.common.model.ModelB;

@RunWith(SpringRunner.class)
@WebMvcTest(Engine2Controller.class)
public class Engine2ControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	RestTemplateBuilder builder;
	@MockBean
	RestTemplate template;
	
	@Before
	public void setUp() {
		when(builder.build()).thenReturn(template);
	}

    @Test
    public void testHello() throws Exception {
    	when(template.getForObject(anyString(), eq(String.class))).thenReturn("{\"msg\" : \"hello abcd\"}");
    	mvc.perform(get("/engine2/api/hello/abcd"))
    		.andExpect(status().isOk())
    		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
    		.andExpect(jsonPath("$.msg", is("hello abcd")))
    		.andDo(print());
    }
    
    @Test
    public void testGetModelA() throws Exception {
    	when(template.getForEntity(anyString(), eq(ModelA.class))).thenReturn(new ResponseEntity<>(new ModelA("abcd", 1), HttpStatus.OK));
    	mvc.perform(get("/engine2/api/modela/abcd"))
	    	.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name", is("abcd")))
			.andExpect(jsonPath("$.number", is(1)))
    		.andDo(print());
    }
    
    @Test
    public void testGetModelB() throws Exception {
    	when(template.getForEntity(anyString(), eq(ModelB.class))).thenReturn(new ResponseEntity<>(new ModelB("abcd", 1), HttpStatus.OK));
    	mvc.perform(get("/engine2/api/modelb/abcd"))
	    	.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.name", is("abcd")))
			.andExpect(jsonPath("$.number", is(1)))
    		.andDo(print());
    }

}
