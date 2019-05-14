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
package org.apache.tamaya.microprofile.tck;

import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.core.spi.LoadableExtension;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Arquillian extension to load Tamaya into Arquillian context.
 * @author <a href="mailto:anatole@apache.org">Anatole Tresch</a>
 */
public class TamayaConfigExtension implements LoadableExtension {

    static {
        System.out.println("Preparing environment for Emily Tests");
        try {
            // Modify Environment
            Map<String, String> map = System.getenv();
            Field f = map.getClass().getDeclaredField("m");
            f.setAccessible(true);
            Map<String, String> modifiableMap = (Map<String, String>)f.get(map);
            modifiableMap.put("my_int_property", "45");
            modifiableMap.put("MY_BOOLEAN_PROPERTY", "true");
            modifiableMap.put("my_string_property", "haha");
            modifiableMap.put("MY_STRING_PROPERTY", "woohoo");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void register(ExtensionBuilder extensionBuilder) {
        extensionBuilder.service(
                ApplicationArchiveProcessor.class,
                TamayaConfigArchiveProcessor.class);
    }
}