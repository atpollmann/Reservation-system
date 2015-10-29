package cl.cc5604.fcbarcelonaonline.controller;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 22-06-13
 * Time: 02:20 AM
 * To change this template use File | Settings | File Templates.
 */

import cl.cc5604.fcbarcelonaonline.business.validator.Validator;
import cl.cc5604.fcbarcelonaonline.entity.ActiveEntity;
import cl.cc5604.fcbarcelonaonline.entity.ActiveTypeEntity;
import cl.cc5604.fcbarcelonaonline.facade.IActiveAdministration;
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
@RequestMapping("/active")
public class ActiveController {

    private static Logger logger = Logger.getLogger(ActiveController.class);

    @Autowired private IActiveAdministration activeAdministration;

    @RequestMapping("/index.html")
    public ModelAndView index() {

        logger.info("handling default method");

        return new ModelAndView("frame")
                .addObject("module", 1)
                .addObject("availableActive", activeAdministration.findAllActives())
                .addObject("availableActiveType", activeAdministration.findAllActiveTypes());
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
            @RequestParam("type") String idActiveType,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        logger.info("creating active");

        try {
            // necesarias? el BO ya las hace ...
            Validator.shouldBeNumeric(value);
            Validator.shouldBeNumeric(idActiveType);

            int valueInt = Integer.parseInt(value);
            int idActiveTypeInt = Integer.parseInt(idActiveType);

            Validator.shouldNotBeNegative(valueInt);

            // ..............................

            // create
            if (id == null || "".equals(id)) {

                ActiveEntity newActive = new ActiveEntity();
                newActive.setId(null);
                newActive.setValue(valueInt);
                ActiveTypeEntity activeTypeEntity = new ActiveTypeEntity();
                activeTypeEntity.setId(idActiveTypeInt);
                newActive.setActiveType(activeTypeEntity);
                activeAdministration.createActive(newActive);
                logger.info("new active created (id=" + newActive.getId());
            }
            // update
            else {
                Validator.shouldBeNumeric(id);

                int idActive = Integer.parseInt(id);

                ActiveEntity oldActive = new ActiveEntity();
                oldActive.setId(idActive);
                oldActive.setValue(valueInt);
                ActiveTypeEntity activeTypeEntity = new ActiveTypeEntity();
                activeTypeEntity.setId(idActiveTypeInt);
                oldActive.setActiveType(activeTypeEntity);
                activeAdministration.updateActive(oldActive);

                logger.info("active updated (id=" + oldActive.getId());
            }

            logger.info("redirecting from active/handlePost to active");

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

            int idActive = Integer.parseInt(id);

            activeAdministration.deleteActive(idActive);

            logger.info("deleted active id:" + id);

            return "ok";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, " + e.getMessage();
        }
    }

}