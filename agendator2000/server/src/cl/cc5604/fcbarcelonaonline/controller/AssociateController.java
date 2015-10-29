package cl.cc5604.fcbarcelonaonline.controller;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 22-06-13
 * Time: 02:20 AM
 * To change this template use File | Settings | File Templates.
 */

import cl.cc5604.fcbarcelonaonline.business.validator.Validator;
import cl.cc5604.fcbarcelonaonline.entity.*;
import cl.cc5604.fcbarcelonaonline.facade.IAssociateAdministration;
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
@RequestMapping("/associate")
public class AssociateController {

    private static Logger logger = Logger.getLogger(AssociateController.class);

    @Autowired private IContractAdministration contractAdministration;
    @Autowired private IAssociateAdministration associateAdministration;

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return new ModelAndView("frame")
                .addObject("availableAssociate", associateAdministration.findAllAssociates())
                .addObject("availableContracts", contractAdministration.findAllAvailableContracts())
                .addObject("availableNationality", associateAdministration.findAllNationalities())
                .addObject("module", 2);
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
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("seatRight") String seatRight,

            @RequestParam("contract") String contractId,
            @RequestParam("nationality") String nationalityId,

            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {

            // validar parametros obligatorios minimos
            Validator.shouldNotBeEmpty(firstName);
            Validator.shouldNotBeEmpty(lastName);
            Validator.shouldNotBeEmpty(birthDate);
            Validator.shouldBeFormattedDate(birthDate);
            Validator.shouldNotBeEmpty(seatRight);
            Validator.shouldBeBooleanStr(seatRight);

            Validator.shouldNotBeEmpty(contractId); // contrato
            Validator.shouldBeNumeric(contractId);

            Validator.shouldNotBeEmpty(nationalityId); // nacionalidad
            Validator.shouldBeNumeric(nationalityId);

            boolean shouldBeCreated = (id == null || "".equals(id));

            AssociateEntity associate = new AssociateEntity();

            // colocar id
            if (shouldBeCreated) {
                associate.setId(null);
            } else {
                Validator.shouldBeNumeric(id);
                associate.setId(Integer.parseInt(id));
            }

            associate.setFirstName(firstName);
            associate.setLastName(lastName);
            associate.setBirthDate(FormatHelper.parseDate(birthDate));

            // colocar derecho asiento
            associate.setSeatRight("true".equals(seatRight));

            // verificar que exista contrato valido
            ContractEntity contract = contractAdministration.findById(Integer.parseInt(contractId));
            Validator.shouldBeFound(contract);
            associate.setContract(contract);

            // asociar nacionalidad valida
            NationalityEntity nationality = associateAdministration.findNationalityById(Integer.parseInt(nationalityId));
            Validator.shouldBeFound(nationality);
            associate.setNationality(nationality);

            if (shouldBeCreated) {
                associateAdministration.createAssociate(associate);
                logger.info("new associate created (" + associate + ")");
            } else {
                associateAdministration.updateAssociate(associate);
                logger.info("associate updated (" + associate + ")");
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RedirectView("error.html");
        }

        return new RedirectView("index.html");
    }

    @RequestMapping(value = "/handleDelete", method = RequestMethod.POST, produces = "text/plain")
    public
    @ResponseBody
    String handleDelete(
            @RequestParam("id") String id, // id de AssociateEntity
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            Validator.shouldNotBeEmpty(id);
            Validator.shouldBeNumeric(id);

            int idAssociate = Integer.parseInt(id);
            associateAdministration.deleteAssociate(idAssociate);

            logger.info("delete associate id:" + id);

            return "ok";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, " + e.getMessage();
        }
    }

    // ------------------------------ contact data ------------------------------

    @RequestMapping("/contactData.html")
    public ModelAndView contactData(
            @RequestParam("associateId") String idAssociate,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try{
            logger.info("contact data for idAssociate :"+idAssociate);

            Validator.shouldNotBeEmpty(idAssociate);
            Validator.shouldBeNumeric(idAssociate);

            AssociateEntity associate = associateAdministration.findById(Integer.parseInt(idAssociate));
            Validator.shouldBeFound(associate);

            return new ModelAndView("frame")
                    .addObject("associate", associate)
                    .addObject("availableContactType", associateAdministration.findAllContactTypes())
                    .addObject("availableContactData", associateAdministration.findAllContactDataByAssociate(associate.getId()))
                    .addObject("module", 8);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return new ModelAndView("frame").addObject("module", -1);
        }
    }


    @RequestMapping(value = "/handlePostContactData", method = RequestMethod.POST)
    public View handlePostContactData(
            @RequestParam("associateId") String associateId,
            @RequestParam("contactType") String contactTypeId,
            @RequestParam("valueData") String valueData,

            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try{
            AssociateEntity associate;
            ContactTypeEntity contactType;

            Validator.shouldNotBeEmpty(associateId);
            Validator.shouldBeNumeric(associateId);
            Validator.shouldBeFound(associate = associateAdministration.findById(Integer.parseInt(associateId)));
            Validator.shouldNotBeEmpty(contactTypeId);
            Validator.shouldBeNumeric(contactTypeId);
            Validator.shouldBeFound(contactType = associateAdministration.findContactTypeById(Integer.parseInt(contactTypeId)));
            Validator.shouldNotBeEmpty(valueData);

            if(contactType.getId() == 2){
                Validator.shouldBePhoneNumber(valueData);
            }else if(contactType.getId() == 3){
                Validator.shouldBeEmail(valueData);
            }

            ContactDataEntity data = new ContactDataEntity();
            data.setId(null);
            data.setContactType(contactType);
            data.setAssociate(associate);
            data.setValueData(valueData);

            associateAdministration.createContactData(data);

            logger.info("contact data created ("+data+")");

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return new RedirectView("error.html");
        }

        return new RedirectView("contactData.html?associateId=" + associateId);
    }

    @RequestMapping(value = "/handleDeleteContactData", method = RequestMethod.POST, produces = "text/plain")
    public
    @ResponseBody
    String handleDeleteContactData(
            @RequestParam("id") String id, // id ContacDataEntity
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            Validator.shouldNotBeEmpty(id);
            Validator.shouldBeNumeric(id);

            associateAdministration.deleteContactData(Integer.parseInt(id));

            logger.info("deleted contactData id:" + id);

            return "ok";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, " + e.getMessage();
        }
    }
}