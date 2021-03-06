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
package org.apache.tamaya.karaf.shell;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.lifecycle.Reference;
import org.apache.karaf.shell.api.action.lifecycle.Service;
import org.apache.tamaya.osgi.commands.BackupCommands;
import org.apache.tamaya.osgi.commands.TamayaConfigService;

import java.io.IOException;

/**
 * A Karaf shell command.
 */
@Command(scope = "tamaya", name = "tm_backup_delete", description="Deletes the OSGI configuration backup  of Tamya.")
@Service
public class BackupDeleteCommand implements Action{

    @Reference
    private TamayaConfigService configPlugin;

    @Argument(index = 0, name = "pid", description = "Allows to filter on the given PID. '*' removes all backups.",
            required = true, multiValued = false)
    String pid;

    @Override
    public Object execute() throws IOException {
        return(BackupCommands.deleteBackup(configPlugin, pid));
    }

}
