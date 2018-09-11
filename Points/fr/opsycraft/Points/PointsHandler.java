package fr.opsycraft.Points;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public class PointsHandler implements CommandExecutor {
	
	Config config = new Config(new File("plugins" + File.separator + "Points" + File.separator + "config.yml"));
	String h = this.config.getString("host");
	String n = this.config.getString("name");
	String p = this.config.getString("pass");
	String db = this.config.getString("dbName");
	int po = this.config.getInt("port");
	public DataBase bdd = new DataBase(this.h, this.db, this.n, this.p);
	
  	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			if(cmd.getName().equalsIgnoreCase("points")) {
				if(args.length == 0) {
					String name = sender.getName();
					int bc = this.bdd.getInt("SELECT money FROM users WHERE pseudo = '" + name + "';", 1);
					sender.sendMessage("§c[Points]§3 Tu as " + bc + " points !");
					return true;
					/*sender.sendMessage("§3/points check [joueur] - Affiche les points boutique actuels.");
		          	sender.sendMessage("§3/points send <joueur> <montant> - Envoyer ses points à un autre joueur.");
		          	if (sender.hasPermission("points.set")) {
		            	sender.sendMessage("§3/points set <joueur> <montant> - Fixer les points d'un joueur.");
		            	sender.sendMessage("§3/points add <joueur> <montant> - Ajouter des points à un joueur.");
		            	sender.sendMessage("§3/points remove <joueur> <montant> - Retirer des points à un joueur.");
		          	}
		            	sender.sendMessage("§3/points shop  - Ouvre le shop en jeu.");*/
				}
				else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("check")) {
						String name = sender.getName();
						int bc = this.bdd.getInt("SELECT money FROM users WHERE pseudo = '" + name + "';", 1);
						sender.sendMessage("§c[Points]§3 Tu as " + bc + " points !");
						return true;
					}
					if (args[0].equalsIgnoreCase("send")) {
						sender.sendMessage("§c[Points]§3 /points send <joueur> <montant>");
						return true;
					}
					if (args[0].equalsIgnoreCase("set")) {
						if (sender.hasPermission("points.set")) {
							sender.sendMessage("§c[Points]§3 /points set <joueur> <montant>");
						}
						else {
							sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
						}
						return true;
					}
					if (args[0].equalsIgnoreCase("add")) {
						if (sender.hasPermission("points.add")) {
							sender.sendMessage("§c[Points]§3 /points add <joueur> <montant>");
					}
						else {
							sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
						}
						return true;
					}
					if (args[0].equalsIgnoreCase("remove")) {
						if (sender.hasPermission("points.remove")) {
							sender.sendMessage("§c[Points]§3 /points remove <joueur> <montant>");
						}
						else {
		          			sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
		          		}
						return true;
		        	}
		         /* if (args[0].equalsIgnoreCase("shop"))
		          	{
		            	if (sender.hasPermission("points.shop")) {
		              		this.menu.show(sender.getPlayer());
		            	}
		            	else {
		              		sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
		            	}
		            	return true;
		        	}*/
		        	else {
			        	sender.sendMessage("§3/points check [joueur] - Affiche les points boutique actuels.");
			        	sender.sendMessage("§3/points send <joueur> <montant> - Envoyer ses points à un autre joueur.");
			        	if (sender.hasPermission("points.set"))
			        	{
			        		sender.sendMessage("§3/points set <joueur> <montant> - Fixer les points d'un joueur.");
			        		sender.sendMessage("§3/points add <joueur> <montant> - Ajouter des points à un joueur.");
			        		sender.sendMessage("§3/points remove <joueur> <montant> - Retirer des points à un joueur.");
			        	}
			        	//sender.sendMessage("§3/points shop  - Ouvre le gui du shop en jeu.");
		        	}
		    	}
				else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("check")) {
						String playerTargetString = this.bdd.getString("SELECT pseudo FROM users WHERE pseudo = '" + args[1] + "';", 1); //Retrieve target string from DB
						if(playerTargetString.equalsIgnoreCase(args[1])) {
				            int playerTargetMoneyInt = this.bdd.getInt("SELECT money FROM users WHERE pseudo = '" + args[1] + "';", 1); //Retrieve target money int from DB
				            sender.sendMessage("§c[Points]§3 " + args[1] + " a " + playerTargetMoneyInt + " points !");
				            return true;
						}
				        else
				        {
				          sender.sendMessage("§c[Points]§3 " + args[1] + " n'est pas inscrit sur le site");
				        }
					}
					if (args[0].equalsIgnoreCase("send") && !args[1].equalsIgnoreCase("")) {
						sender.sendMessage("§c[Points]§3 /points send <joueur> <montant>");
					}
					if (args[0].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("")) {
						if (sender.hasPermission("points.set")) {
							sender.sendMessage("§c[Points]§3 /points set <joueur> <montant>");
						}
						else {
							sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
						}
					}
					if (args[0].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("")) {
						if (sender.hasPermission("points.add")) {
							sender.sendMessage("§c[Points]§3 /points add <joueur> <montant>");
						}
						else {
							sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
						}
					}
					if (args[0].equalsIgnoreCase("remove") && !args[1].equalsIgnoreCase("")) {
						if (sender.hasPermission("points.remove")) {
							sender.sendMessage("§c[Points]§3 /points remove <joueur> <montant>");
						}
						else {
							sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
						}
					}
				}
				else if(args.length == 3) {
					try {
						int intArgument = Integer.parseInt(args[2]);
						String gameSenderString = sender.getName(); //Retrieve the command sender in a string value
						String playerSenderString = this.bdd.getString("SELECT pseudo FROM users WHERE pseudo = '" + gameSenderString + "';", 1); //Retrieve the command sender in a string value from the DB
						String playerSenderMoneyString = this.bdd.getString("SELECT money FROM users WHERE pseudo = '" + gameSenderString + "';", 1); //Retrieve the command sender money in a string value from the DB 
						int playerSenderMoneyInt = Integer.parseInt(playerSenderMoneyString); //Retrieve the command sender money in an int value
						Player target = Bukkit.getServer().getPlayer(args[1]); //Retrieve the target as player entity
						String playerTargetString = this.bdd.getString("SELECT pseudo FROM users WHERE pseudo = '" + args[1] + "';", 1); //Retrieve the target in a string value from the DB
						String playerTargetMoneyString = this.bdd.getString("SELECT money FROM users WHERE pseudo = '" + args[1] + "';", 1); //Retrieve the target money in a string value
						int playerTargetMoneyInt = Integer.parseInt(playerTargetMoneyString); //Retrieve the target money in an int value
						if(args[0].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("") && intArgument >= 0) {
							if(sender.hasPermission("points.set")) {
								if(target != null && target.isOnline()) {
									if(playerTargetString.equalsIgnoreCase(args[1])) {
						                this.bdd.sendRequest("UPDATE users SET money = " + intArgument + " WHERE pseudo = '" + args[1] + "';");
						                sender.sendMessage("§c[Points]§3 Les points de " + args[1] + " ont été fixé à " + intArgument + " points !");
						                target.sendMessage("§c[Points]§3 Vos points viennent d'être fixé à " + intArgument + " points !");
									}
									else {
										sender.sendMessage("§c[Points]§3 Le joueur n'est pas inscrit sur le site");
									}
								}
								else {
									sender.sendMessage("§c[Points]§3 Le joueurs n'est pas connecté !");
								}
							}
							else {
								sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
							}
						}
						if (args[0].equalsIgnoreCase("send") && !args[1].equalsIgnoreCase("") && intArgument > 0) {
							 if(target != sender && target != null && target.isOnline()) {
								 if(playerSenderString.equalsIgnoreCase(gameSenderString)) {
									 if(playerTargetString.equalsIgnoreCase(args[1])) {
										 if(playerTargetMoneyInt >= intArgument) {
							                    int senderMoneyFinal = playerSenderMoneyInt - intArgument;
							                    this.bdd.sendRequest("UPDATE users SET money = " + senderMoneyFinal + " WHERE pseudo = '" + gameSenderString + "';");
							                    int playerFinale = this.bdd.getInt("SELECT money FROM users WHERE pseudo = '" + args[1] + "';", 1);
							                    int playerFinal = playerFinale + intArgument;
							                    this.bdd.sendRequest("UPDATE users SET money = " + playerFinal + " WHERE pseudo = '" + args[1] + "';");
							                    sender.sendMessage("§c[Points]§3 Tu avais " + playerSenderMoneyInt + " points !");
							                    sender.sendMessage("§c[Points]§3 Tu as maintenant " + senderMoneyFinal + " points !");
							                    target.sendMessage("§c[Points]§3 " + gameSenderString + " vient de te donner " + intArgument + " points !");
							                    target.sendMessage("§c[Points]§3 Tu as maintenant " + playerFinal + " points !");
										 }
										 else {
											 sender.sendMessage("§c[Points]§3 Tu n'as pas assez d'points !");
										 }
									 }
									 else {
										 sender.sendMessage("§c[Points]§3 Le joueur n'est pas inscrit sur le site");
									 }
								 }
								 else {
									 sender.sendMessage("§c[Points]§3 Tu n'es pas inscrit sur le site");
								 }
							 }
							 else if(target == sender) {
								 sender.sendMessage("§c[Points]§3 Tu ne peux pas t'envoyer des points toi même !");
							 }
							 else {
								 sender.sendMessage("§c[Points]§3 Le joueurs n'est pas connecté !");
							 }
						}
						else if (args[0].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("") && intArgument > 0) {
							if(sender.hasPermission("points.add")) {
								if(target != null && target.isOnline()) {
									if(playerTargetString.equalsIgnoreCase(args[1])) {
						                int playerFinal = playerTargetMoneyInt + intArgument;
						                this.bdd.sendRequest("UPDATE users SET money = " + playerFinal + " WHERE pseudo = '" + args[1] + "';");
						                target.sendMessage("§c[Points]§3 Tu viens de recevoir " + intArgument + " points !");
						                target.sendMessage("§c[Points]§3 Tu as maintenant " + playerFinal + " points !");
									}
									else {
										sender.sendMessage("§c[Points]§3 Le joueur n'est pas inscrit sur le site");
									}
								}
								else {
									sender.sendMessage("§c[Points]§3 Le joueurs n'est pas connecté !");
								}
							}
							else {
								sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
							}
						}
						else if (args[0].equalsIgnoreCase("remove") && !args[1].equalsIgnoreCase("") && intArgument > 0) {
							if(sender.hasPermission("points.remove")) {
								if(target != null && target.isOnline()) {
									if(playerTargetString.equalsIgnoreCase(args[1])) {
										if(playerTargetMoneyInt > 0 && playerTargetMoneyInt >= intArgument) {
						                    int playerFinal = playerTargetMoneyInt - intArgument;
						                    this.bdd.sendRequest("UPDATE users SET money = " + playerFinal + " WHERE pseudo = '" + args[1] + "';");
						                    target.sendMessage("§c[Points]§3 Tu viens de perdre " + intArgument + " points !");
						                    target.sendMessage("§c[Points]§3 Tu as maintenant " + playerFinal + " points !");	
										}
									}
									else {
										sender.sendMessage("§c[Points]§3 Le joueur n'est pas inscrit sur le site");
									}
								}
								else {
									sender.sendMessage("§c[Points]§3 Le joueurs n'est pas connecté !");
								}
							}
							else {
								sender.sendMessage("§c[Points]§3 Vous n'avez pas la permission de faire cela !");
							}
						}
						else if(playerTargetMoneyInt == 0) {
							sender.sendMessage("§c[Points]§3 " + target.getName() + " n'a plus d'points !");	
						}
					}
					catch(NumberFormatException ex) {
						sender.sendMessage("§c[Points]§3 Veuillez entrer le montant sous forme de chiffres  !");
					}
				}
			}
		}
		else if(sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender){
			try {
				if(cmd.getName().equalsIgnoreCase("points")) {
					if(args.length == 0) {
						sender.sendMessage("§3/points check [joueur] - Affiche les points boutique actuels.");
						sender.sendMessage("§3/points send <joueur> <montant> - Envoyer ses points à un autre joueur.");
						sender.sendMessage("§3/points set <joueur> <montant> - Fixer les points d'un joueur.");
						sender.sendMessage("§3/points add <joueur> <montant> - Ajouter des points à un joueur.");
						sender.sendMessage("§3/points remove <joueur> <montant> - Retirer des points à un joueur.");
						//sender.sendMessage("§3/points shop  - Ouvre le shop en jeu.");
					}
					else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("check")) {
							sender.sendMessage("§c[Points]§3 Pour utiliser cette commande, veuillez indiquer le pseudo d'un joueur !");
							sender.sendMessage("§c[Points]§3 /points check <joueur>");
							return true;
						}
						if (args[0].equalsIgnoreCase("send")) {
							sender.sendMessage("§c[Points]§3 Cette commande n'est utilisable qu'en jeu !");
							return true;
						}
						if (args[0].equalsIgnoreCase("set")) {
							sender.sendMessage("§c[Points]§3 /points set <joueur> <montant>");
							return true;
						}
						if (args[0].equalsIgnoreCase("add")) {
							sender.sendMessage("§c[Points]§3 /points add <joueur> <montant>");
						}
						if (args[0].equalsIgnoreCase("remove")) {
							sender.sendMessage("§c[Points]§3 /points remove <joueur> <montant>");
						}
						/*if (args[0].equalsIgnoreCase("shop"))
			         	{
			           		sender.sendMessage("§c[Points]§3 Cette commande n'est utilisable qu'en jeu !");
			       		}*/
						else {
							sender.sendMessage("§3/points check <joueur> - Affiche les points boutique actuels.");
							sender.sendMessage("§3/points set <joueur> <montant> - Fixer les points d'un joueur.");
							sender.sendMessage("§3/points add <joueur> <montant> - Ajouter des points à un joueur.");
							sender.sendMessage("§3/points remove <joueur> <montant> - Retirer des points à un joueur.");
							//sender.sendMessage("§3/points shop  - Ouvre le gui du shop en jeu.");
						}
					}
					else if(args.length == 2) {
						if(args[0].equalsIgnoreCase("check")) {
							String playerTargetString = this.bdd.getString("SELECT pseudo FROM users WHERE pseudo = '" + args[1] + "';", 1); //Retrieve target string from DB
							if(playerTargetString.equalsIgnoreCase(args[1])) {
								int playerTargetMoneyInt = this.bdd.getInt("SELECT money FROM users WHERE pseudo = '" + args[1] + "';", 1); //Retrieve target money int from DB
								sender.sendMessage("§c[Points]§3 " + args[1] + " a " + playerTargetMoneyInt + " points !");
								return true;
							}
							else
							{
								sender.sendMessage("§c[Points]§3 " + args[1] + " n'est pas inscrit sur le site");
							}
						}
						if (args[0].equalsIgnoreCase("send") && !args[1].equalsIgnoreCase("")) {
							sender.sendMessage("§c[Points]§3 Cette commande n'est utilisable qu'en jeu !");
						}
						if (args[0].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("")) {
							sender.sendMessage("§c[Points]§3 /points set <joueur> <montant>");
						}
						if (args[0].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("")) {
							sender.sendMessage("§c[Points]§3 /points add <joueur> <montant>");
						}
						if (args[0].equalsIgnoreCase("remove") && !args[1].equalsIgnoreCase("")) {
							sender.sendMessage("§c[Points]§3 /points remove <joueur> <montant>");
						}
					}
					else if(args.length == 3) {
						int intArgument = Integer.parseInt(args[2]); 
						Player target = Bukkit.getServer().getPlayer(args[1]); //Retrieve the target as player entity
						String playerTargetString = this.bdd.getString("SELECT pseudo FROM users WHERE pseudo = '" + args[1] + "';", 1); //Retrieve the target in a string value from the DB
						String playerTargetMoneyString = this.bdd.getString("SELECT money FROM users WHERE pseudo = '" + args[1] + "';", 1); //Retrieve the target money in a string value
						int playerTargetMoneyInt = Integer.parseInt(playerTargetMoneyString); //Retrieve the target money in an int value
						if(args[0].equalsIgnoreCase("set") && !args[1].equalsIgnoreCase("") && intArgument >= 0) {
							if(target != null && target.isOnline()) {
								if(playerTargetString.equalsIgnoreCase(args[1])) {
									this.bdd.sendRequest("UPDATE users SET money = " + intArgument + " WHERE pseudo = '" + args[1] + "';");
									sender.sendMessage("§c[Points]§3 Les points de " + args[1] + " ont été fixé à " + intArgument + " points !");
									target.sendMessage("§c[Points]§3 Vos points viennent d'être fixé à " + intArgument + " points !");
								}
								else {
									sender.sendMessage("§c[Points]§3 Le joueur n'est pas inscrit sur le site");
								}	
							}
							else {
								sender.sendMessage("§c[Points]§3 Le joueurs n'est pas connecté !");
							}	
						}
						if (args[0].equalsIgnoreCase("send")) {
							sender.sendMessage("§c[Points]§3 Cette commande n'est utilisable qu'en jeu !");
						}
						else if (args[0].equalsIgnoreCase("add") && !args[1].equalsIgnoreCase("") && intArgument > 0) {
							if(target != null && target.isOnline()) {
								if(playerTargetString.equalsIgnoreCase(args[1])) {
									int playerFinal = playerTargetMoneyInt + intArgument;
									this.bdd.sendRequest("UPDATE users SET money = " + playerFinal + " WHERE pseudo = '" + args[1] + "';");
									target.sendMessage("§c[Points]§3 Tu viens de recevoir " + intArgument + " points !");
									target.sendMessage("§c[Points]§3 Tu as maintenant " + playerFinal + " points !");
								}	
								else {
									sender.sendMessage("§c[Points]§3 Le joueur n'est pas inscrit sur le site");
								}	
							}
							else {
								sender.sendMessage("§c[Points]§3 Le joueurs n'est pas connecté !");
							}
						}
						else if (args[0].equalsIgnoreCase("remove") && !args[1].equalsIgnoreCase("") && intArgument > 0) {
							if(target != null && target.isOnline()) {
								if(playerTargetString.equalsIgnoreCase(args[1])) {
									if(playerTargetMoneyInt > 0 && playerTargetMoneyInt >= intArgument) {
										int playerFinal = playerTargetMoneyInt - intArgument; 
										this.bdd.sendRequest("UPDATE users SET money = " + playerFinal + " WHERE pseudo = '" + args[1] + "';");
										target.sendMessage("§c[Points]§3 Tu viens de perdre " + intArgument + " points !");
										target.sendMessage("§c[Points]§3 Tu as maintenant " + playerFinal + " points !");	
									}	
								}	
								else {
									sender.sendMessage("§c[Points]§3 Le joueur n'est pas inscrit sur le site");
								}	
							}	
							else {
								sender.sendMessage("§c[Points]§3 Le joueurs n'est pas connecté !");
							}
						}
						else if(playerTargetMoneyInt == 0) {
							sender.sendMessage("§c[Points]§3 " + target.getName() + " n'a plus d'points !");	
						}
					}
				}
			}
			catch(NumberFormatException ex) {
				sender.sendMessage("§c[Points]§3 Veuillez inscrire le montant sous forme de chiffre !");
			}
		}
		return false;
	}
}