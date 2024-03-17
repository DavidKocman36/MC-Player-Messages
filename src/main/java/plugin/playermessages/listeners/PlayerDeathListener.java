package plugin.playermessages.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import plugin.playermessages.database.DatabaseManager;

public class PlayerDeathListener implements Listener {
    private final DatabaseManager db;
    public PlayerDeathListener(DatabaseManager db) {
        this.db = db;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {

    }
}
