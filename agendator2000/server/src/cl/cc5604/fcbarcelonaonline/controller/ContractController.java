package cl.cc5604.fcbarcelonaonline.controller;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 22-06-13
 * Time: 02:20 AM
 * To change this template use File | Settings | File Templates.
 */

import cl.cc5604.fcbarcelonaonline.business.validator.Validator;
import cl.cc5604.fcbarcelonaonline.entity.ContractEntity;
import cl.cc5604.fcbarcelonaonline.facade.IContractAdministration;
import cl.cc5604.fcbarcelonaonline.util.FormatHelper;
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
@RequestMapping("/contract")
public class ContractController {

    private static Logger logger = Logger.getLogger(ContractController.class);

    @Autowired private IContractAdministration contractAdministration;

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return new ModelAndView("frame")
                .addObject("availableContract", contractAdministration.findAllContracts())
                .addObject("module", 3);
    }

    @RequestMapping(value = "/handlePost", method = RequestMethod.POST)
    public View handlePost(
            @RequestParam("id") String id,
            @RequestParam("initDate") String initDate,
            @RequestParam("expirationDate") String expirationDate,
            @RequestParam("monthlyPayment") String monthlyPayment,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            // necesarias? el BO ya las hace ...
            Validator.shouldNotBeEmpty(initDate);
            Validator.shouldNotBeEmpty(expirationDate);
            Validator.shouldNotBeEmpty(monthlyPayment);

            Validator.shouldBeFormattedDate(initDate);
            Validator.shouldBeFormattedDate(expirationDate);
            Validator.shouldBeMoney(monthlyPayment);
            // ..............................


            boolean shouldBeCreated = (id == null || "".equals(id));

            ContractEntity newContract = new ContractEntity();
            if (shouldBeCreated) {
                newContract.setId(null);
            } else {
                Validator.shouldBeNumeric(id);
                newContract.setId(Integer.parseInt(id));
            }
            newContract.setInitDate(FormatHelper.parseDate(initDate));
            newContract.setExpirationDate(FormatHelper.parseDate(expirationDate));
            newContract.setMonthlyPayment(FormatHelper.parseCurrency(monthlyPayment));

            if (shouldBeCreated) {
                contractAdministration.createContract(newContract);
                logger.info("new contract created (" + newContract + ")");
            } else {
                contractAdministration.updateContract(newContract);
                logger.info("user updated (" + newContract + ")");
            }

            logger.info("redirecting from contract/handlePost to user");

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

            int idContract = Integer.parseInt(id);

            contractAdministration.deleteContract(idContract);

            logger.info("deleted contract id:" + id);

            return "ok";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, " + e.getMessage();
        }
    }

    @RequestMapping(value = "/error.html", method = RequestMethod.GET)
    public ModelAndView error(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return new ModelAndView("frame").addObject("module", -1);
    }
}