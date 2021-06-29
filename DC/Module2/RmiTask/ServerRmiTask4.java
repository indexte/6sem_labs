package ModuleWork2.RmiTask;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import ModuleWork2.DaoTask.DAOTask4;

public class ServerRmiTask4 {
    public static void main(String[] args) throws RemoteException {
        DAOTask4 serverRMIImpl = new DAOTask4();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("serverRMI", serverRMIImpl);
        System.out.println("Server start");
    }
}
