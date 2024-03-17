package plugin.playermessages.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import plugin.playermessages.database.DatabaseManager;

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
        // else if he is not
        List<String> messages = this.db.getJoin_mess();
        Random rand = new Random();
        String mess = messages.get(rand.nextInt(messages.size()));
        e.setJoinMessage(e.getPlayer().getDisplayName() + " " + mess);
    }
}
