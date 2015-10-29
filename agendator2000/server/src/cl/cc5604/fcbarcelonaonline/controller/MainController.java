package cl.cc5604.fcbarcelonaonline.controller;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 22-06-13
 * Time: 02:20 AM
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/main")
public class MainController {

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return new ModelAndView("frame")
                .addObject("module", 0);
    }

}