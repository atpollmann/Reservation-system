package cl.usach.ingesoft.agendator.controller.throwaway;

import cl.usach.ingesoft.agendator.business.service.IAdministrationService;
import cl.usach.ingesoft.agendator.business.service.IAppointmentsService;
import cl.usach.ingesoft.agendator.entity.AppointmentEntity;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.OngEntity;
import cl.usach.ingesoft.agendator.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/test_appointment")
public class TestAppointmentController {

    @Autowired private IAppointmentsService appointmentsService;

    private Date makeDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day, hour, minute, second);
        return c.getTime();
    }

    @ResponseBody // OK
    @RequestMapping(value = "/book", produces = "text/plain", method = RequestMethod.GET)
    public String update(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            AppointmentEntity ae = new AppointmentEntity();
            // depende fuertemente de ProfessionalsService

            return ae.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
