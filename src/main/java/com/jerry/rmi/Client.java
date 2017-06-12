package com.jerry.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by jerrychien on 2017-06-12 15:30.
 */
// RMI客户端
public class Client {
    public static void main(String[] args) {
        // 注册管理器
        Registry registry = null;
        try {
            // 获取服务注册管理器
            registry = LocateRegistry.getRegistry("127.0.0.1", 6666);
            String[] list = registry.list();
            System.out.println(Arrays.toString(list));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            // 根据命名获取服务
            IService server = (IService) registry.lookup("vince");
            for (int i = 0; i < 10; i++) {
                // 调用远程方法
                String result = server.sayHello();
                // 输出调用结果
                System.out.println(result);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
