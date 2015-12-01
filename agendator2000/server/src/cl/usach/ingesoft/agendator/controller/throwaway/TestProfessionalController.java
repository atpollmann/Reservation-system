package cl.usach.ingesoft.agendator.controller.throwaway;

import cl.usach.ingesoft.agendator.business.bo.ProfessionalCalendarBO;
import cl.usach.ingesoft.agendator.business.service.IProfessionalsService;
import cl.usach.ingesoft.agendator.business.service.impl.ProfessionalsService;
import cl.usach.ingesoft.agendator.entity.AppointmentEntity;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/test_professional")
public class TestProfessionalController {

    @Autowired private IProfessionalsService professionalsService;

    @ResponseBody // OK
    @RequestMapping(value = "/get_calendar", produces = "text/plain", method = RequestMethod.GET)
    public String getCalendar(
            @RequestParam("professional") int professional,
            @RequestParam("care_session") int careSession,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            ProfessionalCalendarBO pc = professionalsService.getProfessionalCalendar(professional, careSession);
            if (pc == null) {
                return "null";
            }
            return pc.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/by_care_session", produces = "text/plain", method = RequestMethod.GET)
    public String getByCareSession(
            @RequestParam("care_session") int careSession,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            List<ProfessionalEntity> profs = professionalsService.findProfessionalsByCareSession(careSession);
            if (profs == null) {
                return "null";
            }
            StringBuilder sb = new StringBuilder();
            for (ProfessionalEntity pe : profs) {
                sb.append(pe.toString());
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
