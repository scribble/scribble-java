package loan;

import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.ApplicationPortal;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.LoanProcessingDept;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.checkEligibility;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.respond;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

import loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier;
import loan.LoanApplication.BuyerBrokerSupplier.channels.LoanProcessingDept.BuyerBrokerSupplier_LoanProcessingDept_1;
import loan.LoanApplication.BuyerBrokerSupplier.roles.LoanProcessingDept;

public class MyLoanProcessingDept
{
	public static void main(String[] args) throws Exception
	{
		BuyerBrokerSupplier sess = new BuyerBrokerSupplier();
		try (
			ScribServerSocket ss = new SocketChannelServer(7777);
			MPSTEndpoint<BuyerBrokerSupplier, LoanProcessingDept> se
					= new MPSTEndpoint<>(sess, LoanProcessingDept, new ObjectStreamFormatter()))
		{
			se.accept(ss, ApplicationPortal);
			
			Buf<String> customerName = new Buf<>();
			Buf<String> dateOfBirth = new Buf<>();
			Buf<Integer> annualSalary = new Buf<>();
			Buf<Integer> creditRating = new Buf<>();
			new BuyerBrokerSupplier_LoanProcessingDept_1(se)
				.receive(ApplicationPortal, checkEligibility, customerName, dateOfBirth, annualSalary, creditRating)
				.send(ApplicationPortal, respond, true);
		}
	}
}
