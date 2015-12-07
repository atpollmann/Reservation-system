package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.controller.base.BaseController;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/professional")
public class ProfessionalController extends BaseController {

    private static final Logger logger = Logger.getLogger(ProfessionalController.class);

    @Autowired private IUsersService usersService;

    @RequestMapping("/index.html")
    public ModelAndView index() {
        logger.info("handling default method");

        return newFrame()
                .selectMenu("users")
                .selectContent("view/professional")
                .build();
    }

}