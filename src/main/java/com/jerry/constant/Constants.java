package com.jerry.constant;

import com.jerry.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Constants
 *
 * @author jerrychien
 * @create 2016-09-13 23:06
 */
public final class Constants {

    private static final Logger logger = LoggerFactory.getLogger(Constants.class);

    public static final String jdbcDriver;

    public static final String jdbcUrl;

    private static final PropertiesUtil jdbcProperties = PropertiesUtil.newInstance("/jdbc.properties");

    static {
        jdbcDriver = jdbcProperties.getProperty("jdbc.driver", "JDBC_DRIVER");
        jdbcUrl = jdbcProperties.getProperty("jdbc.url", "JDBC_URL");
        logger.info("jdbcDriver:{},jdbcUrl:{}", jdbcDriver, jdbcUrl);
    }

}
