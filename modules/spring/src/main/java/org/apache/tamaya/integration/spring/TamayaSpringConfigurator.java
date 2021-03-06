/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tamaya.integration.spring;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Configurator Bean adding {@link TamayaSpringPropertySource} to the current
 * {@link org.springframework.core.env.Environment} and optionally activate Tamaya injection
 * of beans.
 */
public class TamayaSpringConfigurator implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(
                        importingClassMetadata.getAnnotationAttributes(EnableTamayaConfig.class.getName(), false));
        boolean extendOnly = attributes.getBoolean("extendOnly");
        boolean disableTamayaInjection = attributes.getBoolean("disableTamayaInjection");
        List<String> configClasses = new ArrayList<>();
        if (extendOnly) {
            configClasses.add(ExtendingOnlyConfig.class.getName());
        }else {
            configClasses.add(DefaultConfig.class.getName());
        }
        if(!disableTamayaInjection){
            configClasses.add(TamayaInjectionConfig.class.getName());
        }
        return configClasses.toArray(new String[configClasses.size()]);
    }

    /**
     * Configuration active when Tamaya is extending the current Spring configuration only.
     */
    public static class ExtendingOnlyConfig {

        @Bean
        public PropertySourcesPlaceholderConfigurer placeHolderConfigurer(ConfigurableEnvironment env,
                                                                          ObjectFactory<org.apache.tamaya.Configuration> configSupplier) {
            TamayaSpringPropertySource tamayaSpringPropertySource = new TamayaSpringPropertySource(configSupplier);
            env.getPropertySources().addLast(tamayaSpringPropertySource);

            PropertySourcesPlaceholderConfigurer cfgBean = new PropertySourcesPlaceholderConfigurer();
            cfgBean.setEnvironment(env);
            return cfgBean;
        }

    }

    /**
     * Configuration active when Tamaya is overriding the current Spring configuration (default).
     */
    public static class DefaultConfig {

        @Bean
        public PropertySourcesPlaceholderConfigurer placeHolderConfigurer(ConfigurableEnvironment env,
                                                                          ObjectFactory<org.apache.tamaya.Configuration> configSupplier) {

            TamayaSpringPropertySource tamayaSpringPropertySource = new TamayaSpringPropertySource(configSupplier);
            env.getPropertySources().addFirst(tamayaSpringPropertySource);
            PropertySourcesPlaceholderConfigurer cfgBean = new PropertySourcesPlaceholderConfigurer();
            cfgBean.setEnvironment(env);
            return cfgBean;
        }

    }

    /**
     * Configuration which enables Tamaya Configuration Injection Mechanisms within Spring (default).
     */
    public static class TamayaInjectionConfig {

        @Bean
        public SpringConfigInjectionPostProcessor tamayaConfigPostProcessor(ObjectFactory<org.apache.tamaya.Configuration> configSupplier) {
            return new SpringConfigInjectionPostProcessor(configSupplier);
        }
    }


 }