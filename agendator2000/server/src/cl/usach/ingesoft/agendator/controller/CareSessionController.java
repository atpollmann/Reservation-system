package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.service.IAdministrationService;
import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.controller.base.BaseController;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.OngEntity;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/careSession")
@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
public class CareSessionController extends BaseController {

    @Autowired private IAdministrationService administrationService;

    @RequestMapping("/index.html")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        OngEntity ongEntity = administrationService.findCurrentOng();
        List<CareSessionEntity> careSessions = administrationService.findAllCareSessions(ongEntity.getId());

        return newFrame(request)
                .selectMenu("careSession")
                .add("careSessions", careSessions)
                .selectContent("view/careSession")
                .build();
    }

}