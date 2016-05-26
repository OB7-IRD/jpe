/*
 * Copyright (C) 2015 IRD
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

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import fr.ird.jpe.web.utils.FeatureToGeoJson;
import fr.ird.driver.eva.business.FishingEvent;
import fr.ird.driver.eva.business.Position;
import fr.ird.driver.eva.business.Trip;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.joda.time.DateTime;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.context.MessageSource;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 10 févr. 2015
 *
 *
 */
public class MapTripJob extends AbstractShowJob {

    private DefaultFeatureCollection features;
    private SimpleFeatureBuilder featureBuilder;
    private GeometryFactory gf = JTSFactoryFinder.getGeometryFactory(null);

    private String geoms;

    public MapTripJob(MessageSource source, Locale locale, Trip trip) {
        super(source, locale);
        createGeoms(trip);

    }

    public String getGeoms() {
        return geoms;
    }

    private void createGeoms(Trip trip) {
        SimpleFeature feature;
        features = new DefaultFeatureCollection();
        int i = 0;
        List<Position> positions = new ArrayList();
        Position current = null;
        String vesselName = trip.getVessel().getVesselName();
        for (FishingEvent fe : trip.getFishingEvents()) {
            if (fe.getPosition() != null) {
                current = fe.getPosition();
            } else {
                current = new Position(0d, 0d);
            }
            positions.add(current);
            feature = buildFeatureForFishingEvent("fishingEvent" + i,
                    vesselName,
                    fe.getType(),
                    fe.getDateOfFishingEvent(),
                    current
            );
            features.add(feature);
//            System.out.println("FEATURE " + feature);
            i++;
        }
        feature = buildFeatureForVesselPath("vesselPath", vesselName, trip.getDateOfDep(), trip.getDateOfRtp(), positions);
//        System.out.println("FEATURE " + feature);
        features.add(feature);
//        System.out.println("FEATURES " + features);
        geoms = new FeatureToGeoJson().convert(features);
//        System.out.println("GEOMS " + geoms);
    }

    /**
     * Here is how you can use a SimpleFeatureType builder to create the schema
     * for your shapefile dynamically.
     * <p>
     * This method is an improvement on the code used in the main method above
     * (where we used DataUtilities.createFeatureType) because we can set a
     * Coordinate Reference System for the FeatureType and a a maximum field
     * length for the 'name' field dddd
     */
    private static SimpleFeatureType createFeatureTypeForVesselPath() {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();

        builder.setName("VesselPath");
        builder.setCRS(DefaultGeographicCRS.WGS84);    // <- Coordinate reference system

        // add attributes in order
//        builder.add("Location", Line.class);
        builder.add(FEATURE_PROPERTY_LOCATION, LineString.class);
        builder.length(15).add(FEATURE_PROPERTY_TYPE, String.class);
        builder.length(15).add(FEATURE_PROPERTY_VESSEL_NAME, String.class);    // <- 15 chars width for name field
        builder.add(FEATURE_PROPERTY_DEPARTURE_DATE, DateTime.class);
        builder.add(FEATURE_PROPERTY_RTP_DATE, DateTime.class);
        // build the type
        final SimpleFeatureType vesselPath = builder.buildFeatureType();

        return vesselPath;
    }

    private static SimpleFeatureType createFeatureTypeForFishingEvent() {
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();

        builder.setName("FishingEventPoint");
        builder.setCRS(DefaultGeographicCRS.WGS84);    // <- Coordinate reference system

        // add attributes in order
        builder.add(FEATURE_PROPERTY_LOCATION, Point.class);
        builder.length(15).add(FEATURE_PROPERTY_TYPE, String.class);       // <- 15 chars width for name field
        builder.length(15).add(FEATURE_PROPERTY_VESSEL_NAME, String.class);    // <- 15 chars width for name field
        builder.add(FEATURE_PROPERTY_DATE, DateTime.class);    // <- 15 chars width for name field

        // build the type
        final SimpleFeatureType LOCATION = builder.buildFeatureType();

        return LOCATION;
    }
    public static final String FEATURE_PROPERTY_TYPE = "type";
    public static final String FEATURE_PROPERTY_LOCATION = "position";
    public static final String FEATURE_PROPERTY_VESSEL_NAME = "vessel";
    public static final String FEATURE_PROPERTY_DATE = "date";
    public static final String FEATURE_PROPERTY_DEPARTURE_DATE = "departure";
    public static final String FEATURE_PROPERTY_RTP_DATE = "end";

    private SimpleFeature buildFeatureForFishingEvent(String id, String vesselName, String typeOfFishingEvent, DateTime dateOfActivity, Position position) {
        return buildFeatureForFishingEvent(id, vesselName, typeOfFishingEvent, dateOfActivity, position.getLongitude(), position.getLatitude());
    }

    private SimpleFeature buildFeatureForFishingEvent(String id, String vesselName, String typeOfFishingEvent, DateTime dateOfActivity, double longitude, double latitude) {
        featureBuilder = new SimpleFeatureBuilder(createFeatureTypeForFishingEvent());
        featureBuilder.add(gf.createPoint(new Coordinate(longitude, latitude)));
        featureBuilder.set(FEATURE_PROPERTY_VESSEL_NAME, vesselName);
        featureBuilder.set(FEATURE_PROPERTY_DATE, dateOfActivity);
        featureBuilder.set(FEATURE_PROPERTY_TYPE, typeOfFishingEvent);

        return featureBuilder.buildFeature(id);
    }

    private SimpleFeature buildFeatureForVesselPath(String id, String vesselName, DateTime departureDate, DateTime returnOfPortDate, List<Position> positions) {
        featureBuilder = new SimpleFeatureBuilder(createFeatureTypeForVesselPath());
        Coordinate[] coordinates = new Coordinate[positions.size()];
        for (int i = 0; i < positions.size(); i++) {
            Position get = positions.get(i);
//            System.out.println("POSITION " + get);
            coordinates[i] = new Coordinate(get.getLongitude(), get.getLatitude());
        }
//        System.out.println("COORD Size" + coordinates.length);
        featureBuilder.add(gf.createLineString(coordinates));
        featureBuilder.set(FEATURE_PROPERTY_VESSEL_NAME, vesselName);
        if (departureDate != null) {
            featureBuilder.set(FEATURE_PROPERTY_DEPARTURE_DATE, departureDate);
        }
        if (returnOfPortDate != null) {
            featureBuilder.set(FEATURE_PROPERTY_RTP_DATE, returnOfPortDate);
        }
        featureBuilder.set(FEATURE_PROPERTY_TYPE, "VMS");

        return featureBuilder.buildFeature(id);
    }

}
