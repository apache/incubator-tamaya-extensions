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
package org.apache.tamaya.inject.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation allows to define the default section names used for evaluating the candidate
 * keys used for evaluating configuration values. The configuration keys
 * used are additionally determined by the {@link org.apache.tamaya.inject.api.Config}
 * annotation(s). This annotation allows
 * to define the configuration section that is prefixed to all <b>relative</b> configuration keys.
 * @see Config
 * @see ConfigAutoInject
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface ConfigDefaultSections {

    /**
     * Allows to declare an section names that are prepended to resolve relative configuration keys.
     * @return the section names to used for key resolution.
     */
    String[] value() default {};

}
