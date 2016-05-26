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
package fr.ird.jpe.web.utils;

//import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Geometry;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geojson.geom.GeometryJSON;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;

/**
 * Convert Geotools SimpleFeatureCollection to GeoJson.
 * <p/>
 *
 * @author Eugene Borshch
 * @date: 9/17/13
 *
 */
public class FeatureToGeoJson {

    public String convert(SimpleFeatureCollection featureCollection) {
        JSONObject jsonFeatureCollection = buildFeatureCollection(featureCollection);
        return jsonFeatureCollection.toJSONString();
    }

    private JSONObject buildFeatureCollection(SimpleFeatureCollection featureCollection) {
        List<JSONObject> features = new LinkedList<JSONObject>();
        SimpleFeatureIterator simpleFeatureIterator = featureCollection.features();
        while (simpleFeatureIterator.hasNext()) {
            SimpleFeature simpleFeature = simpleFeatureIterator.next();
            features.add(buildFeature(simpleFeature));
        }

        JSONObject obj = new JSONObject();
        obj.put("type", "FeatureCollection");
        obj.put("features", features);
        return obj;
    }

    private JSONObject buildFeature(SimpleFeature simpleFeature) {
        JSONObject obj = new JSONObject();
        obj.put("type", "Feature");
        obj.put("geometry", buildGeometry((Geometry) simpleFeature.getDefaultGeometry()));
        obj.put("properties", buildProperties(simpleFeature));
        return obj;
    }

    private JSONObject buildProperties(SimpleFeature simpleFeature) {
        JSONObject obj = new JSONObject();
        Collection<Property> properties = simpleFeature.getProperties();
        for (Property property : properties) {
            if (property.getValue() == null) {
                obj.put(property.getName().toString(), "");
            } else {
                obj.put(property.getName().toString(),
                        property.getValue().toString());
            }
        }
        return obj;
    }

    private JSONObject buildGeometry(Geometry geometry) {
//        System.out.println("---------------> " + geometry);

        final GeometryJSON gjson = new GeometryJSON();
//        System.out.println("---------------> " + gjson);
        final Object obj = JSONValue.parse(gjson.toString(geometry));
//        System.out.println("---------------> " + obj);

        JSONObject jsonObj = (JSONObject) obj;

        return jsonObj;
    }

}
