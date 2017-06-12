package com.jerry.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by jerrychien on 2017-06-12 15:27.
 */
public interface IService extends Remote {

    String sayHello() throws RemoteException;
}
