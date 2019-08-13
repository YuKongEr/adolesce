package com.yukong;

import com.yukong.spring.Student;
import com.yukong.spring.aci.MyApplicationContextInit;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yukong
 * @date 2019-06-27 14:19
 */
@SpringBootApplication
@Slf4j
public class Application implements InitializingBean {

    @Autowired
    private Student student;

    public static void main(String[] args) {
        SpringApplication springApplication  = new SpringApplication(Application.class);
        springApplication.addInitializers(new MyApplicationContextInit());
        springApplication.run(args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(student.toString());
    }
}
