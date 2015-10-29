package cl.cc5604.fcbarcelonaonline.controller;

/**
 * Created with IntelliJ IDEA.
 * User: rene
 * Date: 22-06-13
 * Time: 02:20 AM
 * To change this template use File | Settings | File Templates.
 */

import cl.cc5604.fcbarcelonaonline.facade.IFinancesReport;
import cl.cc5604.fcbarcelonaonline.util.FormatHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/finance")
public class FinanceController {

    private static Logger logger = Logger.getLogger(FinanceController.class);

    @Autowired private IFinancesReport financesReport;

    @RequestMapping("/index.html")
    public ModelAndView index() {
        logger.info("index.html in FinanceController");
        return new ModelAndView("frame")
                .addObject("module", 4)
                .addObject("totalActives", FormatHelper.formatCurrency(financesReport.getTotalActives2()))
                .addObject("totalPassives", FormatHelper.formatCurrency(financesReport.getTotalPassives()))
                .addObject("totalNet", FormatHelper.formatCurrency(financesReport.getTotalNet()));
    }
}