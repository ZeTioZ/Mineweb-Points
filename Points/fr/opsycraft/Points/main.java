package fr.opsycraft.Points;

import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	
	Config config = new Config(new File("plugins" + File.separator + "Points" + File.separator + "config.yml"));
	String h = this.config.getString("host");
	String n = this.config.getString("name");
	String p = this.config.getString("pass");
	String db = this.config.getString("dbName");
	int po = this.config.getInt("port");
	public DataBase bdd = new DataBase(this.h, this.db, this.n, this.p);
	private File chestf;
	
  	public void onEnable() {
  		saveDefaultConfig();
  		this.chestf = new File(getDataFolder(), "chest.yml");
  		if (!this.chestf.exists()) {
  			this.chestf.getParentFile().mkdirs();
  			saveResource("chest.yml", false);
  		}
  		//this.menu = new Menu(this);
  		getCommand("points").setExecutor(new PointsHandler());
    
  		this.bdd.connection();
  		System.out.println("[Points] Le plugin vient de s'allumer");
  	}
  
  	public void onDisable() {
  		System.out.println("[Points] Le plugin vient de s'éteindre");
  		this.bdd.disconnection();
  	}
}
