package demo.bettybook.math.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIMath extends Remote
{
   void val(Integer x) throws RemoteException;
   void bye() throws RemoteException;
   Integer add(Integer y) throws RemoteException;
   Integer mult(Integer y) throws RemoteException;
}
