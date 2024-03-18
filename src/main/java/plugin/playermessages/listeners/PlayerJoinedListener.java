package plugin.playermessages.listeners;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import plugin.playermessages.database.DatabaseManager;
import plugin.playermessages.utils.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerJoinedListener implements Listener {

    private final DatabaseManager db;
    public PlayerJoinedListener(DatabaseManager db) {
        this.db = db;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        // if player is in database
        List<String> messages;
        String message;
        if((message = this.db.getData(e.getPlayer().getDisplayName(), Config.mess_join_column)) != null){
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            messages = new Gson().fromJson(message, listType);
            this.printMessage(messages, e);
        }
        else {
            // else if he is not
            messages = this.db.getJoin_mess();
            if (!messages.isEmpty()) {
                this.printMessage(messages, e);
            }
        }
    }

    private void printMessage(List<String> messages, PlayerJoinEvent e)
    {
        //^.*%player%.*$
        Pattern p = Pattern.compile("^.*%player%.*$");
        Random rand = new Random();
        String mess = messages.get(rand.nextInt(messages.size()));
        Matcher m = p.matcher(mess);
        if(m.matches()){
            mess = mess.replace("%player%", e.getPlayer().getDisplayName());
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&e"+ mess));
        }
        else {
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&e" + e.getPlayer().getDisplayName() + " " + mess));
        }
    }
}
