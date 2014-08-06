package at.zweisicht.dynmaplandlord;

import java.sql.*;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

public class SQLiteJDBC {
	
	Connection c = null;
	Statement stmt = null;
	Plugin dynmap;
	DynmapAPI api = (DynmapAPI) dynmap;	
	DynmapLandlord dll;
	
	int db_id = 0;
			    
	MarkerSet markers;
	
	SQLiteJDBC(Plugin dynmap, DynmapLandlord dll) {
		
		this.dynmap = dynmap;
		api = (DynmapAPI) dynmap;
		this.dll = dll;
		//markers = api.getMarkerAPI().createMarkerSet("Landlord", "LandlordDynmap", null, false);
	  }
	
	
	void connect(){

	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:plugins/Landlord/Landlord.db");
	    } catch ( Exception e ) {
	    	dll.getLogger().info( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
	    dll.getLogger().info("Opened database successfully");		
	}
	
	void markChunks(){
		
		markers = api.getMarkerAPI().createMarkerSet("Landlord", "LandlordDynmap", null, false);
		
		UUID uuid;
		String world = "world";
		int x = 0;
		int z = 0;
		
		try {
			stmt = c.createStatement();
			
		     String sql = "SELECT * FROM `ll_land`;";
		     ResultSet rs = stmt.executeQuery( sql );
		     
		     int id = 0;
		     while ( rs.next() ) {
		    	 
		    	id +=1;
		    	uuid = UUID.fromString(rs.getString("owner_name"));
		    	world = rs.getString("world_name");
		    	x = rs.getInt("x");
		    	z = rs.getInt("z");
		    	//db_id = rs.getInt("id");
		    	
				OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
		    	
		    	// Eckpunkte
		    	double[] x1 = {x *16 +16, x *16, x *16, x*16+16};
		    	double[] z1 = {z*16+16, z*16+16, z*16, z*16};
		    	
		    	
		    	AreaMarker am = markers.createAreaMarker("Landlord_" + id, player.getName(), false, world, x1, z1, false);
		    	am.setFillStyle(0.3, 0xFFB90F);
		    	am.setLineStyle(1, 0.5, 0xFFD700);
		     }
			
		      rs.close();
		      stmt.close();
		      c.close();
		     
		} catch (SQLException e) {
			dll.getLogger().info("Can'r read DB: " + e.getMessage());
		}
		
	}
	
	void reloadMarkChunks(){
		
		dll.getLogger().info("Reload the DynmapMarks!");
		connect();
		markers.deleteMarkerSet();
		markChunks();
		
	}
	
}
