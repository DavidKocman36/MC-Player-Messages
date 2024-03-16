package plugin.playermessages.listeners;

import org.bukkit.event.Listener;
import plugin.playermessages.database.DatabaseManager;

public class PlayerLeftListener implements Listener {
    private final DatabaseManager db;
    public PlayerLeftListener(DatabaseManager db) {
        this.db = db;
    }
}
