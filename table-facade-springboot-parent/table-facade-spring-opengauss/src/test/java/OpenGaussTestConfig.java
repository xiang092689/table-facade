/*
 * Copyright 2024 OpenFacade Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.github.openfacade.table.spring.test.common.TestConfig;
import io.github.openfacade.table.test.common.container.OpenGaussContainer;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.testcontainers.junit.jupiter.Container;

@Slf4j
public class OpenGaussTestConfig extends TestConfig {

    public static final int CONNECTION_RETRY_TIMEOUT =  5 * 60 * 1000;

    public static final int CONNECTION_RETRY_INTERVAL = 5 * 1000;

    public static final int MAX_CONNECTION_RETRY =  CONNECTION_RETRY_TIMEOUT / CONNECTION_RETRY_INTERVAL;

    @Container
    OpenGaussContainer openGaussContainer;

    @Bean
    public DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.create(connectionFactory);
    }

    @Bean
    public ConnectionFactory connectionFactory(OpenGaussContainer openGaussContainer, R2dbcProperties r2dbcProperties) throws InterruptedException {
        PostgresqlConnectionFactory pgConnectionFactory = new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
                .host("localhost")
                .port(35432)  // optional, defaults to 5432
                .username(openGaussContainer.getUsername())
                .password(openGaussContainer.getPassword())
                .database(openGaussContainer.getDatabaseName())
                .schema(openGaussContainer.getSchema())
                .build());
        // wait for container available
        int retries = 0;
        boolean connected = false;
        while (!connected) {
            try {
                pgConnectionFactory.create().block();
                connected = true;
            } catch (Exception e) {
                if (retries >= MAX_CONNECTION_RETRY) {
                    throw e;
                }
                retries++;
                log.warn("connect openGauss failed, retry {}/{}", retries, MAX_CONNECTION_RETRY);
                Thread.sleep(6 * 1000);
            }
        }
        log.info("OpenGauss is available now");
        return pgConnectionFactory;
    }

    @Bean
    public OpenGaussContainer openGaussContainer() {
        openGaussContainer = new OpenGaussContainer().withCompatibility("B");
        openGaussContainer.startContainer();
        return openGaussContainer;
    }

    @Bean
    public R2dbcProperties r2dbcProperties(OpenGaussContainer openGaussContainer) {
        String url = String.format("r2dbc:postgresql://localhost:35432/%s?currentSchema=%s",
                openGaussContainer.getDatabaseName(), openGaussContainer.getSchema());
        R2dbcProperties properties = new R2dbcProperties();
        properties.setUrl(url);
        properties.setUsername(openGaussContainer.getUsername());
        properties.setPassword(openGaussContainer.getPassword());
        return properties;
    }

    @PreDestroy
    public void gracefullyExit() {
//        if (openGaussContainer != null) {
//            openGaussContainer.stopContainer();
//        }
    }
}
