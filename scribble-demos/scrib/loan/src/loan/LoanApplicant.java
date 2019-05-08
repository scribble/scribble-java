/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package loan;

import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.Applicant;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.ApplicationPortal;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.applyForLoan;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.reject;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.requestConfirmation;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier;
import loan.LoanApplication.BuyerBrokerSupplier.roles.Applicant;
import loan.LoanApplication.BuyerBrokerSupplier.statechans.Applicant.BuyerBrokerSupplier_Applicant_1;
import loan.LoanApplication.BuyerBrokerSupplier.statechans.Applicant.BuyerBrokerSupplier_Applicant_2_Cases;

public class LoanApplicant
{
	public static void main(String[] args) throws Exception
	{
		BuyerBrokerSupplier sess = new BuyerBrokerSupplier();
		try (MPSTEndpoint<BuyerBrokerSupplier, Applicant> se
				= new MPSTEndpoint<>(sess, Applicant, new ObjectStreamFormatter()))
		{
			se.request(ApplicationPortal, SocketChannelEndpoint::new, "localhost", 8888);
			
			BuyerBrokerSupplier_Applicant_2_Cases branch
				= new BuyerBrokerSupplier_Applicant_1(se)
					.send(ApplicationPortal, applyForLoan, "Name", "DoB", 123, 456)
					.branch(ApplicationPortal);
			switch (branch.getOp())
			{
				case reject:
				{
					branch.receive(reject);
					break;
				}
				case requestConfirmation:
				{
					Buf<Integer> b = new Buf<>();
					branch.receive(requestConfirmation, b);
					break;
				}
			}
		}
	}
}
