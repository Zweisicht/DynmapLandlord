package at.zweisicht.dynmaplandlord;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class CommandListener implements Listener {


	SQLiteJDBC sql;
	DynmapLandlord dll;
	
	CommandListener(SQLiteJDBC sql, DynmapLandlord plugin){
		this.sql = sql;
		dll = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		
		System.out.println("Befehl!");
	}
	
	@EventHandler
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		
		System.out.println("Befehl!");
		
		if(e.getMessage().contains("claim") || e.getMessage().contains("buy")){
			
			//Check the IDs an add a new Mark
			sql.markChunks();
			
		}
		
	}
}
