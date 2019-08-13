package com.yukong.spring.processor;

import com.yukong.spring.Student;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author yukong
 * @date 2019-06-27 14:36
 */
@Slf4j
@Component
public class MyPostBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof Student ) {
             boolean falg = bean.getClass().isAnnotationPresent(Data.class);
            log.info(" MyPostBeanPostProcessor: postProcessBeforeInitialization  {} {}", beanName, falg);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof Student) {
            log.info(" MyPostBeanPostProcessor: postProcessAfterInitialization  {}", beanName);
        }
        return bean;
    }
}
