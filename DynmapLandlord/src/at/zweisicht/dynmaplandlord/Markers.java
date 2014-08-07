package at.zweisicht.dynmaplandlord;

import java.util.List;

import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

import com.jcdesimp.landlord.Landlord;
import com.jcdesimp.landlord.persistantData.OwnedLand;

public class Markers {

	MarkerSet markers ;
	Plugin dynmap;
	DynmapAPI api = (DynmapAPI) dynmap;	
	Landlord ll;
	DynmapLandlord dll;
	
	Markers(Plugin dynmap, DynmapLandlord dll, Landlord ll){
		
		this.dynmap = dynmap;
		api = (DynmapAPI) dynmap;
		this.ll = ll;
		this.dll = dll;
		
	}
	
	void reloadMarkers(){
		
		dll.getLogger().info("Reload the DynmapMarks!");
		markers.deleteMarkerSet();
		createMarkers();
		
	}
	
	
	void createMarkers(){
		
		List<OwnedLand> oll = Landlord.getInstance().getDatabase().find(OwnedLand.class).where().findList();
		markers = api.getMarkerAPI().createMarkerSet("Landlord", "LandlordDynmap", null, false);
		
		for(int i = 0; i <= oll.size() -1; i++){
			
			OwnedLand  info = oll.get(i);
			
			String username = info.getOwnerUsername();
			String world = info.getWorldName();
			
			int x = info.getXBlock();
			int z = info.getZBlock();
			
	    	double[] x1 = {x+16, x, x, x+16};
	    	double[] z1 = {z+16, z+16, z, z};
			
			AreaMarker am = markers.createAreaMarker("" + info.getId(), username, false, world, x1, z1, false);
			am.setFillStyle(0.3, 0xFFB90F);
	    	am.setLineStyle(1, 0.5, 0xFFD700);
			
		}
	}
	
	
	void removeMarker(OwnedLand ol){
		markers.findAreaMarker("" + ol.getId()).deleteMarker();
	}
	
	
	void addMarker(OwnedLand ol){
		
		String username = ol.getOwnerUsername();
		String world = ol.getWorldName();
		
		int x = ol.getXBlock();
		int z = ol.getZBlock();
		
    	double[] x1 = {x+16, x, x, x+16};
    	double[] z1 = {z+16, z+16, z, z};
		
		AreaMarker am = markers.createAreaMarker("" + ol.getId(), username, false, world, x1, z1, false);
		am.setFillStyle(0.3, 0xFFB90F);
    	am.setLineStyle(1, 0.5, 0xFFD700);
		
	}
	
}
