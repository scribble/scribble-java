package bettybook.math.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIMath extends Remote
{
   void Val(Integer x) throws RemoteException;
   void Bye() throws RemoteException;
   Integer Add(Integer y) throws RemoteException;
   Integer Mult(Integer y) throws RemoteException;
}
