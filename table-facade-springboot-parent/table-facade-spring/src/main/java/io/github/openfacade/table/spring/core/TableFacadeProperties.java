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

package io.github.openfacade.table.spring.core;

import io.github.openfacade.table.api.DriverType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Getter
@Setter
@ConfigurationProperties("spring.table.facade")
public class TableFacadeProperties {
    private DriverType driverType;

    @NestedConfigurationProperty
    private OpenGauss openGauss;

    @NestedConfigurationProperty
    private Postgre postgre;

    @Getter
    @Setter
    public static class OpenGauss {
        private String schema;
    }

    @Getter
    @Setter
    public static class Postgre {
        private String schema;
    }
}
