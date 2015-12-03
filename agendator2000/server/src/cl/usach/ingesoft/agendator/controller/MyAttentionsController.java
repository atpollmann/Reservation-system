package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by rene on 03-12-15.
 */
@Controller
@RequestMapping("/my_attentions")
public class MyAttentionsController extends BaseController {

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return newFrame()
                .selectMenu("my_attentions")
                .selectContent("view/main")
                .build();
    }

}