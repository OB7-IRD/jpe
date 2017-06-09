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
package fr.ird.jpe.web.controller.model;

import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import fr.ird.common.Utils;
import fr.ird.common.log.LogService;
import fr.ird.jpe.web.common.Tabular;
import fr.ird.jpe.web.utils.ChartsUtils;
import fr.ird.jpe.web.utils.ColorUtils;
import fr.ird.jpe.web.utils.DateUtils;
import fr.ird.driver.eva.business.Capture;
import fr.ird.driver.eva.business.FishingActivity;
import fr.ird.driver.eva.business.FishingEvent;
import fr.ird.driver.eva.business.Trip;
import fr.ird.highcharts.AbstractChart;
import fr.ird.highcharts.Donut;
import fr.ird.highcharts.Pie;
import fr.ird.highcharts.StackedAndGroupedColumn;
import fr.ird.highcharts.donut.InsideData;
import fr.ird.highcharts.donut.OutsideData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.context.MessageSource;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 28 oct. 2014
 *
 */
public class ShowTripJob extends AbstractShowJob {

    private AbstractChart speciesDistributionChart;
    private Tabular speciesDistributionTable;
    private AbstractChart speciesByCatchChart;

    public ShowTripJob(MessageSource source, Locale locale, Trip trip) {
        super(source, locale);
        createSpeciesDistribution(trip);
        createSpeciesByCatch(trip);

    }

    public String[] getCharts() {
        return new String[]{ChartsUtils.stringify(speciesDistributionChart),
            ChartsUtils.stringify(speciesByCatchChart)};
    }

    public Tabular getSpeciesDistributionTable() {
        return speciesDistributionTable;
    }

    public AbstractChart getSpeciesDistributionChart() {
        return speciesDistributionChart;
    }

    public AbstractChart getSpeciesByCatchChart() {
        return speciesByCatchChart;
    }

    private void createSpeciesByCatchWithoutSizeComposition(Trip trip) {
        List categories = new ArrayList<>();
        List data = new ArrayList<>();
        Map map = new TreeMap<String, List>();
        String name;

        for (FishingEvent fe : trip.getFishingEvents()) {
            categories.add(DateUtils.format(fe.getDateOfFishingEvent()));

            if (fe instanceof FishingActivity) {
                FishingActivity fa = (FishingActivity) fe;

//              System.out.println("FA " + fa);
                String key;
                List tmp = new ArrayList<>();

//              for (Capture c : trip.getElementaryCapturesFrom(trip.getFishingEvents())) {
                for (Capture c : fa.getElementaryCaptures()) {

//                  System.out.println("EC " + c);
                    key = c.getSpecie().getNameOfSpecies();

                    if (map.containsKey(key)) {
                        tmp = (List) map.get(key);

                        if (tmp == null) {
                            tmp = new ArrayList<>();
                        }

                        tmp.add(c.getSpecie().getWeightOfFish());

//                      System.out.println("KEY " + key + " -- " + c.getSpecie().getWeightOfFish());
                    } else {
                        tmp = new ArrayList<>();
                        tmp.add(c.getSpecie().getWeightOfFish());
                    }

                    map.put(key, tmp);
                }
            }
        }

        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, List> entry = (Map.Entry<String, List>) it.next();

            name = entry.getKey();
            data.add(
                    new SimpleSeries().setStack(name).setName(name).setData(entry.getValue()).setColor(
                            ColorUtils.getColorFromSpecie(name)));
        }

