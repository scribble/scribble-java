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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scribble.protocol.ProtocolContext;

import java.util.LinkedList;
import java.util.List;

@RunWith(Parameterized.class)
public class ProtocolProjectorTestCasesTest {

    private String globalModelFile;
    private String expectedLocalModelFile;
    private ProtocolContext context;

    public ProtocolProjectorTestCasesTest(String globalModelFile, String expectedLocalModelFile, ProtocolContext context) {
        this.globalModelFile = globalModelFile;
        this.expectedLocalModelFile = expectedLocalModelFile;
        this.context = context;
    }

    @Parameterized.Parameters
    public static List<Object[]> testcases() {
        Object[][] array = new Object[][]{

            {"SCRIBBLE-125.spr", "SCRIBBLE-125@C.spr"},
            
        };
        List<Object[]> result = new LinkedList<Object[]>();
        for (Object[] sub: array) {
            result.add(new Object[] {
                    "tests/protocol/global/testcase/" + sub[0],
                    "tests/protocol/local/testcase/" + sub[1],
                    (sub.length == 3 ? sub[2] :  CTKUtil.getProtocolContext(null))
            });
        }
        return result;
    }

    @Test
    public void doTest() {
        CTKUtil.checkProjectsSuccessfully(globalModelFile, expectedLocalModelFile, context);
    }
}
