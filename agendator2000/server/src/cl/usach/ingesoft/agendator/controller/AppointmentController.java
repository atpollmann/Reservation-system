package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.service.IAppointmentsService;
import cl.usach.ingesoft.agendator.business.service.impl.AppointmentsService;
import cl.usach.ingesoft.agendator.controller.base.BaseController;
import cl.usach.ingesoft.agendator.entity.AppointmentEntity;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import cl.usach.ingesoft.agendator.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/appointment")
public class AppointmentController extends BaseController {

    @Autowired private IAppointmentsService appointmentsService;

    @RequestMapping("/index.html")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UserEntity loggedUser = getUserFromSession(request.getSession());
        int id = loggedUser.getId();

        List<AppointmentEntity> appointments = new ArrayList<AppointmentEntity>();

        // TODO: Cambiar por la hora actual!
        if (loggedUser.getIsProfessional()) {
            appointments = appointmentsService.findByProfessional(id);
        } else if (loggedUser.getIsPatient()) {
            appointments = appointmentsService.findByPatient(id);
        } else if (loggedUser.getIsAdministrator()) {
            appointments = appointmentsService.findByAdministrator();
        }

        return newFrame(request)
                .selectMenu("appointment")
                .add("appointments", appointments)
                .selectContent("view/appointment")
                .build();
    }

    @RequestMapping("/cancelAppointment")
    public View cancelAppointment(
            @RequestParam("id") int id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            UserEntity currentUser = getUserFromSession(request.getSession());
            appointmentsService.cancelAppointment(currentUser.getId(), id);

            return defaultSuccessPage("index.html", "Hora cancelada!");
        } catch (Exception e) {
            return defaultErrorPage(e);
        }
    }

}