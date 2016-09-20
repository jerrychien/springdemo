package com.jerry.proxy.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Count实现类
 *
 * @author jerrychien
 * @create 2016-09-14 11:07
 */
public class CountImpl implements Count {

    private static final Logger logger = LoggerFactory.getLogger(CountImpl.class);

    @Override
    public void queryCount() {
        logger.info("查看账户方法……");
    }

    @Override
    public void updateCount() {
        logger.info("修改账户方法……");
    }
}
