package cartographish.maps.maps.models;

import java.util.List;
import java.util.Map;

public class GeoJsonModels {

    public static class FeatureCollection {
        private final String type = "FeatureCollection";
        private List<GeoJsonFeature> features;

        public FeatureCollection() {}
        public FeatureCollection(List<GeoJsonFeature> features) { this.features = features; }

        public String getType() { return type; }
        public List<GeoJsonFeature> getFeatures() { return features; }
        public void setFeatures(List<GeoJsonFeature> features) { this.features = features; }
    }

    public static class GeoJsonFeature {
        private final String type = "Feature";
        private Geometry geometry;
        private Map<String, Object> properties;

        public GeoJsonFeature(Geometry geometry, Map<String, Object> properties) {
            this.geometry = geometry;
            this.properties = properties;
        }

        public String getType() { return type; }
        public Geometry getGeometry() { return geometry; }
        public void setGeometry(Geometry geometry) { this.geometry = geometry; }
        public Map<String, Object> getProperties() { return properties; }
        public void setProperties(Map<String, Object> properties) { this.properties = properties; }
    }

    public static class Geometry {
        private final String type = "Point";
        private double[] coordinates;

        public Geometry(double longitude, double latitude) {
            this.coordinates = new double[]{longitude, latitude};
        }

        public String getType() { return type; }
        public double[] getCoordinates() { return coordinates; }
        public void setCoordinates(double[] coordinates) { this.coordinates = coordinates; }
    }
}