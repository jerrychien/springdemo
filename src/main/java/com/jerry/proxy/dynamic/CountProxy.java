package com.jerry.proxy.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代理类(增强CountImpl实现)
 * 观察代码可以发现每一个代理类只能为一个接口服务，这样一来程序开发中必然会产生过多的代理，
 * 而且，所有的代理操作除了调用的方法不一样之外，其他的操作都一样，则此时肯定是重复代码。
 * 解决这一问题最好的做法是可以通过一个代理类完成全部的代理功能，那么此时就必须使用动态代理完成。
 *
 * @author jerrychien
 * @create 2016-09-14 11:09
 */
public class CountProxy implements Count {

    private static final Logger logger = LoggerFactory.getLogger(CountImpl.class);

    private CountImpl countImpl;

    public CountProxy(CountImpl countImpl) {
        this.countImpl = countImpl;
    }

    @Override
    public void queryCount() {
        logger.info("事务处理之前");
        // 调用委托类的方法;
        countImpl.queryCount();
        logger.info("事务处理之后");
    }

    @Override
    public void updateCount() {
        logger.info("事务处理之前");
        // 调用委托类的方法;
        countImpl.updateCount();
        logger.info("事务处理之后");
    }
}
