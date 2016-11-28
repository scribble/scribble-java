package loan;

import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.ApplicationPortal;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.checkEligibility;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.ProcessingDept;
import static loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier.respond;
import loan.LoanApplication.BuyerBrokerSupplier.BuyerBrokerSupplier;
import loan.LoanApplication.BuyerBrokerSupplier.channels.ProcessingDept.BuyerBrokerSupplier_ProcessingDept_1;
import loan.LoanApplication.BuyerBrokerSupplier.roles.ProcessingDept;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

public class LoanProcessingDept
{
	public static void main(String[] args) throws Exception
	{
		BuyerBrokerSupplier sess = new BuyerBrokerSupplier();
		try (
			ScribServerSocket ss = new SocketChannelServer(7777);
			MPSTEndpoint<BuyerBrokerSupplier, ProcessingDept> se
					= new MPSTEndpoint<>(sess, ProcessingDept, new ObjectStreamFormatter()))
		{
			se.accept(ss, ApplicationPortal);
			
			Buf<String> customerName = new Buf<>();
			Buf<String> dateOfBirth = new Buf<>();
			Buf<Integer> annualSalary = new Buf<>();
			Buf<Integer> creditRating = new Buf<>();
			new BuyerBrokerSupplier_ProcessingDept_1(se)
				.receive(ApplicationPortal, checkEligibility, customerName, dateOfBirth, annualSalary, creditRating)
				.send(ApplicationPortal, respond, true);
		}
	}
}
