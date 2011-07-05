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
public class ProtocolProjectorTest {

    private String globalModelFile;
    private String expectedLocalModelFile;
    private ProtocolContext context;

    public ProtocolProjectorTest(String globalModelFile, String expectedLocalModelFile, ProtocolContext context) {
        this.globalModelFile = globalModelFile;
        this.expectedLocalModelFile = expectedLocalModelFile;
        this.context = context;
    }

    @Parameterized.Parameters
    public static List<Object[]> testcases() {
        Object[][] array = new Object[][]{

            {"Annotation.spr", "Annotation@Buyer.spr"}, // 0          
            {"Annotation.spr", "Annotation@Seller.spr"}, // 1

            {"Choice.spr", "Choice@Buyer.spr"},          // 2
            {"Choice.spr", "Choice@Seller.spr"},         // 3
            {"ChoiceMerge.spr", "ChoiceMerge@Broker.spr"}, // 4
        		
            // SCRIBBLE-102 - broker needs to be parameter
            {"ChoiceMerge.spr", "ChoiceMerge@Seller.spr"}, // 5
            
            {"ChoiceMergeCommonPrefix.spr", "ChoiceMergeCommonPrefix@A.spr"}, // 6
            
            {"ChoiceMergeCommonPrefix.spr", "ChoiceMergeCommonPrefix@B.spr"}, // 7

            //{"ChoiceMergeCommonPrefix.spr", "ChoiceMergeCommonPrefix@C.spr"}, not passing yet (SCRIBBLE-84)
            {"ChoiceMergeIdenticalBranches.spr", "ChoiceMergeIdenticalBranches@A.spr"}, // 8
            {"ChoiceMergeIdenticalBranches.spr", "ChoiceMergeIdenticalBranches@B.spr"}, // 9
           
        	// TODO: Need to merge
            //{"ChoiceMergeIdenticalBranches.spr", "ChoiceMergeIdenticalBranches@C.spr"}, // 10
        		
            {"ChoiceMergeNested.spr", "ChoiceMergeNested@A.spr"},         // 11

        	// SCRIBBLE-102
            //{"ChoiceMergeNested.spr", "ChoiceMergeNested@B.spr"},         // 12
        	// SCRIBBLE-102
            //{"ChoiceMergeNested.spr", "ChoiceMergeNested@C.spr"},         // 13

        	// SCRIBBLE-91
            //{"ChoiceMergeNested.spr", "ChoiceMergeNested@D.spr"},         // 14

            {"IncludeProtocol.spr", "IncludeProtocol@Buyer.spr", CTKUtil.getProtocolContext("tests/protocol/global")}, // 14
            {"IncludeProtocol.spr", "IncludeProtocol@Seller.spr", CTKUtil.getProtocolContext("tests/protocol/global")},  // 15

            {"MultiPartyInteractionsAndChoice.spr", "MultiPartyInteractionsAndChoice@Broker.spr"}, // 16
        	{"MultiPartyInteractionsAndChoice.spr", "MultiPartyInteractionsAndChoice@Buyer.spr"}, // 17
            
        	// SCRIBBLE-102 - needs Broker as parameter
        	{"MultiPartyInteractionsAndChoice.spr", "MultiPartyInteractionsAndChoice@CreditAgency.spr"}, // 18            
        	{"MultiPartyInteractionsAndChoice2.spr", "MultiPartyInteractionsAndChoice2@Broker.spr"}, // 20

        	{"MultiPartyInteractionsAndChoice2.spr", "MultiPartyInteractionsAndChoice2@Buyer.spr"}, // 21
           
            // TODO: Invalid merging
            //{"MultiPartyInteractionsAndChoice.spr", "MultiPartyInteractionsAndChoice@Seller.spr"}, // 19
            //{"MultiPartyInteractionsAndChoice2.spr", "MultiPartyInteractionsAndChoice2@Seller.spr"}, // 22
            
            
            {"MulticastInteraction.spr", "MulticastInteraction@Buyer.spr"}, // 23
            {"MulticastInteraction.spr", "MulticastInteraction@SellerA.spr"}, // 24
        	{"Parallel.spr", "Parallel@Buyer.spr"}, // 27
			{"Parallel.spr", "Parallel@Seller.spr"}, // 28

            {"Recur.spr", "Recur@Buyer.spr"},
            {"Recur.spr", "Recur@Seller.spr"},
            {"Recur2.spr", "Recur2@Buyer.spr"},
            
            {"Recur2.spr", "Recur2@Seller.spr"},
            {"Repeat.spr", "Repeat@Buyer.spr"},
            {"Repeat.spr", "Repeat@Seller.spr"},
        	// SCRIBBLE-102
            //{"Repeat2.spr", "Repeat2@CreditAgency.spr"},
            {"Repeat2.spr", "Repeat2@Buyer.spr"},
            {"RunExternalProtocol.spr", "RunExternalProtocol@Buyer.spr", CTKUtil.getProtocolContext("tests/protocol/global")},
            {"RunExternalProtocol.spr", "RunExternalProtocol@Seller.spr", CTKUtil.getProtocolContext("tests/protocol/global")},
            {"RunSubProtocol.spr", "RunSubProtocol@Buyer.spr"},
            {"RunSubProtocol.spr", "RunSubProtocol@Seller.spr"},
            
            {"SingleInteraction.spr", "SingleInteraction@Buyer.spr"},
            {"SingleInteraction.spr", "SingleInteraction@Seller.spr"},
            {"SingleInteractionXSDImport.spr", "SingleInteractionXSDImport@Buyer.spr"},
            {"SingleInteractionXSDImport.spr", "SingleInteractionXSDImport@Seller.spr"},
            {"DoInterrupt.spr", "DoInterrupt@Buyer.spr"},
            {"DoInterrupt.spr", "DoInterrupt@Seller.spr"},
            {"Unordered.spr", "Unordered@Buyer.spr"},
            {"Unordered.spr", "Unordered@Seller.spr"},
            {"PurchaseGoods3.spr", "PurchaseGoods3@Buyer.spr"},
            {"PurchaseGoods3.spr", "PurchaseGoods3@Store.spr"},
            {"DirectedChoice.spr", "DirectedChoice@Buyer.spr"},
            {"DirectedChoice.spr", "DirectedChoice@Seller.spr"},

            {"End.spr", "End@Buyer.spr"},
            {"End.spr", "End@Seller.spr"},
            
            /*
            {"ESBBroker.spr", "ESBBroker@SupplierTxnProcessor.spr"}
            /*
 */
         };
        List<Object[]> result = new LinkedList<Object[]>();
        for (Object[] sub: array) {
            result.add(new Object[] {
                    "tests/protocol/global/" + sub[0],
                    "tests/protocol/local/" + sub[1],
                    (sub.length == 3 ? sub[2] : null)
            });
        }
        return result;
    }

    @Test
    public void doTest() {
        CTKUtil.checkProjectsSuccessfully(globalModelFile, expectedLocalModelFile, context);
    }
}
