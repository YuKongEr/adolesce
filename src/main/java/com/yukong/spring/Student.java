package com.yukong.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yukong
 * @date 2019-06-27 14:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "student")
@Slf4j
public class Student implements InitializingBean {

    private String name;

    private Integer age;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("调用 afterPropertiesSet");
    }
}
