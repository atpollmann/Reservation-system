package cl.usach.ingesoft.agendator.controller.throwaway;

import cl.usach.ingesoft.agendator.business.service.IProfessionalsService;
import cl.usach.ingesoft.agendator.business.service.IUsersService;
import cl.usach.ingesoft.agendator.entity.AdministratorEntity;
import cl.usach.ingesoft.agendator.entity.PatientEntity;
import cl.usach.ingesoft.agendator.entity.ProfessionalEntity;
import cl.usach.ingesoft.agendator.entity.UserEntity;
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
@RequestMapping("/test_user")
public class TestUserController {

    @Autowired private IUsersService usersService;
    @Autowired private IProfessionalsService professionalsService;

    @ResponseBody // OK
    @RequestMapping(value = "/create_patient", produces = "text/plain", method = RequestMethod.GET)
    public String createPatient() {
        try {

            PatientEntity pe = new PatientEntity();
            pe.setRun(17770618);
            pe.setFirstName("Rene");
            pe.setLastName("Tapia");
            pe.setEmail("example@bar.com");
            pe.setHashedPassword("123456");
            usersService.createUser(pe);

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/create_administrator", produces = "text/plain", method = RequestMethod.GET)
    public String createAdministrator() {
        try {
            AdministratorEntity pe = new AdministratorEntity();
            pe.setRun(1250999);
            pe.setFirstName("Juan");
            pe.setLastName("Perez");
            pe.setEmail("jperez@perez.cl");
            pe.setHashedPassword("abcdef");
            pe.setType("root");
            usersService.createUser(pe);

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/create_professional", produces = "text/plain", method = RequestMethod.GET)
    public String createProfessional() {
        try {
            ProfessionalEntity pe = new ProfessionalEntity();
            pe.setRun(1250998);
            pe.setFirstName("Fernando");
            pe.setLastName("Fernandez");
            pe.setEmail("ff@fernandez.cl");
            pe.setHashedPassword("uvwxyz");
            pe.setSpeciality(professionalsService.findSpecialityById(1));
            usersService.createUser(pe);

            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/find_all", produces = "text/plain", method = RequestMethod.GET)
    public String findAll() {
        try {
            List<UserEntity> allUsers = usersService.findAllUsers();
            StringBuilder sb = new StringBuilder();
            for(UserEntity ue : allUsers) {
                sb.append(ue.toString() + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/delete", produces = "text/plain", method = RequestMethod.GET)
    public String delete(
            @RequestParam("id") int id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            usersService.deleteUser(id);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/update", produces = "text/plain", method = RequestMethod.GET)
    public String update(
            @RequestParam("id") int id,
            @RequestParam("last_name") String lastName,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            UserEntity user = usersService.findUser(id);
            user.setLastName(lastName);
            usersService.updateUser(user);

            return user.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @ResponseBody // OK
    @RequestMapping(value = "/find_by_email", produces = "text/plain", method = RequestMethod.GET)
    public String update(
            @RequestParam("email") String email,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            UserEntity user = usersService.findUserByEmail(email);
            return user.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
