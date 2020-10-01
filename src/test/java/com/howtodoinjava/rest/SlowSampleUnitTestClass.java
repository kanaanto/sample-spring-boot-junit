package com.howtodoinjava.rest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SlowSampleUnitTestClass {
	private static final Logger logger = LoggerFactory.getLogger(SlowSampleUnitTestClass.class);
    
	@BeforeClass
    public static void init() {
		logger.info("------ RUNNING SLOW UNIT TEST CLASS ------");
    }
	
	@Test
	public void testSampleService() {
		Assert.assertEquals("yes", "yes");
	}
}
