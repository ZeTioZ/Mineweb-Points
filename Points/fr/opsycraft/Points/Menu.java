package fr.opsycraft.Points;


import java.io.File;
import fr.opsycraft.Points.Config;
import fr.opsycraft.Points.DataBase;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Menu
  implements Listener
{
  Config config = new Config(new File("plugins" + File.separator + "Points" + File.separator + "config.yml"));
  String h = this.config.getString("host");
  String n = this.config.getString("name");
  String p = this.config.getString("pass");
  String db = this.config.getString("dbName");
  int po = this.config.getInt("port");
  public DataBase bdd = new DataBase(this.h, this.db, this.n, this.p);
  private Inventory inv;
  private ItemStack ItemList;
  Config chestf = new Config(new File("plugins" + File.separator + "Points" + File.separator + "chest.yml"));
  String Name = this.chestf.getString("Chest.Name");
  int[] menuList = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
  
  public Menu(Plugin p)
  {
    this.inv = Bukkit.getServer().createInventory(null, 9, this.Name);
    for (int i = 0; this.menuList.length >= i; i++) {
      String x = Integer.toString(i);
      if (this.chestf.getString(x) != null)
      {
        this.ItemList = createItem(Material.getMaterial(this.chestf.getString(i + ".ID")), this.chestf.getString(i + ".NAME"));
        this.inv.setItem(this.chestf.getInt(i + ".POSITIONX"), this.ItemList);
      }
    }
    Bukkit.getServer().getPluginManager().registerEvents(this, p);
  }
  
  private ItemStack createItem(Material dc, String name)
  {
    for (int i = 0; this.menuList.length >= i; i++) {
      String x = Integer.toString(i);
      if (this.chestf.getString(x) != null)
      {
        ItemStack iS = new ItemStack(dc, this.chestf.getInt(i + ".NOMBRE"));
        ItemMeta im = iS.getItemMeta();
        im.setDisplayName(name);
        im.setLore(this.chestf.getStringList(i + ".LORE"));
        iS.setItemMeta(im);
        return iS;
      }
    }
    return this.ItemList;
  }
  
  public void show(Player p)
  {
    p.openInventory(this.inv);
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent e)
  {
    if (!e.getInventory().getName().equalsIgnoreCase(this.inv.getName())) {
      return;
    }
    if (e.getCurrentItem().getItemMeta() == null) {
      return;
    }
    Player player = (Player)e.getWhoClicked();
    for (int i = 0; this.menuList.length >= i; i++) {
      String x = Integer.toString(i);
      if ((this.chestf.getString(x) != null) && (e.getCurrentItem().getItemMeta().getDisplayName().contains(this.chestf.getString(i + ".NAME"))))
      {
        e.setCancelled(true);
        String name = player.getName();
        String playersend = this.bdd.getString("SELECT pseudo FROM users WHERE pseudo = '" + name + "';", 1);
        if (playersend.equalsIgnoreCase(name))
        {
          RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
          double playermoney = ((Economy)rsp.getProvider()).getBalance(player);
          if (playermoney >= this.chestf.getInt(i + ".PRIX.IG"))
          {
            ((Economy)rsp.getProvider()).withdrawPlayer(player, this.chestf.getInt(i + ".PRIX.IG"));
            int playermoneyweb = this.bdd.getInt("SELECT money FROM users WHERE pseudo = '" + name + "';", 1);
            int finplayer = playermoneyweb + this.chestf.getInt(i + ".PRIX.WEB");
            this.bdd.sendRequest("UPDATE users SET money = " + finplayer + " WHERE pseudo = '" + name + "';");
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = this.chestf.getString("1.COMMAND").replace("%PLAYER%", player.getName());
            Bukkit.dispatchCommand(console, command);
            player.sendMessage("§c[Points]§3 Tu viens de payer " + this.chestf.getInt(new StringBuilder(String.valueOf(i)).append(".PRIX.WEB").toString()) + " points sur la boutique in game");
            player.sendMessage("§c[Points]§3 Tu as maintenant " + finplayer + " points sur la boutique");
            return;
          }
          player.sendMessage("§c[Points]§3 Tu n'as pas assez d'argent !");
          
          return;
        }
        player.sendMessage("§c[Points]§3 Tu n'es pas inscrit sur le site");
        
        return;
      }
    }
  }
}