        speciesByCatchChart = new StackedAndGroupedColumn("speciesByCatchChart",
                getMessage("title.main.species.bycatch.chart"), getMessage("title.xaxis.species.bycatch.chart"),
                getMessage("title.yaxis.species.bycatch.chart"), categories, data);
    }

    private void createSpeciesByCatch(Trip trip) {
        boolean hasSizeCompositionInformation = false;

        for (Capture c : trip.getElementaryCapturesFrom(trip.getFishingEvents())) {
            hasSizeCompositionInformation |= "".equals(c.getSpecie().getSizeComposition());
            if (hasSizeCompositionInformation) {
                createSpeciesByCatchWithSizeComposition(trip);
            }
        }
        createSpeciesByCatchWithoutSizeComposition(trip);
    }

    private void createSpeciesByCatchWithSizeComposition(Trip trip) {
        List categories = new ArrayList<>();
        List data = new ArrayList<>();
        Map map = new TreeMap<String, List>();
        String stack;
        String name;

        for (FishingEvent fe : trip.getFishingEvents()) {
            categories.add(DateUtils.format(fe.getDateOfFishingEvent()));

            if (fe instanceof FishingActivity) {
                FishingActivity fa = (FishingActivity) fe;

//              System.out.println("FA " + fa);
                String key;
                List tmp = new ArrayList<>();

//              for (Capture c : trip.getElementaryCapturesFrom(trip.getFishingEvents())) {
                for (Capture c : fa.getElementaryCaptures()) {

//                  System.out.println("EC " + c);
                    stack = c.getSpecie().getNameOfSpecies();
                    name = c.getSpecie().getSizeComposition();
                    key = stack + "//" + name;

                    if (map.containsKey(key)) {
                        tmp = (List) map.get(key);

                        if (tmp == null) {
                            tmp = new ArrayList<>();
                        }

                        tmp.add(c.getSpecie().getWeightOfFish());

//                      System.out.println("KEY " + key + " -- " + c.getSpecie().getWeightOfFish());
                    } else {
                        tmp = new ArrayList<>();
                        tmp.add(c.getSpecie().getWeightOfFish());
                    }

                    map.put(key, tmp);
                }
            }
        }

        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, List> entry = (Map.Entry<String, List>) it.next();

            stack = entry.getKey().split("//")[0];
            name = entry.getKey().split("//")[1];
            data.add(
                    new SimpleSeries().setStack(stack).setName(name).setData(entry.getValue()).setColor(
                            ColorUtils.getColorFromSpecie(name)));
        }

        speciesByCatchChart = new StackedAndGroupedColumn("speciesByCatchChart",
                getMessage("title.main.species.bycatch.chart"), getMessage("title.xaxis.species.bycatch.chart"),
                getMessage("title.yaxis.species.bycatch.chart"), categories, data);
    }

    private void createSpeciesDistributionWithoutSizeComposition(Trip trip) {
        Map<String, SpeciesData> data = new TreeMap<>();
        String key;
        Double totalWeight = 0d;

        for (Capture c : trip.getElementaryCapturesFrom(trip.getFishingEvents())) {
            key = c.getSpecie().getNameOfSpecies();
            totalWeight += c.getSpecie().getWeightOfFish();
            if (data.containsKey(key)) {
                SpeciesData sd = (SpeciesData) data.get(key);

                sd.setWeight(c.getSpecie().getWeightOfFish() + sd.getWeight());
                data.put(key, sd);
            } else {
                data.put(key, new SpeciesData(c.getSpecie().getNameOfSpecies(), "",
                        c.getSpecie().getWeightOfFish()));
            }
        }
        List series = new ArrayList();
        List speciesData = new ArrayList<>();
        for (Iterator it = data.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, SpeciesData> entry = (Map.Entry<String, SpeciesData>) it.next();
            SpeciesData sd = entry.getValue();
            LogService.getService(this.getClass()).logApplicationInfo(entry + " " + sd.toString());
            series.add(
                    new Point().setName(sd.getName()).setY(Utils.round(sd.getWeight(), 2)).setColor(
                            ColorUtils.getColorFromSpecie(sd.getName())));
            speciesData.add(sd);
        }

        speciesDistributionChart = new Pie("speciesDistributionChart",
                getMessage("title.main.species.distribution.chart"),
                series, "speciesDistribution");

        List headers = new ArrayList<>();

        headers.add(getMessage("label.specie.name"));
        headers.add(getMessage("label.specie.size"));
        headers.add(getMessage("label.specie.weight"));

        List footers = new ArrayList<>();

        speciesDistributionTable = new Tabular(headers, footers, speciesData);
    }

    private void createSpeciesDistributionWithSizeComposition(Trip trip) {

//      System.out.println("TRIP in speciesDistribution " + trip);
        Map<String, Map<String, SpeciesData>> data = new TreeMap<>();
        List insideData = new ArrayList<>();
        String keyLevelOne, keyLevelTwo;

        for (Capture c : trip.getElementaryCapturesFrom(trip.getFishingEvents())) {
            keyLevelOne = c.getSpecie().getNameOfSpecies();
            keyLevelTwo = c.getSpecie().getNameOfSpecies() + "//" + c.getSpecie().getSizeComposition();

            if (data.containsKey(keyLevelOne)) {
                Map m = data.get(keyLevelOne);

                if (m.containsKey(keyLevelTwo)) {
                    SpeciesData sd = (SpeciesData) m.get(keyLevelTwo);

                    sd.setWeight(c.getSpecie().getWeightOfFish() + sd.getWeight());
                    m.put(keyLevelTwo, sd);
                } else {
                    m.put(keyLevelTwo,
                            new SpeciesData(c.getSpecie().getNameOfSpecies(), c.getSpecie().getSizeComposition(),
                                    c.getSpecie().getWeightOfFish()));
                }

                data.put(keyLevelOne, m);
            } else {
                Map m = new TreeMap();

                m.put(keyLevelTwo,
                        new SpeciesData(c.getSpecie().getNameOfSpecies(), c.getSpecie().getSizeComposition(),
                                c.getSpecie().getWeightOfFish()));
                data.put(keyLevelOne, m);
            }
        }

        InsideData isd;
        HexColor color;
        int i = 1;
        List speciesData = new ArrayList<>();

        for (Map.Entry<String, Map<String, SpeciesData>> entry : data.entrySet()) {

            // entry.getKey().split("//")[0];
            isd = new InsideData();
            color = ColorUtils.getColorFromSpecie(entry.getKey());
            i++;
            isd.setName(entry.getKey());
            isd.setColor(color);

            List<OutsideData> outsideData = new ArrayList<>();
            Double weightBySpecie = 0d;

            for (Map.Entry<String, SpeciesData> entryL2 : entry.getValue().entrySet()) {
                SpeciesData sd = entryL2.getValue();

                // Put the item in the list to build a table
                speciesData.add(sd);

//              System.out.println("SD " + sd);
                weightBySpecie += sd.getWeight();
                outsideData.add(new OutsideData(sd.getSize(), sd.getWeight(),
                        ColorUtils.getColorFromSpecie(sd.getSize())));
            }

            isd.setOutsideData(outsideData);
            isd.setValue(weightBySpecie);
            insideData.add(isd);
        }

        speciesDistributionChart = new Donut("speciesDistributionChart",
                getMessage("title.main.species.distribution.chart"),
                getMessage("title.xaxis.species.distribution.chart"),
                getMessage("title.inside.label.species.distribution.chart"),
                getMessage("title.outside.label.species.distribution.chart"), insideData);

        List headers = new ArrayList<>();

        headers.add(getMessage("label.specie.name"));
        headers.add(getMessage("label.specie.size"));
        headers.add(getMessage("label.specie.weight"));

        List footers = new ArrayList<>();

        speciesDistributionTable = new Tabular(headers, footers, speciesData);
    }

    private void createSpeciesDistribution(Trip trip) {
        boolean hasSizeCompositionInformation = false;

        for (Capture c : trip.getElementaryCapturesFrom(trip.getFishingEvents())) {
            hasSizeCompositionInformation |= "".equals(c.getSpecie().getSizeComposition());
            if (hasSizeCompositionInformation) {
                createSpeciesDistributionWithSizeComposition(trip);
            }
        }
        createSpeciesDistributionWithoutSizeComposition(trip);
    }

    public class SpeciesData {

        private String name;
        private String size;
        private Double weight;

        public SpeciesData(String name, String size, Double weight) {
            this.name = name;
            this.size = size;
            this.weight = weight;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "SpeciesData{" + "name=" + name + ", size=" + size + ", weight=" + weight + '}';
        }
    }
}
