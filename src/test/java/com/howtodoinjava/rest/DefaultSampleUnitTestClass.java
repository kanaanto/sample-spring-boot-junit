package com.howtodoinjava.rest;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.howtodoinjava.rest.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class DefaultSampleUnitTestClass 
{   
    @LocalServerPort
    int randomServerPort;
    
	private static final Logger logger = LoggerFactory.getLogger(DefaultSampleUnitTestClass.class);
    
	@BeforeClass
    public static void init() {
		logger.info("------ RUNNING DEFAULT UNIT TEST CLASS ------");
    }

	
    //@Test
    public void testGetEmployeeListSuccess() throws URISyntaxException 
    {
        RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:"+randomServerPort+"/employees/";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        
        //Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("employeeList"));
    }

    @Test
    public void testGetEmployeeListSuccessWithHeaders() throws URISyntaxException 
    {
        RestTemplate restTemplate = new RestTemplate();
        
        final String baseUrl = "http://localhost:"+randomServerPort+"/employees/";
        URI uri = new URI(baseUrl);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-LOCATION", "USA");

        HttpEntity<Employee> requestEntity = new HttpEntity<>(null, headers);

        try 
        {
            restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
            Assert.fail();
        }
        catch(HttpClientErrorException ex) 
        {
            //Verify bad request and missing header
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
        }
    }
    
    @Test
    public void printMultiArr() {
    	String str = "IN ORDER TO UNDERSTAND RECURSION ONE MUST FIRST UNDERSTAND RECURSION";
    	String encryptedStr = processStr(str, true);
    	String decryptedStr = processStr(encryptedStr, false);
    	
    	Assert.assertEquals("IONOTDEN*DN*EC*U**FRUONROISRRDENRTSDECESAIERU*TNORSRM*DN*TSUU*TAISNR", 
    			encryptedStr.replace("?", ""));
    	Assert.assertEquals(str, decryptedStr.replace("?", ""));
    	System.out.println("ENCRYPTED=" +encryptedStr.replace("?", ""));
    	System.out.println("DECRYPTED=" +decryptedStr.replace("?", ""));
    }
    
    public String processStr(String str, boolean doEncrypt) { 
    	final int x = 10; //constant
    	if(str.length() <= x) {
    		return str;
    	}
    	final String string = doEncrypt ? str.replace(" ", "*") : str.replace("*", " ");
    	final int y = (str.length() + x - 1) / x;	
    	int col = doEncrypt ? y: x; // 
    	int row = doEncrypt ? x: y;
    	
    	String currStr = "";
    	List<String> arr = IntStream.range(0, col)
    			.mapToObj(i -> string.substring(i * row, Math.min((i+1) * row, string.length())))
                .collect(Collectors.toList());
    	//System.out.println(doEncrypt? "Encrypting..." : "Decrypting...");
    	for(String s : arr) {
    		System.out.println(s);
    	}
    	for(int i = 0; i < row; i++) {
    		for(int j = 0; j < col; j++) {
    			if(arr.get(j).toCharArray().length > i) {
    				currStr += arr.get(j).toCharArray()[i];
    			} else {
    				currStr += "?";
    			}
    		}
    	}
    	return currStr;
	}
    
 
   /*
    @Test
    public void testAddEmployeeSuccess() throws URISyntaxException 
    {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:"+randomServerPort+"/employees/";
        URI uri = new URI(baseUrl);
        Employee employee = new Employee(null, "Adam", "Gilly", "test@email.com");
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-COM-PERSIST", "true");      

        HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
        
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        
        //Verify request succeed
        Assert.assertEquals(201, result.getStatusCodeValue());
    }
    
    @Test
    public void testAddEmployeeMissingHeader() throws URISyntaxException 
    {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:"+randomServerPort+"/employees/";
        URI uri = new URI(baseUrl);
        Employee employee = new Employee(null, "Adam", "Gilly", "test@email.com");
        
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
        
        try 
        {
            restTemplate.postForEntity(uri, request, String.class);
            Assert.fail();
        }
        catch(HttpClientErrorException ex) 
        {
            //Verify bad request and missing header
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
        }
    }*/
}
