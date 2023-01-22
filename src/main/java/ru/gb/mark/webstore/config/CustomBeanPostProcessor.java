package ru.gb.mark.webstore.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Log4j2
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {


    private final Environment env;

    public CustomBeanPostProcessor(Environment env) {
        this.env = env;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass().getSimpleName().equals(MailProperties.class.getSimpleName())) {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                try {
                    if (f.getName().equals("username")) {
                        f.set(bean, env.getProperty("mailUser"));
                    }

                    if (f.getName().equals("password")) {
                        f.set(bean, env.getProperty("mailPas"));
                    }

                    f.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return bean;
    }

}
