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

import fr.ird.jpe.web.common.Activity;
import fr.ird.jpe.web.controller.model.EvaJob;
import fr.ird.jpe.web.controller.model.MapTripJob;
import fr.ird.jpe.web.controller.model.ShowTripJob;
import fr.ird.driver.eva.business.Trip;
import fr.ird.eva.core.service.EvaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 8 oct. 2014
 *
 */
@Controller
public class LogbookController {

    public List<Activity> getActivities() {
        ArrayList<Activity> actions = new ArrayList();
        actions.add(new Activity("label.trip.all", Activity.LISTING, TRIP_LIST_URI));
        return actions;
    }

    public final static String TRIP_URI = "/trip";
    public final static String TRIP_LIST_URI = TRIP_URI + "/list";
    public final static String TRIP_TRANSFER_URI = TRIP_URI + "/transfer";
    public final static String TRIP_SHOW_URI = TRIP_URI + "/show";
    private List<Trip> allTrips;

    @Autowired
    private MessageSource source;
//    @Autowired
//    private EvaService evaService;

    @RequestMapping(
            value = {"/", TRIP_URI},
            method = RequestMethod.GET
    )
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("trip/index");
        model.addObject("actions", getActivities());
        return model;
    }

    @RequestMapping(
            value = {TRIP_LIST_URI},
            method = RequestMethod.GET
    )
    public ModelAndView list() {
        ModelAndView model = new ModelAndView("trip/list");
        model.addObject("actions", getActivities());
        
        allTrips = EvaService.getService().findAllTrips();
        model.addObject("trips", allTrips);

        return model;
    }

    @RequestMapping(
            value = TRIP_TRANSFER_URI,
            method = RequestMethod.GET
    )
    public String transfer(@RequestParam(
            value = "tripNumber[]",
            required = false
    ) List<String> tripNumber, Model model, HttpServletRequest request) {
        model.addAttribute("actions", getActivities());
        List<String> tripNumbers = tripNumber;
        EvaJob evaJob;

        if (!model.containsAttribute("evajob")) {
            evaJob = new EvaJob();
            evaJob.setTripNumbers(tripNumbers);
        } else {
            evaJob = (EvaJob) model.asMap().get("evajob");
            tripNumbers = evaJob.getTripNumbers();
        }
        List trips = new ArrayList<>();
        for (String tn : tripNumbers) {
            trips.add(EvaService.getService().findTrip(tn));
        }

        model.addAttribute("evajob", evaJob);
        model.addAttribute("trips", trips);

        return "trip/transfer";
    }

    @RequestMapping(
            value = TRIP_SHOW_URI,
            method = RequestMethod.GET
    )
    public ModelAndView show(@RequestParam(
            value = "tripNumber",
            required = true
    ) String tn, Locale locale) {
        ModelAndView model = new ModelAndView("trip/show");
        Trip trip = EvaService.getService().findFullTrip(tn);
        List<Activity> actions = getActivities();
        actions.add(new Activity("label.action.transfer", Activity.EXECUTE, TRIP_TRANSFER_URI + "?tripNumber[]=" + trip.getTripNumber()));
        model.addObject("actions", actions);
        model.addObject("trip", trip);
        model.addObject("job", new ShowTripJob(source, locale, trip));
        model.addObject("map", new MapTripJob(source, locale, trip));

        return model;
    }
}
