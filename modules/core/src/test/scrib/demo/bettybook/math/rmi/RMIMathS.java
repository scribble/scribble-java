package demo.bettybook.math.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIMathS implements RMIMath
{
	private int x;

	public void val(Integer x) throws RemoteException
	{
		this.x = x;
	}

	public void bye() throws RemoteException
	{

	}

	public Integer add(Integer y) throws RemoteException
	{
		return this.x + y;
	}

	public Integer mult(Integer y) throws RemoteException
	{
		return this.x * y;
	}

	public static void main(String[] args) throws Exception
	{
		if (System.getSecurityManager() == null)
		{
			System.setSecurityManager(new SecurityManager());
		}
		String name = "MathService";
		RMIMath engine = new RMIMathS();
		RMIMath stub = (RMIMath) UnicastRemoteObject.exportObject(engine, 0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind(name, stub);
	}
}
