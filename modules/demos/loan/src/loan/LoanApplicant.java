package loan;

import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.Applicant;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.ApplicationPortal;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.applyForLoan;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.reject;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.requestConfirmation;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier;
import loan.LoanApplication.BuyerBrokerSupplier.channels.Applicant.BuyerBrokerSupplier_Applicant_1;
import loan.LoanApplication.BuyerBrokerSupplier.channels.Applicant.BuyerBrokerSupplier_Applicant_2_Cases;
import loan.LoanApplication.BuyerBrokerSupplier.roles.Applicant;

public class LoanApplicant
{
	public static void main(String[] args) throws Exception
	{
		BuyerBrokerSupplier sess = new BuyerBrokerSupplier();
		try (MPSTEndpoint<BuyerBrokerSupplier, Applicant> se
				= new MPSTEndpoint<>(sess, Applicant, new ObjectStreamFormatter()))
		{
			se.connect(ApplicationPortal, SocketChannelEndpoint::new, "localhost", 8888);
			
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
