package fr.opsycraft.Points;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config
{
  private File fichierConfig;
  private FileConfiguration fconfig;
  
  public Config(File file)
  {
    this.fichierConfig = file;
    loadConfig();
  }
  
  public void save()
  {
    try
    {
      this.fconfig.save(this.fichierConfig);
    }
    catch (IOException ex)
    {
      Bukkit.getLogger().severe("An error has occured while saving file " + this.fichierConfig.getPath());
    }
  }
  
  private void loadConfig()
  {
    this.fconfig = YamlConfiguration.loadConfiguration(this.fichierConfig);
  }
  
  public void set(String path, Object obj)
  {
    this.fconfig.set(path, obj);
    save();
  }
  
  public String getString(String path)
  {
    String name = this.fconfig.getString(path);
    return name == null ? null : name.replace("&", "§");
  }
  
  public int getInt(String path)
  {
    return this.fconfig.getInt(path);
  }
  
  public long getLong(String path)
  {
    return this.fconfig.getLong(path);
  }
  
  public boolean getBoolean(String path)
  {
    return this.fconfig.getBoolean(path);
  }
  
  public double getDouble(String path)
  {
    return this.fconfig.getDouble(path);
  }
  
  public List<String> getStringList(String path)
  {
    List<String> name = new ArrayList<>();
    for (String nom : this.fconfig.getStringList(path)) {
      name.add(nom.replace("&", "§"));
    }
    return name;
  }
  
  public List<Integer> getIntegerList(String path)
  {
    List<Integer> name = new ArrayList<>();
    for (Integer nom : this.fconfig.getIntegerList(path)) {
      name.add(nom);
    }
    return name;
  }
  
  public List<String> getKeys(String path)
  {
    List<String> list = new ArrayList<>();
    if ("".equalsIgnoreCase(path)) {
      for (String section : this.fconfig.getKeys(false)) {
        list.add(section);
      }
    } else {
      for (String section : this.fconfig.getConfigurationSection(path).getKeys(false)) {
        list.add(section);
      }
    }
    return list;
  }
  
  public boolean exist(String path)
  {
    return this.fconfig.contains(path);
  }
}
