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
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.FinanceDept;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.ProcessingDept;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.applyForLoan;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.checkEligibility;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.getLoanAmount;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.reject;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.requestConfirmation;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.respond;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.sendLoanAmount;
import loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier;
import loan.LoanApplication.BuyerBrokerSupplier.channels.ApplicationPortal.BuyerBrokerSupplier_ApplicationPortal_1;
import loan.LoanApplication.BuyerBrokerSupplier.channels.ApplicationPortal.BuyerBrokerSupplier_ApplicationPortal_4;
import loan.LoanApplication.BuyerBrokerSupplier.roles.ApplicationPortal;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

public class LoanApplicationPortal
{
	public static void main(String[] args) throws Exception
	{
		BuyerBrokerSupplier sess = new BuyerBrokerSupplier();
		try (
			ScribServerSocket ss = new SocketChannelServer(8888);
			MPSTEndpoint<BuyerBrokerSupplier, ApplicationPortal> se
					= new MPSTEndpoint<>(sess, ApplicationPortal, new ObjectStreamFormatter()))
		{
			se.accept(ss, Applicant);
			se.connect(ProcessingDept, SocketChannelEndpoint::new, "localhost", 7777);
			se.connect(FinanceDept, SocketChannelEndpoint::new, "localhost", 9999);
			
			Buf<String> customerName = new Buf<>();
			Buf<String> dateOfBirth = new Buf<>();
			Buf<Integer> annualSalary = new Buf<>();
			Buf<Integer> creditRating = new Buf<>();
			Buf<Boolean> response = new Buf<>();
			BuyerBrokerSupplier_ApplicationPortal_4 s4
				= new BuyerBrokerSupplier_ApplicationPortal_1(se)
					.receive(Applicant, applyForLoan, customerName, dateOfBirth, annualSalary, creditRating)
					.send(ProcessingDept, checkEligibility, customerName.val, dateOfBirth.val, annualSalary.val, creditRating.val)
					.receive(ProcessingDept
							, respond, response);
			if (response.val)
			{
				Buf<Integer> loan = new Buf<>();
				s4.send(FinanceDept, getLoanAmount, 789)
				  .receive(FinanceDept, sendLoanAmount, loan)
				  .send(Applicant, requestConfirmation, loan.val);
			}
			else
			{
				s4.send(FinanceDept, reject)
				  .send(Applicant, reject);
			}
		}
	}
}
