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

/**
 * Policy to control how new values are applied to a {@link DynamicValue}.
 */
public enum UpdatePolicy {
    /**
     * @deprecated Use {@link #IMMEDIATE} instead of.
     */
    @Deprecated
    IMMEDEATE,
    /** New values are applied immediately and registered listeners are informed about the change. */
    IMMEDIATE,
    /** New values or not applied, but stored in the createValue property. Explicit call to DynamicValue#commit
     of DynamicValue#commitAndGet are required to accept the change and inform the listeners about the change.
     * Registered listeners will be informed, when the commit was performed explicitly.
     */
    EXPLICIT,
    /**
     * @deprecated Use {@link #EXPLICIT} instead of.
     */
    @Deprecated
    EXPLCIT,
    /**
     * New values are always immediately discarded, listeners are not triggered.
     */
    NEVER,
    /**
     * All listeners are informed about the change encountered, but the createValue will not be applied.
     */
    LOG_ONLY
}
