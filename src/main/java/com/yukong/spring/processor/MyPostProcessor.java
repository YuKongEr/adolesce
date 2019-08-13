package com.yukong.spring.processor;

import com.yukong.spring.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author yukong
 * @date 2019-06-27 14:22
 */
@Component
public class MyPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
      /*  Student student = new Student();
        student.setAge(18).setName("yukong");
        configurableListableBeanFactory.registerSingleton("student", student);*/

    }
}
