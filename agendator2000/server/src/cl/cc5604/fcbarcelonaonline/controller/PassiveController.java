package cl.cc5604.fcbarcelonaonline.controller;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 22-06-13
 * Time: 02:20 AM
 * To change this template use File | Settings | File Templates.
 */

import cl.cc5604.fcbarcelonaonline.business.validator.Validator;
import cl.cc5604.fcbarcelonaonline.entity.PassiveEntity;
import cl.cc5604.fcbarcelonaonline.entity.PassiveStatusEntity;
import cl.cc5604.fcbarcelonaonline.facade.IPassiveAdministration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/passive")
public class PassiveController {

    private static Logger logger = Logger.getLogger(PassiveController.class);

    @Autowired private IPassiveAdministration passiveAdministration;

    @RequestMapping("/index.html")
    public ModelAndView index() {
        logger.info("handling default method");

        return new ModelAndView("frame")
                .addObject("module", 5)
                .addObject("availablePassive", passiveAdministration.findAllPassives())
                .addObject("availablePassiveStatus", passiveAdministration.findAllPassiveStatus());
    }

    @RequestMapping(value = "/error.html", method = RequestMethod.GET)
    public ModelAndView error(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return new ModelAndView("frame").addObject("module", -1);
    }

    @RequestMapping(value = "/handlePost", method = RequestMethod.POST)
    public View handlePost(
            @RequestParam("id") String id,
            @RequestParam("value") String value,
            @RequestParam("status") String idStatus,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        logger.info("creating passive");

        try {
            // necesarias? el BO ya las hace ...
            Validator.shouldBeNumeric(value);
            Validator.shouldBeNumeric(idStatus);

            int valueInt = Integer.parseInt(value);
            int idStatusInt = Integer.parseInt(idStatus);

            Validator.shouldNotBeNegative(valueInt);

            // ..............................

            // create
            if (id == null || "".equals(id)) {

                PassiveEntity newPassive = new PassiveEntity();
                newPassive.setId(null);
                newPassive.setValue(valueInt);
                PassiveStatusEntity passiveStatus = new PassiveStatusEntity();
                passiveStatus.setId(idStatusInt);
                newPassive.setPassiveStatus(passiveStatus);
                passiveAdministration.createPassive(newPassive);
                logger.info("new active created (id=" + newPassive.getId());
            }
            // update
            else {
                Validator.shouldBeNumeric(id);

                int idPassive = Integer.parseInt(id);

                PassiveEntity oldPassive = new PassiveEntity();
                oldPassive.setId(idPassive);
                oldPassive.setValue(valueInt);
                PassiveStatusEntity passiveStatus = new PassiveStatusEntity();
                passiveStatus.setId(idStatusInt);
                oldPassive.setPassiveStatus(passiveStatus);
                passiveAdministration.updatePassive(oldPassive);

                logger.info("active updated (id=" + oldPassive.getId());
            }

            logger.info("redirecting from passive/handlePost to passive");

            return new RedirectView("index.html");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RedirectView("error.html");
        }
    }

    @RequestMapping(value = "/handleDelete", method = RequestMethod.POST, produces = "text/plain")
    public
    @ResponseBody
    String handleDelete(
            @RequestParam("id") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // necesarias? el BO ya las hace ...
            Validator.shouldNotBeEmpty(id);
            Validator.shouldBeNumeric(id);
            // ..............................

            int idPassive = Integer.parseInt(id);

            passiveAdministration.deletePassive(idPassive);

            logger.info("deleted active id:" + id);

            return "ok";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, " + e.getMessage();
        }
    }

}