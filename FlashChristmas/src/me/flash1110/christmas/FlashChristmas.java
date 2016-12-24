package me.flash1110.christmas;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FlashChristmas
  extends JavaPlugin
{
  public void onDisable()
  {
    System.out.println("[FlashPresent] FlashPresent v" + 
      getDescription().getVersion() + " disabled!");
  }
  
  public void onEnable()
  {
    System.out.println("[FlashPresent] FlashPresent v" + 
      getDescription().getVersion() + " enabled!");
    
    loadConfiguration();
  }
  
  public void loadConfiguration()
  {
    getConfig().addDefault("Information", "This configuration is only to store data!");
    
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
  
  public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
  {
    String commandName = command.getName().toLowerCase();
    if (commandName.equals("present"))
    {
      if (args.length == 2)
      {
        String first = args[0];
        if (first.equalsIgnoreCase("info"))
        {
          String second = args[1];
          
          Player getplayer = Bukkit.getPlayer(second);
          if (getplayer != null) {
            second = getplayer.getName();
          }
          if (getConfig().contains("Player." + second.toLowerCase() + ".used"))
          {
            int usedamount = getConfig().getInt(
              "Player." + second.toLowerCase() + ".used");
            int maxandextra = getConfig().getInt(
              "Player." + second.toLowerCase() + ".lastmax") + 
              getConfig().getInt(
              "Player." + second.toLowerCase() + 
              ".extra");
            
            sender.sendMessage("§b" + second + " §ehas used §d" + 
              usedamount + "§7/§d" + maxandextra + 
              " §eof their chests");
            sender.sendMessage("§2To see what they have opened so far use");
            sender.sendMessage("§7'§6/present info <player> <pagenumber>§7'");
          }
          else
          {
            sender.sendMessage("§b" + second + 
              " §chas used no presents or does not exist");
          }
        }
        else
        {
          sender.sendMessage("§cDid you mean /present info <player>");
        }
      }
      if (args.length == 3)
      {
        String first = args[0];
        boolean truee = false;
        if (first.equalsIgnoreCase("info"))
        {
          truee = true;
          
          String second = args[1];
          
          Player getplayer = Bukkit.getPlayer(second);
          if (getplayer != null) {
            second = getplayer.getName();
          }
          if (getConfig().contains("Player." + second.toLowerCase() + ".used"))
          {
            int usedamount = getConfig().getInt(
              "Player." + second.toLowerCase() + ".used");
            int maxandextra = getConfig().getInt(
              "Player." + second.toLowerCase() + ".lastmax") + 
              getConfig().getInt(
              "Player." + second.toLowerCase() + 
              ".extra");
            
            sender.sendMessage("§b" + second + " §ehas used §d" + 
              usedamount + "§7/§d" + maxandextra + 
              " §eof their presents");
            
            String third = args[2];
            try
            {
              int idint = Integer.parseInt(third);
              if (idint < 1)
              {
                sender.sendMessage(ChatColor.RED + 
                  "Page Number can not be less than 1");
                return false;
              }
              List<String> itemlist = getConfig()
                .getStringList(
                "Player." + second.toLowerCase() + 
                ".items");
              
              int pagenumber = 1;
              
              int amounttotakeaway = 10;
              
              int itemlistsize = itemlist.size();
              while (itemlistsize > amounttotakeaway)
              {
                itemlistsize -= 10;
                pagenumber++;
              }
              if (idint > pagenumber) {
                idint = pagenumber;
              }
              int vaulemax = idint * 10;
              int vaulemin = idint * 10 - 10;
              while (vaulemin < vaulemax)
              {
                if (itemlist.size() > vaulemin)
                {
                  String converteditemmessage = 
                    ((String)itemlist.get(vaulemin)).replace("§0", "§0")
                    .replace("§1", "§1")
                    .replace("§2", "§2")
                    .replace("§3", "§3")
                    .replace("§4", "§4")
                    .replace("§5", "§5")
                    .replace("§6", "§6")
                    .replace("§7", "§7")
                    .replace("§8", "§8")
                    .replace("§9", "§9")
                    .replace("§a", "§a")
                    .replace("§b", "§b")
                    .replace("§c", "§c")
                    .replace("§d", "§d")
                    .replace("§e", "§e")
                    .replace("§f", "§f");
                  
                  sender.sendMessage(converteditemmessage);
                }
                vaulemin++;
              }
              sender.sendMessage("§7Viewing Page §5" + idint + 
                " §7out of §5" + pagenumber);
              return true;
            }
            catch (NumberFormatException e)
            {
              sender.sendMessage(ChatColor.RED + 
                "Page Number must be a whole number");
            }
          }
          sender.sendMessage("§b" + second + 
            " §chas used no presents or does not exist");
        }
        if (first.equalsIgnoreCase("removeextra"))
        {
          truee = true;
          if (sender.hasPermission("flashpresent.admin"))
          {
            String second = args[1];
            
            System.out
              .println("[FlashPresent] Attempting to remove extra presents from player " + 
              second);
            if (getConfig().contains("Player." + second.toLowerCase()))
            {
              String third = args[2];
              try
              {
                int idint = Integer.parseInt(third);
                if (idint < 0)
                {
                  sender.sendMessage(ChatColor.RED + 
                    "Amount can not be less than 0");
                  return false;
                }
                int currentextra = getConfig().getInt(
                  "Player." + second.toLowerCase() + 
                  ".extra");
                
                int settoextra = currentextra - idint;
                
                getConfig().set(
                  "Player." + second.toLowerCase() + 
                  ".extra", Integer.valueOf(settoextra));
                saveConfig();
                
                Player onlineplayer = Bukkit.getPlayer(second);
                if (onlineplayer != null) {
                  onlineplayer.sendMessage("§6You had §d" + 
                    idint + 
                    " §6taken from your max present allowance");
                }
                sender.sendMessage(ChatColor.GOLD + 
                  "Removed §d" + idint + 
                  " §6from player §a" + second + 
                  " §6present allowance");
              }
              catch (NumberFormatException e)
              {
                sender.sendMessage(ChatColor.RED + 
                  "Ammount must be a whole number");
              }
              return true;
            }
            sender.sendMessage("§cPlayer§a " + second + 
              " §chas not yet claimed any presents");
          }
          else
          {
            sender.sendMessage(ChatColor.RED + 
              "You lack permission to do that");
          }
        }
        if (first.equalsIgnoreCase("addextra"))
        {
          truee = true;
          if (sender.hasPermission("flashpresent.admin"))
          {
            String second = args[1];
            
            System.out
              .println("[FlashPresent] Attemtping to add extra presents to player " + 
              second);
            if (getConfig().contains("Player." + second.toLowerCase()))
            {
              String third = args[2];
              try
              {
                int idint = Integer.parseInt(third);
                if (idint < 0)
                {
                  sender.sendMessage(ChatColor.RED + 
                    "Amount can not be less than 0");
                  return false;
                }
                int currentextra = getConfig().getInt(
                  "Player." + second.toLowerCase() + 
                  ".extra");
                
                int settoextra = currentextra + idint;
                
                getConfig().set(
                  "Player." + second.toLowerCase() + 
                  ".extra", Integer.valueOf(settoextra));
                saveConfig();
                
                Player onlineplayer = Bukkit.getPlayer(second);
                if (onlineplayer != null) {
                  onlineplayer.sendMessage("§6You had §d" + 
                    idint + 
                    " §6added to your max present allowance");
                }
                sender.sendMessage(ChatColor.GOLD + "Added §d" + 
                  idint + " §6to player §a" + second + 
                  " §6present allowance");
              }
              catch (NumberFormatException e)
              {
                sender.sendMessage(ChatColor.RED + 
                  "Amount must be a whole number");
              }
            }
            sender.sendMessage("§cPlayer§a " + second + 
              " §chas not yet claimed any presents");
          }
          else
          {
            sender.sendMessage(ChatColor.RED + 
              "You lack permission to do that");
          }
        }
        if (first.equalsIgnoreCase("set"))
        {
          truee = true;
          if (sender.hasPermission("flashpresent.admin"))
          {
            String second = args[1];
            if (getConfig().contains("Player." + second.toLowerCase()))
            {
              String third = args[2];
              try
              {
                int idint = Integer.parseInt(third);
                if (idint < 0)
                {
                  sender.sendMessage(ChatColor.RED + 
                    "Amount can not be less than 0");
                  return false;
                }
                getConfig().set(
                  "Player." + second.toLowerCase() + 
                  ".used", Integer.valueOf(idint));
                saveConfig();
                
                sender.sendMessage(ChatColor.GOLD + 
                  "Set player §a" + second + 
                  " §6current used presents to §d" + 
                  idint);
              }
              catch (NumberFormatException e)
              {
                sender.sendMessage(ChatColor.RED + 
                  "Ammount must be a whole number");
                sender.sendMessage(ChatColor.RED + 
                  "§6Correct usage: §7/§bpresent set §7<§aplayername§7> <§aamount§7>");
              }
            }
            sender.sendMessage("§cPlayer§a " + second + 
              " §chas not yet claimed any presents");
          }
          else
          {
            sender.sendMessage("§cYou lack permission to do that");
          }
        }
        if (!truee)
        {
          sender.sendMessage("§cDid you mean /present info <player> <pagenumber>");
          sender.sendMessage("§cDid you mean /present addextra <player> <amount>");
          sender.sendMessage("§cDid you mean /present removeextra <player> <amount>");
          sender.sendMessage("§cDid you mean /present set <player> <amount>");
        }
      }
      if ((sender instanceof Player))
      {
        Player player = (Player)sender;
        if (args.length == 0)
        {
          sender.sendMessage("§6Open a present with §a/present open");
          sender.sendMessage("§6View info with §a/present info <player> <pagenumber>");
          if (player.hasPermission("flashpresent.admin"))
          {
            sender.sendMessage("§6Add §bextra§6 with §a/present addextra <p> <#>");
            sender.sendMessage("§6Remove §bextra§6 with §a/present removeextra <p> <#>");
            sender.sendMessage("§6Set the §2used§6 amount with §a/present set <p> <#>");
          }
        }
        if (args.length == 1)
        {
          String first = args[0];
          if ((first.equalsIgnoreCase("open")) || (first.equalsIgnoreCase("claim")))
          {
            if (player.hasPermission("flashpresent.open"))
            {
              if (getConfig().contains("Player." + player.getName().toLowerCase() + ".used"))
              {
                int currentamount = getConfig().getInt(
                  "Player." + 
                  player.getName()
                  .toLowerCase() + 
                  ".used");
                
                int maxallowed = 3;
                if (player.hasPermission("flash.rank.soldier")) {
                  maxallowed = 5;
                }
                if (player.hasPermission("flash.rank.knight")) {
                  maxallowed = 10;
                }
                if (player.hasPermission("flash.rank.general")) {
                  maxallowed = 15;
                }
                if (player.hasPermission("flash.rank.king")) {
                  maxallowed = 30;
                }
                if (player.hasPermission("flash.rank.emperor")) {
                  maxallowed = 50;
                }
                if (player.hasPermission("flash.rank.end")) {
                  maxallowed = 75;
                }
                int extra = getConfig().getInt(
                  "Player." + 
                  player.getName()
                  .toLowerCase() + 
                  ".extra");
                
                int maxallowedandextra = extra + maxallowed;
                if (currentamount < maxallowedandextra)
                {
                  if (player.getInventory().firstEmpty() < 0)
                  {
                    player.sendMessage("§cYou need at §a§nleast§c one space in your inventory free");
                    return false;
                  }
                  CreatePresent.GivePresent(this, player, 
                    Integer.valueOf(maxallowed), Integer.valueOf(currentamount), 
                    Integer.valueOf(extra));
                }
                else
                {
                  player.sendMessage(ChatColor.RED + 
                    "You have used your maxium amount of presents §7(" + 
                    ChatColor.LIGHT_PURPLE + 
                    maxallowedandextra + "§7)");
                }
              }
              else
              {
                getConfig().set(
                  "Player." + 
                  player.getName()
                  .toLowerCase() + 
                  ".used", Integer.valueOf(0));
                if (!getConfig().contains("Player." + player.getName().toLowerCase() + ".extra")) {
                  getConfig().set(
                    "Player." + 
                    player.getName()
                    .toLowerCase() + 
                    ".extra", Integer.valueOf(0));
                }
                int extra = getConfig().getInt(
                  "Player." + 
                  player.getName()
                  .toLowerCase() + 
                  ".extra");
                
                int maxallowed = 3;
                if (player.hasPermission("flash.rank.soldier")) {
                  maxallowed = 5;
                }
                if (player.hasPermission("flash.rank.knight")) {
                  maxallowed = 10;
                }
                if (player.hasPermission("flash.rank.general")) {
                  maxallowed = 15;
                }
                if (player.hasPermission("flash.rank.king")) {
                  maxallowed = 30;
                }
                if (player.hasPermission("flash.rank.emperor")) {
                  maxallowed = 50;
                }
                if (player.hasPermission("flash.rank.end")) {
                  maxallowed = 75;
                }
                CreatePresent.GivePresent(this, player, 
                  Integer.valueOf(maxallowed), Integer.valueOf(0), Integer.valueOf(extra));
              }
            }
            else {
              player.sendMessage(ChatColor.RED + 
                "You lack permission to do that");
            }
          }
          else {
            player.sendMessage(ChatColor.RED + 
              "Did you mean /present open");
          }
        }
      }
    }
    return false;
  }
}

