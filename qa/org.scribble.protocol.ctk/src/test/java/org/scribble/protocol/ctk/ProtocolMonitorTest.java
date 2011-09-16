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

import static org.junit.Assert.*;

import org.scribble.command.simulate.Event;
import org.scribble.protocol.export.monitor.MonitorProtocolExporter;
import org.scribble.protocol.model.*;
import org.scribble.protocol.monitor.DefaultSession;
import org.scribble.protocol.monitor.DefaultMonitorContext;
import org.scribble.protocol.monitor.ProtocolMonitor;
import org.scribble.protocol.monitor.Session;
import org.scribble.protocol.monitor.model.Description;

public class ProtocolMonitorTest {
    
    protected void testMonitor(String modelFile, String eventFile, boolean shouldFail) {
        TestJournal journal=new TestJournal();
        
        // Load the model
        ProtocolModel model=CTKUtil.getModel(modelFile, journal);
        
        assertNotNull(model);    
        assertTrue(journal.getErrorCount() == 0);
        
        MonitorProtocolExporter exporter=new MonitorProtocolExporter();
        
        // Generate the monitoring description from the model
        Description desc=exporter.generateDescription(model, journal);
        
        assertNotNull(desc);
        assertTrue(journal.getErrorCount() == 0);
        
        // Retrieve monitoring events
        java.util.List<Event> events=CTKUtil.getMonitorEvents(eventFile);
        
        assertNotNull(events);
        
        ProtocolMonitor monitor=CTKUtil.getMonitor();

        DefaultMonitorContext context=new DefaultMonitorContext();
        
        Session conv=monitor.createSession(context, desc, DefaultSession.class);
        
        boolean failed=false;
        
        for (Event event : events) {
            if (event.validate(monitor, context, desc, conv).isValid() == false) {
                failed = true;
                
                if (!shouldFail) {
                    fail("Failed to monitor '"+event+"'");
                }
            }
        }
        
        if (shouldFail && !failed) {
            fail("Monitor test was expected to fail but didn't");
        }
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice_1AtBuyer() {
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice@Buyer.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice-1@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice_2AtBuyer() {
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice@Buyer.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice-2@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice_3AtBuyer() {
        // FAIL
        // Incorrect message type
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice@Buyer.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice-3@Buyer.events", true);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice_4AtBuyer() {
        // Don't explicitly define the choice decision, so use lookahead to determine the
        // choice
        
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice@Buyer.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice-4@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice2_1AtBuyer() {
        // Test nested choice, where one choice path has just a operator, events
        // explicitly define both choice decisions
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice2@Buyer.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice2-1@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice2_2AtBuyer() {
        // Test nested choice, with only outer choice having explicit decision
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice2@Buyer.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice2-2@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice2_3AtBuyer() {
        // FAIL
        // Test nested choice, with only inner choice having explicit decision
        // which will fail as the decision will be matched against the outer choice
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice2@Buyer.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice2-3@Buyer.events", true);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice2_4AtBuyer() {
        // Test nested choice, with no explicit choice decisions
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice2@Buyer.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice2-4@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice2_5AtBuyer() {
        // Test invalid product path, with explicit decision
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice2@Buyer.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice2-5@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice2_1AtBroker() {
        // Test Order to Confirmation using only lookahead, so no explicit choice decisions
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice2@Broker.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice2-1@Broker.events", false);
    }
    
    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice2_2AtBroker() {
        // Test Order with InvalidProduct returned, and no explicit choice decisions
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice2@Broker.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice2-2@Broker.events", false);
    }

    @org.junit.Test
    public void testMultiPartyInteractionsAndChoice2_3AtBroker() {
        // Test Order to Confirmation using explicit choice decisions
        testMonitor("tests/protocol/local/MultiPartyInteractionsAndChoice2@Broker.spr",
                "tests/monitor/MultiPartyInteractionsAndChoice2-3@Broker.events", false);
    }
    
    @org.junit.Test
    public void testParallel_1AtBuyer() {
        testMonitor("tests/protocol/local/Parallel@Buyer.spr",
                "tests/monitor/Parallel-1@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testParallel_2AtBuyer() {
        testMonitor("tests/protocol/local/Parallel@Buyer.spr",
                "tests/monitor/Parallel-2@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testParallel_1AtSeller() {
        testMonitor("tests/protocol/local/Parallel@Seller.spr",
                "tests/monitor/Parallel-1@Seller.events", false);
    }
    
    @org.junit.Test
    public void testParallel_2AtSeller() {
        testMonitor("tests/protocol/local/Parallel@Seller.spr",
                "tests/monitor/Parallel-2@Seller.events", false);
    }
    
    @org.junit.Test
    public void testUnordered_1AtBuyer() {
        testMonitor("tests/protocol/local/Unordered@Buyer.spr",
                "tests/monitor/Unordered-1@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testUnordered_2AtBuyer() {
        testMonitor("tests/protocol/local/Unordered@Buyer.spr",
                "tests/monitor/Unordered-2@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testUnordered_1AtSeller() {
        testMonitor("tests/protocol/local/Unordered@Seller.spr",
                "tests/monitor/Unordered-1@Seller.events", false);
    }
    
    @org.junit.Test
    public void testUnordered_2AtSeller() {
        testMonitor("tests/protocol/local/Unordered@Seller.spr",
                "tests/monitor/Unordered-2@Seller.events", false);
    }
    
    @org.junit.Test
    public void testRepeat_1AtBuyer() {
        testMonitor("tests/protocol/local/Repeat@Buyer.spr",
                "tests/monitor/Repeat-1@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testRepeat_2AtBuyer() {
        testMonitor("tests/protocol/local/Repeat@Buyer.spr",
                "tests/monitor/Repeat-2@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testRepeat_1AtSeller() {
        testMonitor("tests/protocol/local/Repeat@Seller.spr",
                "tests/monitor/Repeat-1@Seller.events", false);
    }
    
    @org.junit.Test
    public void testRepeat_2AtSeller() {
        testMonitor("tests/protocol/local/Repeat@Seller.spr",
                "tests/monitor/Repeat-2@Seller.events", false);
    }
    
    @org.junit.Test
    public void testRecur_1AtBuyer() {
        testMonitor("tests/protocol/local/Recur@Buyer.spr",
                "tests/monitor/Recur-1@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testRecur_1AtSeller() {
        testMonitor("tests/protocol/local/Recur@Seller.spr",
                "tests/monitor/Recur-1@Seller.events", false);
    }
    
    @org.junit.Test
    public void testRecur_2AtBuyer() {
        testMonitor("tests/protocol/local/Recur@Buyer.spr",
                "tests/monitor/Recur-2@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testRecur_2AtSeller() {
        testMonitor("tests/protocol/local/Recur@Seller.spr",
                "tests/monitor/Recur-2@Seller.events", false);
    }

    @org.junit.Test
    @org.junit.Ignore
    public void testRecur2_1AtBuyer() {
        testMonitor("tests/protocol/local/Recur2@Buyer.spr",
                "tests/monitor/Recur2-1@Buyer.events", false);
    }
    
    @org.junit.Test
    @org.junit.Ignore
    public void testRecur2_1AtSeller() {
        testMonitor("tests/protocol/local/Recur2@Seller.spr",
                "tests/monitor/Recur2-1@Seller.events", false);
    }
    
    @org.junit.Test
    public void testRecur3_1AtBuyer() {
        testMonitor("tests/protocol/local/Recur3@Buyer.spr",
                "tests/monitor/Recur3-1@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testRecur3_1AtSeller() {
        testMonitor("tests/protocol/local/Recur3@Seller.spr",
                "tests/monitor/Recur3-1@Seller.events", false);
    }
    
    @org.junit.Test
    public void testRunSubProtocolAtBuyer() {
        testMonitor("tests/protocol/local/RunSubProtocol@Buyer.spr",
                "tests/monitor/RunSubProtocol@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testRunSubProtocolAtSeller() {
        testMonitor("tests/protocol/local/RunSubProtocol@Seller.spr",
                "tests/monitor/RunSubProtocol@Seller.events", false);
    }
    
    @org.junit.Test
    public void testDoInterrupt_1AtBuyer() {
        testMonitor("tests/protocol/local/DoInterrupt@Buyer.spr",
                "tests/monitor/DoInterrupt-1@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testDoInterrupt_1AtSeller() {
        testMonitor("tests/protocol/local/DoInterrupt@Seller.spr",
                "tests/monitor/DoInterrupt-1@Seller.events", false);
    }
    
    @org.junit.Test
    public void testDoInterrupt_2AtBuyer() {
        testMonitor("tests/protocol/local/DoInterrupt@Buyer.spr",
                "tests/monitor/DoInterrupt-2@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testDoInterrupt_2AtSeller() {
        testMonitor("tests/protocol/local/DoInterrupt@Seller.spr",
                "tests/monitor/DoInterrupt-2@Seller.events", false);
    }

    @org.junit.Test
    public void testSingleInteractionXSDImportAtBuyer() {
        testMonitor("tests/protocol/local/SingleInteractionXSDImport@Buyer.spr",
                "tests/monitor/SingleInteractionXSDImport@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testSingleInteractionXSDImportAtSeller() {
        testMonitor("tests/protocol/local/SingleInteractionXSDImport@Seller.spr",
                "tests/monitor/SingleInteractionXSDImport@Seller.events", false);
    }
    
    @org.junit.Test
    public void testDirectedChoice_1AtBuyer() {
        testMonitor("tests/protocol/local/DirectedChoice@Buyer.spr",
                "tests/monitor/DirectedChoice-1@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testDirectedChoice_1AtSeller() {
        testMonitor("tests/protocol/local/DirectedChoice@Seller.spr",
                "tests/monitor/DirectedChoice-1@Seller.events", false);
    }
    
    @org.junit.Test
    public void testDirectedChoice_2AtBuyer() {
        testMonitor("tests/protocol/local/DirectedChoice@Buyer.spr",
                "tests/monitor/DirectedChoice-2@Buyer.events", false);
    }
    
    @org.junit.Test
    public void testDirectedChoice_2AtSeller() {
        testMonitor("tests/protocol/local/DirectedChoice@Seller.spr",
                "tests/monitor/DirectedChoice-2@Seller.events", false);
    }
}
