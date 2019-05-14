/*
 * Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.tamaya.microprofile;

import java.util.Objects;

import org.apache.tamaya.spi.PropertySource;
import org.apache.tamaya.spi.PropertySourceProvider;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.ConfigSourceProvider;

/**
 * Microprofile {@link ConfigSource} implementation that wraps a {@link PropertySource} instance.
 */
public class MicroprofileConfigSourceProvider implements ConfigSourceProvider{

    private PropertySourceProvider delegate;

    public MicroprofileConfigSourceProvider(PropertySourceProvider propertySourceProvider){
        this.delegate = Objects.requireNonNull(propertySourceProvider);
    }

    public PropertySourceProvider getPropertySourceProvider(){
        return this.delegate;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources(ClassLoader forClassLoader) {
        if(delegate instanceof TamayaPropertySourceProvider){
            return ((TamayaPropertySourceProvider)delegate).getConfigSourceProvider()
                    .getConfigSources(forClassLoader);
        } else {
            return MicroprofileAdapter.toConfigSources(delegate.getPropertySources());
        }
    }

    @Override
    public String toString() {
        return "TamayaConfigSourceProvider{" +
                "delegate=" + delegate +
                '}';
    }
}
