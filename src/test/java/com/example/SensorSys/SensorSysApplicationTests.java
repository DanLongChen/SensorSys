package com.example.SensorSys;

import com.sensor.entity.Chromosome;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SensorSysApplicationTests {
	@Autowired
	public ApplicationContext context;
	@Autowired
	public Chromosome chromosome;
	@Test
	public void contextLoads() {
		System.out.println(context.containsBean("result"));
		System.out.println(chromosome.getRatio());
	}

}
