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
import fr.ird.common.message.Message;
import fr.ird.jpe.web.utils.WebUtils;
import fr.ird.jpe.web.controller.model.EvaJob;
import fr.ird.jpe.web.validator.EvaJobValidator;
import fr.ird.driver.eva.exception.EvaDriverException;
import fr.ird.eva.common.exception.EvaException;
import fr.ird.eva.common.service.MessageService;
import fr.ird.eva.core.service.TransferService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    @InitBinder("filePath")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new EvaJobValidator());
    }

    @RequestMapping(
            value = EVA_RUN_URI,
            method = RequestMethod.POST
    )
    public String runEvA(@Valid
            @ModelAttribute("evajob") EvaJob evajob, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.evajob", result);
            redirectAttributes.addFlashAttribute("evajob", evajob);

            return "redirect:" + LogbookController.TRIP_TRANSFER_URI;
        }

        MultipartFile msAccessFile = evajob.getMsAccessFile();
        String dbPath = null;
        TransferService transferService = TransferService.getService();

        List<String> messages = new ArrayList<>();
        try {
            transferService.init();
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

            transferService.executeTransfer(evajob.getTripNumbers());
            Flux flux;
            for (String tn : evajob.getTripNumbers()) {
                flux = MessageService.getFlux(tn);
                for (Message m : flux.getMessages()) {
                    messages.add(m.displayMessage(Locale.FRANCE));
                }
                MessageService.detachFlux(tn, flux);
            }

        } catch (EvaException | EvaDriverException ex) {
            LogService.getService(EvaController.class).logApplicationError(ex.getMessage());
            messages.add(ex.getMessage());
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filePath", dbPath);

        return "eva/result";
    }
}
