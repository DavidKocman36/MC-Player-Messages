package plugin.playermessages.listeners;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import plugin.playermessages.database.DatabaseManager;
import plugin.playermessages.utils.Config;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerDeathListener implements Listener {
    private final DatabaseManager db;
    public PlayerDeathListener(DatabaseManager db) {
        this.db = db;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        // if player is in database
        List<String> messages;
        String message;
        if((message = this.db.getData(e.getEntity().getDisplayName(), Config.mess_death_column)) != null){
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            messages = new Gson().fromJson(message, listType);
            if(!messages.isEmpty()) {
                this.printMessage(messages, e);
            }
        }
        else {
            // else if he is not
            messages = this.db.getDeath_mess();
            if (!messages.isEmpty()) {
                this.printMessage(messages, e);
            }
        }
    }

    private void printMessage(List<String> messages, PlayerDeathEvent e)
    {
        Pattern p1 = Pattern.compile("^.*%player%.*$");
        Pattern p2 = Pattern.compile("^.*%entity%.*$");
        Random rand = new Random();
        String mess = messages.get(rand.nextInt(messages.size()));
        Matcher m1 = p1.matcher(mess);
        Matcher m2 = p2.matcher(mess);
        String deathCause;

        if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)
        {
            EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
            deathCause = nEvent.getDamager().getName();
        }
        else if (e.getEntity().getKiller() != null)
        {
            deathCause = e.getEntity().getKiller().getDisplayName();
        }
        else {
            deathCause =  e.getEntity().getLastDamageCause().getCause().toString().toLowerCase();
        }

        if(m1.matches() || m2.matches()){
            mess = mess.replace("%player%", e.getEntity().getDisplayName());
            mess = mess.replace("%entity%", deathCause);
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&e"+ mess));
        }
        else {
            e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', "&e" + e.getEntity().getDisplayName() + " " + mess));
        }
    }
}
