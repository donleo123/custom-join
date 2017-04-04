package me.donleo123.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main
  extends JavaPlugin
  implements Listener
  {

	public void onEnable() {
		getLogger().info("§0[§4CustomJoin v3.0§0] §6Enabled!");
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	
	@EventHandler
    public void onJoin(PlayerJoinEvent e) {
		if(!e.getPlayer().hasPlayedBefore()) {
			Player p = e.getPlayer();
			e.setJoinMessage(null);
			Server server = Bukkit.getServer();
			String message = getConfig().getString("first join message");
			String message_r = message.replace("%player%", p.getName());
			server.broadcastMessage(message_r.replace("&", "§"));
		}else{
		    Player p = e.getPlayer();
		    e.setJoinMessage(null);
		    Server server = Bukkit.getServer();
		    String message = getConfig().getString("join message");
		    String message_r = message.replace("%player%", p.getName());
		    server.broadcastMessage(message_r.replace("&", "§"));
		}
	}
	
	private void SendPlayerMessage(Player p, String message) {
		String prefix = "§0[§4CustomJoin 3.0§0] ";
		p.sendMessage(prefix + message);
	}
	
	  public void SendHelp(Player p)
	  {
	    p.sendMessage("§7§m=========================");
	    p.sendMessage("§7[§aCustomJoin§7]");
	    p.sendMessage("§c/CustomJoin help §9Shows this help page.");
	    p.sendMessage("§c/CustomJoin reload §9Reloads the config.");
	    p.sendMessage("§c/CustomJoin showjoin §9Shows the join message.");
	    p.sendMessage("§c/CustomJoin showfirst §9Shows the firstjoin message.");
	    p.sendMessage("§7§m=========================");
	  }
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			if (sender instanceof Player) {
				Player p = (Player)sender;
				if (p.hasPermission("customjoin.admin") || (p.isOp())) {
					if (label.equalsIgnoreCase("customjoin") || (label.equalsIgnoreCase("cj"))) {
						if (args[0].equalsIgnoreCase("reload")) {
							reloadConfig();
							SendPlayerMessage(p, "§6Config Reloaded");
						}
						else if (args[0].equalsIgnoreCase("showjoin")) {
				              p.sendMessage(ChatColor.GRAY + "-=" + ChatColor.RED + " Join Message " + ChatColor.GRAY + "=-");
				              p.sendMessage("");
				              String message = getConfig().getString("join message");
				              String messageR = message.replace("%player%", p.getName());
				              p.sendMessage(messageR.replace("&", "§"));
				              p.sendMessage("");
						}
						else if (args[0].equalsIgnoreCase("showfirst")) {
				              p.sendMessage(ChatColor.GRAY + "-=" + ChatColor.RED + " First Join Message " + ChatColor.GRAY + "=-");
				              p.sendMessage("");
				              String message = getConfig().getString("first join message");
				              String messageR = message.replace("%player%", p.getName());
				              p.sendMessage(messageR.replace("&", "§"));
				              p.sendMessage("");
					}
						else if (args[0].equalsIgnoreCase("help")) {
							SendHelp(p);
						}
						else {
							SendHelp(p);
							return true;
						}
					}
			}
		}
		      else {
		    	  Player p = (Player)sender;
				SendPlayerMessage(p, "§cYou don't have the right permission");
		      }
	}
		return false;
}
  }