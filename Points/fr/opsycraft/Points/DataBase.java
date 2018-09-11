package fr.opsycraft.Points;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase
{
  private String host;
  private String name;
  private String user;
  private String pass;
  private String url;
  private Connection conn;
  
  public DataBase(String h, String n, String u, String p)
  {
    this.host = h;
    this.name = n;
    this.user = u;
    this.pass = p;
    this.url = ("jdbc:mysql://" + this.host + "/" + this.name);
  }
  
  public void connection()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    try
    {
      this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
      System.out.println("[Points] La base de donnée est bien liée.");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      System.out.println("[Points] Impossible de se connecter à la BDD.");
    }
  }
  
  public boolean connected()
  {
    return this.conn != null;
  }
  
  private void connectIfNot()
  {
    if (!connected()) {
      connection();
    }
  }
  
  public void disconnection()
  {
    if (connected()) {
      this.conn = null;
    }
  }
  
  public String getString(String request, int ci)
  {
    connectIfNot();
    try
    {
      Statement state = this.conn.createStatement();
      ResultSet result = state.executeQuery(request);
      try
      {
        if (result.next()) {
          return result.getString(ci);
        }
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      return "NULL";
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return request;
  }
  
  public int getInt(String request, int ci)
  {
    connectIfNot();
    try
    {
      Statement state = this.conn.createStatement();
      ResultSet result = state.executeQuery(request);
      try
      {
        if (result.next()) {
          return result.getInt(ci);
        }
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
      return -1;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return ci;
  }
  
  public void sendRequest(String request)
  {
    connectIfNot();
    try
    {
      Statement state = this.conn.createStatement();
      state.executeUpdate(request);
      state.close();
      System.out.println("[Points] L'opération a réussis.");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
      System.out.println("[Points] L'opération a échouée.");
    }
  }
}
