package cartographish.maps.maps.mapper;

import cartographish.maps.maps.models.Basin;
import cartographish.maps.maps.models.GeoLocation;
import cartographish.maps.maps.models.WaterBody;
import cartographish.maps.maps.models.Zone;
import cartographish.maps.maps.response.ExternalWaterBodyResponse;

public class WaterBodyMapper {

    public static WaterBody fromExternal(ExternalWaterBodyResponse ext){

        WaterBody wBody = new WaterBody();
        
        wBody.setName(ext.getNomeAcqua());
        wBody.setWaterCategory(ext.getCategoriaAcqua());

        Basin basin = new Basin();

        basin.setBasinCode(ext.getCodiceBacino());
        basin.setBasinName("Unknown");
        wBody.setBasin(basin);

        Zone zone = new Zone();

        zone.setZoneCode(ext.getZonaPesca());
        wBody.setZone(zone);

        GeoLocation geo = new GeoLocation();
        
        geo.setLatitudine(ext.getLat());
        geo.setLongitudine(ext.getLon());
        wBody.setGeoLocation(geo);

        return wBody;
    }
}
