package fr.opsycraft.Points;

import java.io.File;
import java.util.List;
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
  private ItemStack Item13;
  private ItemStack Item12;
  private ItemStack Item11;
  Config chestf = new Config(new File("plugins" + File.separator + "Points" + File.separator + "chest.yml"));
  String Name = this.chestf.getString("Chest.Name");
  String N1 = this.chestf.getString("1");
  String N2 = this.chestf.getString("2");
  String N3 = this.chestf.getString("3");
  int POSI1 = this.chestf.getInt("1.POSITIONX");
  int POSI2 = this.chestf.getInt("2.POSITIONX");
  int POSI3 = this.chestf.getInt("3.POSITIONX");
  int nb1 = this.chestf.getInt("1.NOMBRE");
  int nb2 = this.chestf.getInt("2.NOMBRE");
  int nb3 = this.chestf.getInt("3.NOMBRE");
  
  public Menu(Plugin p)
  {
    this.inv = Bukkit.getServer().createInventory(null, 9, this.Name);
    if (this.N1 != null)
    {
      this.Item11 = createItem(Material.getMaterial(this.chestf.getString("1.ID")), this.chestf.getString("1.NAME"));
      this.inv.setItem(this.POSI1, this.Item11);
    }
    if (this.N2 != null)
    {
      this.Item12 = createItem(Material.getMaterial(this.chestf.getString("2.ID")), this.chestf.getString("2.NAME"));
      this.inv.setItem(this.POSI2, this.Item12);
    }
    if (this.N3 != null)
    {
      this.Item13 = createItem(Material.getMaterial(this.chestf.getString("3.ID")), this.chestf.getString("3.NAME"));
      this.inv.setItem(this.POSI3, this.Item13);
    }
    Bukkit.getServer().getPluginManager().registerEvents(this, p);
  }
  
  private ItemStack createItem(Material dc, String name)
  {
    List<String> LORE1 = this.chestf.getStringList("1.LORE");
    List<String> LORE2 = this.chestf.getStringList("2.LORE");
    List<String> LORE3 = this.chestf.getStringList("3.LORE");
    if (this.N1 != null)
    {
      ItemStack i = new ItemStack(dc, this.nb1);
      ItemMeta im = i.getItemMeta();
      im.setDisplayName(name);
      im.setLore(LORE1);
      i.setItemMeta(im);
      return i;
    }
    if (this.N2 != null)
    {
      ItemStack i2 = new ItemStack(dc, this.nb2);
      ItemMeta im2 = i2.getItemMeta();
      im2.setDisplayName(name);
      im2.setLore(LORE2);
      i2.setItemMeta(im2);
      return i2;
    }
    if (this.N3 != null)
    {
      ItemStack i3 = new ItemStack(dc, this.nb3);
      ItemMeta im3 = i3.getItemMeta();
      im3.setDisplayName(name);
      im3.setLore(LORE3);
      i3.setItemMeta(im3);
      return i3;
    }
    return this.Item11;
  }
  
  public void show(Player p)
  {
    p.openInventory(this.inv);
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent e)
  {
    int PRIX1 = this.chestf.getInt("1.PRIX.IG");
    int PRIX2 = this.chestf.getInt("2.PRIX.IG");
    int PRIX3 = this.chestf.getInt("3.PRIX.IG");
    int PRIX4 = this.chestf.getInt("1.PRIX.WEB");
    int PRIX5 = this.chestf.getInt("2.PRIX.WEB");
    int PRIX6 = this.chestf.getInt("3.PRIX.WEB");
    if (!e.getInventory().getName().equalsIgnoreCase(this.inv.getName())) {
      return;
    }
    if (e.getCurrentItem().getItemMeta() == null) {
      return;
    }
    Player player = (Player)e.getWhoClicked();
    if (e.getCurrentItem().getItemMeta().getDisplayName().contains(this.chestf.getString("1.NAME")))
    {
      e.setCancelled(true);
      String name = player.getName();
      String playersend = this.bdd.getString("SELECT pseudo FROM users WHERE pseudo = '" + name + "';", 1);
      if (playersend.equalsIgnoreCase(name))
      {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        int playermoney = (int)((Economy)rsp.getProvider()).getBalance(player);
        if (playermoney >= PRIX1)
        {
          ((Economy)rsp.getProvider()).withdrawPlayer(player, PRIX1);
          int playermoneyweb = this.bdd.getInt("SELECT money FROM users WHERE pseudo = '" + name + "';", 1);
          int finplayer = playermoneyweb - PRIX4;
          this.bdd.sendRequest("UPDATE users SET money = " + finplayer + " WHERE pseudo = '" + name + "';");
          ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
          String command = this.chestf.getString("1.COMMAND").replace("%PLAYER%", player.getName());
          Bukkit.dispatchCommand(console, command);
          player.sendMessage("§c[Points]§3 Tu viens de payer " + PRIX4 + " points sur la boutique in game");
          player.sendMessage("§c[Points]§3 Tu as maintenant " + finplayer + " points sur la boutique");
          return;
        }
        player.sendMessage("§c[Points]§3 Tu n'as pas assez d'argent !");
        
        return;
      }
      player.sendMessage("§c[Points]§3 Tu n'es pas inscrit sur le site");
      
      return;
    }
    if (e.getCurrentItem().getItemMeta().getDisplayName().contains(this.chestf.getString("2.NAME")))
    {
      e.setCancelled(true);
      String name = player.getName();
      String playersend = this.bdd.getString("SELECT pseudo FROM users WHERE pseudo = '" + name + "';", 1);
      if (playersend.equalsIgnoreCase(name))
      {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        int playermoney = (int)((Economy)rsp.getProvider()).getBalance(player);
        if (playermoney >= PRIX2)
        {
          ((Economy)rsp.getProvider()).withdrawPlayer(player, PRIX2);
          int playermoneyweb = this.bdd.getInt("SELECT money FROM users WHERE pseudo = '" + name + "';", 1);
          int finplayer = playermoneyweb - PRIX5;
          this.bdd.sendRequest("UPDATE users SET money = " + finplayer + " WHERE pseudo = '" + name + "';");
          ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
          String command = this.chestf.getString("2.COMMAND").replace("%PLAYER%", player.getName());
          Bukkit.dispatchCommand(console, command);
          player.sendMessage("§c[Points]§3 Tu viens de payer " + PRIX5 + " points sur la boutique in game");
          player.sendMessage("§c[Points]§3 Tu as maintenant " + finplayer + " points sur la boutique");
          return;
        }
        player.sendMessage("§c[Points]§3 Tu n'as pas assez d'argent !");
        
        return;
      }
      player.sendMessage("§c[Points]§3 Tu n'es pas inscrit sur le site");
      
      return;
    }
    if (e.getCurrentItem().getItemMeta().getDisplayName().contains(this.chestf.getString("3.NAME")))
    {
      e.setCancelled(true);
      String name = player.getName();
      String playersend = this.bdd.getString("SELECT pseudo FROM users WHERE pseudo = '" + name + "';", 1);
      if (playersend.equalsIgnoreCase(name))
      {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        int playermoney = (int)((Economy)rsp.getProvider()).getBalance(player);
        if (playermoney >= PRIX3)
        {
          ((Economy)rsp.getProvider()).withdrawPlayer(player, PRIX3);
          int playermoneyweb = this.bdd.getInt("SELECT money FROM users WHERE pseudo = '" + name + "';", 1);
          int finplayer = playermoneyweb - PRIX6;
          this.bdd.sendRequest("UPDATE users SET money = " + finplayer + " WHERE pseudo = '" + name + "';");
          ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
          String command = this.chestf.getString("3.COMMAND").replace("%PLAYER%", player.getName());
          Bukkit.dispatchCommand(console, command);
          player.sendMessage("§c[Points]§3 Tu viens de payer " + PRIX6 + " points boutique en jeu.");
          player.sendMessage("§c[Points]§3 Tu as maintenant " + finplayer + " points sur la boutique.");
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
