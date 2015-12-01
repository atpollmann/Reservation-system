package cl.usach.ingesoft.agendator.controller.throwaway;

import cl.usach.ingesoft.agendator.business.service.IAdministrationService;
import cl.usach.ingesoft.agendator.entity.CareSessionEntity;
import cl.usach.ingesoft.agendator.entity.OngEntity;
import cl.usach.ingesoft.agendator.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/test_administration")
public class TestAdministrationController {

    @Autowired private IAdministrationService administrationService;

    @ResponseBody // OK
    @RequestMapping(value = "/create_care_session", produces = "text/plain", method = RequestMethod.GET)
    public String createCareSession() {
        try {
            OngEntity ong = administrationService.findCurrentOng();

            CareSessionEntity cse = new CareSessionEntity();
            cse.setOng(ong);
            cse.setValid(true);
            cse.setStartDate(DateUtils.makeDate(2015, 3, 1, 8, 0, 0)); // 08:00:00 de 01/03/2015
            cse.setEndDate(DateUtils.makeDate(2015, 3, 5, 18, 0, 0));  // 18:00:00 de 05/03/2015
            cse.setLocation("Junta de vecinos #1");

            administrationService.createCareSession(cse);

            return cse.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/cancel_care_session", produces = "text/plain", method = RequestMethod.GET)
    public String cancelCareSession(
            @RequestParam("id") int id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            return String.valueOf(administrationService.cancelCareSession(id));
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/update_care_session", produces = "text/plain", method = RequestMethod.GET)
    public String updateCareSession(
            @RequestParam("id") int id,
            @RequestParam("new_location") String newLocation,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            CareSessionEntity cse = administrationService.findCareSessionById(id);
            cse.setLocation(newLocation);
            administrationService.updateCareSession(cse);
            return cse.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/find_current_ong", produces = "text/plain", method = RequestMethod.GET)
    public String findCurrentOng(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            OngEntity ong = administrationService.findCurrentOng();
            return ong.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * http://localhost:8080/agendator/app/test_administration/find_current_care_session?year=2015&month=3&day=3&hour=0&minute=0&second=0
     */
    @ResponseBody // OK
    @RequestMapping(value = "/find_current_care_session", produces = "text/plain", method = RequestMethod.GET)
    public String findCurrentCareSession(
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @RequestParam("day") int day,
            @RequestParam("hour") int hour,
            @RequestParam("minute") int minute,
            @RequestParam("second") int second,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // cualquier fecha en el rango start/end de la fecha creada en createCareSession deberia funcionar.
            OngEntity ong = administrationService.findCurrentOng();
            CareSessionEntity cse = administrationService.findCurrentCareSession(ong,
                    DateUtils.makeDate(year, month, day, hour, minute, second));
            return String.valueOf(cse);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/find_by_id", produces = "text/plain", method = RequestMethod.GET)
    public String findById(
            @RequestParam("id") int id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            CareSessionEntity cse = administrationService.findCareSessionById(id);
            return String.valueOf(cse);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
