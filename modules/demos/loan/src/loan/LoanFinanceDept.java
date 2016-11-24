package loan;

import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.ApplicationPortal;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.FinanceDept;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.getLoanAmount;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.reject;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.sendLoanAmount;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

import loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier;
import loan.LoanApplication.BuyerBrokerSupplier.channels.FinanceDept.BuyerBrokerSupplier_FinanceDept_1;
import loan.LoanApplication.BuyerBrokerSupplier.channels.FinanceDept.BuyerBrokerSupplier_FinanceDept_1_Cases;
import loan.LoanApplication.BuyerBrokerSupplier.roles.FinanceDept;

public class LoanFinanceDept
{
	public static void main(String[] args) throws Exception
	{
		BuyerBrokerSupplier sess = new BuyerBrokerSupplier();
		try (
			ScribServerSocket ss = new SocketChannelServer(9999);
			MPSTEndpoint<BuyerBrokerSupplier, FinanceDept> se
					= new MPSTEndpoint<>(sess, FinanceDept, new ObjectStreamFormatter()))
		{
			se.accept(ss, ApplicationPortal);
			
			BuyerBrokerSupplier_FinanceDept_1_Cases branch
				= new BuyerBrokerSupplier_FinanceDept_1(se).branch(ApplicationPortal);
			switch (branch.getOp())
			{
				case getLoanAmount:
				{
					Buf<Integer> loan = new Buf<>();
					branch
						.receive(ApplicationPortal, getLoanAmount, loan)
						.send(ApplicationPortal, sendLoanAmount, loan.val);
					break;
				}
				case reject:
				{
					branch.receive(ApplicationPortal, reject);
					break;
				}
			}
		}
	}
}
