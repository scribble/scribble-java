/*
 * Copyright 2009-10 www.scribble.org
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
 *
 */
package org.scribble.protocol.ctk;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scribble.protocol.ProtocolContext;
import org.scribble.protocol.export.text.TextProtocolExporter;
import org.scribble.protocol.model.ProtocolModel;
import org.scribble.protocol.model.Role;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class ProtocolProjectorRejectsTest {

    private String globalModelFile;
    private ProtocolContext context;

    public ProtocolProjectorRejectsTest(String globalModelFile, ProtocolContext context) {
        this.globalModelFile = globalModelFile;
        this.context = context;
    }

    @Parameterized.Parameters
    public static List<Object[]> testcases() {
        Object[][] array = new Object[][]{
            {"ChoiceNotMergeableSimple.spr"},
            {"ChoiceNotMergeableRecursion.spr"}
        };
        List<Object[]> result = new LinkedList<Object[]>();
        for (Object[] sub: array) {
            result.add(new Object[] {
                    "tests/protocol/global/witherrors/" + sub[0],
                    (sub.length == 2 ? sub[1] : null)
            });
        }
        return result;
    }

    @Ignore("fails, reported as SCRIBBLE-85")
    @Test
    public void doTest() {
        TestJournal logger=new TestJournal();

        ProtocolModel model= CTKUtil.getModel(globalModelFile, logger);

        for (Role role: model.getRoles()) {
            ProtocolModel projected= CTKUtil.project(model, role, logger, context);

            if (projected != null) {
                ByteArrayOutputStream arrayOs = new ByteArrayOutputStream();
                new TextProtocolExporter().export(projected, logger, arrayOs);
                fail("The protocol in file: " + globalModelFile + " should not be projectable. " +
                     "Projection was successful for role: " + role + ", result:\n" + arrayOs.toString());
            }
            assertTrue(logger.getErrorCount() > 0);
        }
    }
}
