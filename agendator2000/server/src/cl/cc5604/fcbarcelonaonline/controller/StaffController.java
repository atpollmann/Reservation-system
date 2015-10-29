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
import cl.cc5604.fcbarcelonaonline.entity.NationalityEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffEntity;
import cl.cc5604.fcbarcelonaonline.entity.StaffTypeEntity;
import cl.cc5604.fcbarcelonaonline.facade.IContractAdministration;
import cl.cc5604.fcbarcelonaonline.facade.IStaffAdministration;
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
@RequestMapping("/staff")
public class StaffController {

    private static Logger logger = Logger.getLogger(StaffController.class);

    @Autowired private IStaffAdministration staffAdministration;
    @Autowired private IContractAdministration contractAdministration;

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return new ModelAndView("frame")
                .addObject("availableStaff", staffAdministration.findAllStaffs())
                .addObject("availableStaffType", staffAdministration.findAllStaffTypes())
                .addObject("availableNationality", staffAdministration.findAllNationalities())
                .addObject("availableContracts", contractAdministration.findAllAvailableContracts())
                .addObject("module", 6);
    }

    @RequestMapping(value = "/handlePost", method = RequestMethod.POST)
    public View handlePost(
            @RequestParam("id") String id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("hired") String hired,
            @RequestParam("staffType") String staffTypeId,
            @RequestParam("nationality") String nationalityId,

            @RequestParam(value = "contract", required = false) String contractId, // validar solo si es requerido
            @RequestParam(value = "baseValue", required = false) String baseValue,

            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {

            // validar parametros obligatorios minimos
            Validator.shouldNotBeEmpty(firstName); // nombre
            Validator.shouldNotBeEmpty(lastName); // apellido
            Validator.shouldNotBeEmpty(birthDate); // fecha nacimiento
            Validator.shouldBeFormattedDate(birthDate);
            Validator.shouldNotBeEmpty(hired); // contratado
            Validator.shouldBeBooleanStr(hired);

            Validator.shouldNotBeEmpty(staffTypeId); // tipo personal
            Validator.shouldBeNumeric(staffTypeId);

            Validator.shouldNotBeEmpty(nationalityId); // nacionalidad
            Validator.shouldBeNumeric(nationalityId);

            Validator.shouldNotBeEmpty(contractId); // contrato
            Validator.shouldBeNumeric(contractId);

            boolean shouldBeCreated = (id == null || "".equals(id));

            StaffEntity staff = new StaffEntity();

            // colocar id
            if (shouldBeCreated) {
                staff.setId(null);
            } else {
                Validator.shouldBeNumeric(id);
                staff.setId(Integer.parseInt(id));
            }


            staff.setFirstName(firstName);
            staff.setLastName(lastName);
            staff.setBirthDate(FormatHelper.parseDate(birthDate));

            // colocar hired
            staff.setHired("true".equals(hired));

            // TODO: verificar que se setee el valor base, solo cuando el personal es de tipo jugador

            // colocar valor base, si corresponde
            if (baseValue == null || "".equals(baseValue)) {
                staff.setBaseValue(0);
            } else {
                Validator.shouldBeNumeric(baseValue);
                int moneyVal = Integer.parseInt(baseValue);
                Validator.shouldNotBeNegative(moneyVal);
                staff.setBaseValue(moneyVal);
            }

            // verificar que exista contrato valido
            ContractEntity contract = contractAdministration.findById(Integer.parseInt(contractId));
            Validator.shouldBeFound(contract);
            staff.setContract(contract); // null or not, depending if hired

            // asociar nacionalidad valida
            NationalityEntity nationality = staffAdministration.findNationalityById(Integer.parseInt(nationalityId));
            Validator.shouldBeFound(nationality);
            staff.setNationality(nationality);

            // asociar tipo de personal valido
            StaffTypeEntity staffType = staffAdministration.findStaffTypeById(Integer.parseInt(staffTypeId));
            Validator.shouldBeFound(staffType);
            staff.setStaffType(staffType);

            if (shouldBeCreated) {
                staffAdministration.createStaff(staff);
                logger.info("new staff created (" + staff + ")");
            } else {
                staffAdministration.updateStaff(staff);
                logger.info("staff updated (" + staff + ")");
            }

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

            Validator.shouldNotBeEmpty(id);
            Validator.shouldBeNumeric(id);

            int idStaff = Integer.parseInt(id);
            staffAdministration.deleteStaff(idStaff);

            logger.info("delete staff id:" + id);

            return "ok";
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "error, " + e.getMessage();
        }
    }

}