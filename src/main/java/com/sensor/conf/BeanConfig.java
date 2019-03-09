package com.sensor.conf;

import com.sensor.entity.Chromosome;
import com.sensor.entity.Gene;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by DanLongChen on 2018/11/23
 **/
@Configuration
public class BeanConfig {
    @Bean
    public Chromosome getChromosome(){
        return new Chromosome(0,0.09);
    }
    @Bean
    public Gene getGene(){
        return new Gene(0,0,0);
    }

}
