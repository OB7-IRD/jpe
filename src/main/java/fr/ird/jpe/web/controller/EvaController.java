/*
 * Copyright (C) 2014 IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.jpe.web.controller;

import fr.ird.common.log.LogService;
import fr.ird.common.message.Flux;
import fr.ird.jpe.web.utils.WebUtils;
import fr.ird.jpe.web.controller.model.EvaJob;
import fr.ird.jpe.web.validator.EvaJobValidator;
import fr.ird.driver.eva.common.exception.EvaDriverException;
import fr.ird.eva.common.exception.EvaException;
import fr.ird.eva.core.service.TransferService;
import java.io.File;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 20 oct. 2014
 *
 */
@Controller
public class EvaController {

    public final static String EVA_URI = "/eva";
    public final static String EVA_RUN_URI = EVA_URI + "/run";

//  @Autowired
//  @Qualifier("evaJobValidator")
//  private Validator validator;
//
//  @ModelAttribute("evajob")
//  public EvaJob createEvaJobModel() {
//      // ModelAttribute value should be same as used in the empSave.jsp
//      return new EvaJob();
//  }
//
//    @Autowired
//    TransferService transferService;
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new EvaJobValidator());
    }

    @RequestMapping(
            value = EVA_RUN_URI,
            method = RequestMethod.POST
    )
    public String runEvA(@Valid
            @ModelAttribute("evajob") EvaJob evajob, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

//      System.out.println("RUN EVA --> EVA JOB :" + evajob);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.evajob", result);
            redirectAttributes.addFlashAttribute("evajob", evajob);

//          System.out.println("Result: nb error=" + result.getErrorCount());
//          System.out.println("Result: TN=" + evajob.getTripNumber());
//          System.out.println("Result: Model=");
//          for (String k : model.asMap().keySet()) {
//              System.out.println("-> " + k);
//          }
            return "redirect:" + LogbookController.TRIP_TRANSFER_URI;
        }

        MultipartFile msAccessFile = evajob.getMsAccessFile();
        String dbPath = null;
        TransferService transferService = TransferService.getService();

        try {
//            transferService.init();

            if ((msAccessFile != null) && !msAccessFile.isEmpty()) {
                String name = msAccessFile.getOriginalFilename();

                System.out.println(msAccessFile.getOriginalFilename() + " : Received file of size "
                        + msAccessFile.getSize() + " bytes");

                if (WebUtils.uploadFile(name, msAccessFile)) {
                    dbPath = transferService.initDB(WebUtils.TEMP_FILE_DIR + File.separator + name);
                } else {
                    System.out.println("You failed to upload...");
                }
            } else {
                dbPath = transferService.initDB();
            }

            Flux flux = transferService.executeTransfer(evajob.getTripNumber());

//          model.addAttribute("flux", flux);
        } catch (EvaException | EvaDriverException ex) {
            LogService.getService(EvaController.class).logApplicationError(ex.getMessage());
        }

        model.addAttribute("filePath", dbPath);

        return "eva/result";
    }
}
