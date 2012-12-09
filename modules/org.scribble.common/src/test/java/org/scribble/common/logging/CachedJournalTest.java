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
package org.scribble.common.logging;

import junit.framework.TestCase;

//import static org.junit.Assert.*;

public class CachedJournalTest extends TestCase {

    private static final String ISSUE_VALUE1 = "IssueValue1";
    private static final String ISSUE_KEY1 = "IssueKey1";
    private static final String TEST_ISSUE = "TestIssue";

    //@org.junit.Test
    public void testGetIssues() {
        CachedJournal journal=new CachedJournal();
        
        java.util.Map<String, Object> props=new java.util.HashMap<String, Object>();
        props.put(ISSUE_KEY1, ISSUE_VALUE1);
        
        journal.error(TEST_ISSUE, props);
        
        java.util.List<CachedJournal.IssueDetails> details=journal.getIssues();
        
        if (details.size() != 1) {
            fail("Should be one entry");
        }
        
        CachedJournal.IssueDetails d=details.get(0);
        
        if (d.getMessage().equals(TEST_ISSUE) == false) {
            fail("Message not correct");
        }
        
        if (d.getIssueType() != CachedJournal.IssueType.Error) {
            fail("Issue type not an error");
        }
        
        if (d.getProperties() != props) {
            fail("Properties not the same");
        }
    }
}
