package cl.usach.ingesoft.agendator.controller;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.business.service.IAdministrationService;
import cl.usach.ingesoft.agendator.business.service.IProfessionalsService;
import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.business.validator.Validator;
import cl.usach.ingesoft.agendator.controller.base.BaseController;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.OngEntity;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import cl.usach.ingesoft.agendator.util.DateUtils;
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
import java.util.List;

@Controller
@RequestMapping("/professional")
@PreAuthorize("hasRole('ROLE_PROFESSIONAL')")
public class ProfessionalController extends BaseController {

    @Autowired private IProfessionalsService professionalsService;
    @Autowired private IUsersService usersService;
    @Autowired private IAdministrationService administrationService;

    @RequestMapping("/index.html")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // Find professional
        UserEntity professional = getUserFromSession(request.getSession());
        Validator.shouldBeFound(professional);

        // Find current ONG
        OngEntity ongEntity = administrationService.findCurrentOng();

        // Find Caresessions not yet finalized (pending sessions)
        List<CareSessionEntity> pendingCareSessions = administrationService.findPendingCareSessions(ongEntity.getId(),
                DateUtils.makeDate(2015, 1, 1));

        // Maybe find caresession for any parameter set for this URL
        int idCareSession = -1;
        try {
            idCareSession = Integer.parseInt(request.getParameter("idCareSession"));
        } catch (NumberFormatException nfe) {
            //nfe.printStackTrace();
        }
        CareSessionEntity cse = null;
        if (idCareSession > -1) {
            cse = administrationService.findCareSessionById(idCareSession);
        }
        // By default, if no parameter is set, use the first pending careSession
        if (cse == null) {
            if (pendingCareSessions.size() > 0) {
                cse = pendingCareSessions.get(0);
            } else {
                throw new IllegalStateException("Cannot add availability when admin has set no sessionCares yet!");
            }
        }

        // Get the already allocated schedules for that professional/careSession
        ProfessionalCalendarBO calendar = professionalsService.getProfessionalCalendar(professional.getId(), cse.getId());

        return newFrame(request)
                .selectMenu("professional")
                .selectContent("view/professional")
                .add("careSessions", pendingCareSessions)
                .add("careSession", cse)
                .add("calendar", calendar)
                .add("idCareSession", cse.getId())
                .build();
    }

    @RequestMapping(value = "/updateSchedule", method = RequestMethod.POST)
    public View updateUser(
            @RequestParam("idCareSession") int idCareSession,
            @RequestParam("allSchedules") String allSchedules,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            UserEntity user = getUserFromSession(request.getSession());
            Validator.shouldBeFound(user);

            CareSessionEntity cse = administrationService.findCareSessionById(idCareSession);
            Validator.shouldBeFound(cse);

            professionalsService.parseAndSaveSchedules(idCareSession, user.getId(), allSchedules);

            return defaultSuccessPage("index.html", "Disponibilidad actualizada!");
        } catch (Exception e) {
            e.printStackTrace();
            return defaultErrorPage(e);
        }
    }

}