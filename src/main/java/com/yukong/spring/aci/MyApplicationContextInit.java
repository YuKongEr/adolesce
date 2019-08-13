package com.yukong.spring.aci;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author yukong
 * @date 2019-06-27 14:54
 */
@Component
public class MyApplicationContextInit implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        HashMap<String, Object> map = new HashMap<>();
        map.put("student.name", "yukongGG");
        map.put("student.age", 20);
        MapPropertySource mapPropertySource = new MapPropertySource("mySoureMap", map);
        propertySources.addLast(mapPropertySource);
    }


    public static void main(String[] args) {
        List<Integer> list =  Arrays.asList(1,2,3);
        System.out.println(list.getClass().getName());
        list.add(4);
    }
}
