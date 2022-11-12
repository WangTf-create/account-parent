package com.citi.mvnbook.account.persist.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * @author wangtongfa
 * @date 2022/11/6 19:07
 */
public class PersistenceEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final Properties properties = new Properties();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String[] profiles = {
                "account-service.properties"
        };
        for (String profile : profiles) {
            ClassPathResource resource = new ClassPathResource(profile);

            environment.getPropertySources().addLast(loadPropertyResource(resource));

        }

    }

    private PropertySource<?> loadPropertyResource(ClassPathResource resource) {
        if (!resource.exists()) {
            throw new IllegalArgumentException("Resource file " + resource.getFilename() + " not exists");
        }
        try {
            properties.load(resource.getInputStream());
            return new PropertiesPropertySource(Objects.requireNonNull(resource.getFilename()), properties);
        } catch (IOException e) {
            throw new IllegalStateException("Load resource failed", e);
        }
    }


}
