package plugin.playermessages.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import plugin.playermessages.database.DatabaseManager;

public class PlayerJoinedListener implements Listener {

    private final DatabaseManager db;
    public PlayerJoinedListener(DatabaseManager db) {
        this.db = db;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Bukkit.getLogger().info(e.getPlayer().getDisplayName() + " jee tu!");
    }
}
