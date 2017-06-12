package com.jerry.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by jerrychien on 2017-06-12 15:28.
 */
public class IServiceImpl extends UnicastRemoteObject implements IService {

    /**
     */
    private static final long serialVersionUID = 682805210518738166L;

    public IServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() throws RemoteException {
        System.out.println("say hello server");
        return String.valueOf(System.currentTimeMillis());
    }
}
