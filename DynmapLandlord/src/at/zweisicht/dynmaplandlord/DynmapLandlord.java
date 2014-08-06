package at.zweisicht.dynmaplandlord;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DynmapLandlord extends JavaPlugin  implements Listener, CommandExecutor{
	
	    Plugin dynmap = getServer().getPluginManager().getPlugin("dynmap");
    	SQLiteJDBC sql;
    	
    @Override
    public void onEnable() {

    	getServer().getPluginManager().registerEvents(this, this);
    	
    	if((dynmap == null) || (!dynmap.isEnabled())) {
    		getLogger().info("Dynmap is not running!");
    	}
    	
    	sql = new SQLiteJDBC(dynmap, this);
    	sql.connect();
    	sql.markChunks();
    	
    }
	
    @Override
    public void onDisable() {
    	
    	getLogger().info("DynmapLandord disable!");
    	
    }   

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		
		String cmd = e.getMessage();
		
		if(cmd.contains("ll buy") || cmd.contains("ll claim")
				|| cmd.contains("land buy") || cmd.contains("land claim")){
			;
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	            @Override
	            public void run() {
	                sql.reloadMarkChunks();
	            }
	        }, 3);
			
		}
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("dll")) {
			if (args.length == 1) {
				if (args[0].contains("reload")){
					sql.reloadMarkChunks();
				}
			}
		}
		
		return true;
	}
    
}
