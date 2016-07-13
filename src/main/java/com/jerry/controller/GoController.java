package com.jerry.controller;

import com.jerry.manager.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * GoController
 *
 * @author jerrychien
 * @create 2016-06-14 23:29
 * @desp
 * @SessionAttributes("mySession") 属性
 * <p/>
 * 在本controller中有效
 * <p/>
 * 在默认情况下，ModelMap 中的属性作用域是 request 级别是，
 * 也就是说，当本次请求结束后，ModelMap 中的属性将销毁。如果希望
 * 在多个请求中共享 ModelMap 中的属性，必须将其属性转存到 session 中，
 * 这样 ModelMap 的属性才可以被跨请求访问。
 * <p/>
 * 可以在对应的 JSP 视图页面中通过 request.getAttribute(“mySession”) 和
 * session.getAttribute(“mySession”) 获取 mySession 对象，
 * @SessionAttributes 允许指定多个属性。你可以通过字符串数组的方式指定多个属性，
 * 如 @SessionAttributes({“attr1”,”attr2”})。此外，@SessionAttributes
 * 还可以通过属性类型指定要 session 化的 ModelMap 属性，
 * 如 @SessionAttributes(types = User.class)，当然也可以指定多个类，
 * 如 @SessionAttributes(types = {User.class,Dept.class})，
 * 还可以联合使用属性名和属性类型指定：@SessionAttributes(types = {User.class,Dept.class},value={“attr1”,”attr2”})。
 */
@Controller
@SessionAttributes({"mySession", "articleId"})
public class GoController implements EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(GoController.class);

    @Autowired
    private UserManager userManager;

    /**
     * 在此controller中方法执行前调用
     *
     * @param name
     * @return
     */
    @ModelAttribute("comment")
    public String replaceName(String name) {
        logger.info("start repalceName, comment: {}", name);
        if (StringUtils.isNotBlank(name)) {
            name = name.replaceAll("shit", "");
        }
        return name;
    }

    @RequestMapping("/article/{articleId}/comment")
    public String comment(@PathVariable String articleId, @ModelAttribute("comment") String comment, RedirectAttributes redirectAttributes,
                          ModelMap model) {
        logger.info("comment:{}", comment);
        logger.info("articleId:{}", articleId);
        //redirect时候传递参数
        redirectAttributes.addFlashAttribute("comment", comment);
        //sessionAttribute session保存参数,最初想使用sessionAttribute属性,但是此属性在redirect中不生效
        //model.addAttribute("articleId", articleId);
        //只能使用这个属性
        redirectAttributes.addFlashAttribute("articleId", articleId);
        return "redirect:/showArticle";
    }

    @RequestMapping(value = "/showArticle", method = RequestMethod.GET)
    public String showArticle(Model model, SessionStatus sessionStatus) {
        String articleId = (String) model.asMap().get("articleId");
        logger.info("articleId: {}", articleId);
        model.addAttribute("articleTitle", articleId + "号文章标题");
        model.addAttribute("article", articleId + "号文章内容");
        model.addAttribute("comment", model.asMap().get("comment"));
        sessionStatus.setComplete();
        return "article";
    }

    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    public String head() {
        return "go";
    }

    @RequestMapping("/login")
    public String login(ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("action", request.getRequestURI());
        modelMap.addAttribute("mySession", request.getRequestURI() + ":" +
                DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss SSS"));
        return "login";
    }

    @RequestMapping("/login1")
    public String login1(ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("action", request.getRequestURI());
        return "login";
    }


    @RequestMapping("/loginOut")
    public String loginOut() {
        return "loginOut";
    }


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        logger.info("let's go go go !!!");
//        slf4j中占位符的使用
        logger.info("hi, {} is speaking {}", "jerryChien", "\"what the shit!\"");
        userManager.doPrint();
        model.addAttribute("msg", "index, index page");
        return "go";
    }

    private Environment environment = null;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
        StandardServletEnvironment standardServletEnvironment = (StandardServletEnvironment) environment;
        PropertySource propertySource = standardServletEnvironment.getPropertySources().get("systemProperties");
        Map<String, String> map = (Map<String, String>) propertySource.getSource();
        logger.info("map:" + map);
        logger.info("environment:" + environment);
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            logger.info(entry.getKey() + ":" + entry.getValue());
        }

    }
}
