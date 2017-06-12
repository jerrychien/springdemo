package com.jerry.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by jerrychien on 2017-06-12 15:34.
 */
//RMI服务端
public class Server {
    public static void main(String[] args) {
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(6666);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            IServiceImpl service = new IServiceImpl();
            registry.bind("vince", service);
            System.out.println("bind server");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

    }
}
