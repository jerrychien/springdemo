package com.jerry.controller;

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

    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    public String head() {
        return "go";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("msg", "go go go");
        return "go";
    }

    private Environment environment = null;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
