package com.jerry.controller;

import com.jerry.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * GoController
 *
 * @author jerrychien
 * @create 2016-06-14 23:29
 */
@Controller
public class GoController implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(GoController.class);

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    public String head() {
        return "go";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        logger.info("let's go go go !!!");
        userManager.doPrint();
        model.addAttribute("msg", "index, index page");
        return "go";
    }

    private Environment environment = null;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
