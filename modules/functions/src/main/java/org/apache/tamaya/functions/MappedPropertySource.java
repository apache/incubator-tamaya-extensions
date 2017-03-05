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
package org.apache.tamaya.functions;

import org.apache.tamaya.spi.PropertySource;
import org.apache.tamaya.spi.PropertyValue;
import org.apache.tamaya.spisupport.PropertySourceComparator;

import java.util.*;

/**
 * PropertySource implementation that maps certain parts (defined by an {@code UnaryOperator<String>}) to alternate sections.
 */
class MappedPropertySource implements PropertySource {

    private static final long serialVersionUID = 8690637705511432083L;

    /**
     * The mapping operator.
     */
    private final KeyMapper keyMapper;
    /**
     * The base configuration.
     */
    private final PropertySource propertySource;

    /**
     * Creates a new instance.
     *
     * @param config    the base configuration, not null
     * @param keyMapper The mapping operator, not null
     */
    public MappedPropertySource(PropertySource config, KeyMapper keyMapper) {
        this.propertySource = Objects.requireNonNull(config);
        this.keyMapper = Objects.requireNonNull(keyMapper);
    }

    @Override
    public int getOrdinal() {
        return PropertySourceComparator.getOrdinal(this.propertySource);
    }

    @Override
    public String getName() {
        return this.propertySource.getName() + "[mapped]";
    }

    @Override
    public Map<String, PropertyValue> getProperties() {
        Map<String,PropertyValue> result = new HashMap<>();
        for (PropertyValue en : this.propertySource.getProperties().values()) {
            String targetKey = keyMapper.mapKey(en.getKey());
            if (targetKey != null) {
                result.put(targetKey, PropertyValue.of(targetKey, en.getValue(), getName()));
            }
        }
        return result;
    }

    @Override
    public boolean isScannable() {
        return propertySource.isScannable();
    }

    @Override
    public PropertyValue get(String key) {
        PropertyValue result = this.propertySource.get(key);
        if(result!=null){
            String targetKey = keyMapper.mapKey(key);
            if (targetKey != null) {
                return result.toBuilder().mapKey(targetKey).build();
            }
        }
        return null;
    }

}