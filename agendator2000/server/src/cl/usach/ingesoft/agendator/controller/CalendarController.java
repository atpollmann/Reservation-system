package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.business.service.IAppointmentsService;
import cl.usach.ingesoft.agendator.business.service.IProfessionalsService;
import cl.usach.ingesoft.agendator.business.service.impl.ProfessionalsService;
import cl.usach.ingesoft.agendator.business.validator.Validator;
import cl.usach.ingesoft.agendator.controller.base.BaseController;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/calendar")
@PreAuthorize("hasRole('ROLE_PATIENT') or hasRole('ROLE_PROFESSIONAL')")
public class CalendarController extends BaseController {

    @Autowired private IProfessionalsService professionalsService;
    @Autowired private IAppointmentsService appointmentsService;

    @RequestMapping("/index.html")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UserEntity currentUser = getUserFromSession(request.getSession());
        if (currentUser.getIsProfessional()) {
            return handleProfessional(currentUser, request, response);
        } else {
            return handlePatient(currentUser, request, response);
        }
    }

    private ModelAndView handleProfessional(
            UserEntity currentUser,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        ProfessionalCalendarBO calendar = professionalsService.getProfessionalCalendar(currentUser.getId());
        return newFrame(request)
                .selectMenu("calendar")
                .add("calendar", calendar)
                .selectContent("view/calendarProfessional")
                .build();
    }

    private ModelAndView handlePatient(
            UserEntity currentUser,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return newFrame(request)
                .selectMenu("calendar")
                .selectContent("view/calendarPatient")
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