package plugin.playermessages.database;

import org.bukkit.Bukkit;
import plugin.playermessages.PlayerMessages;
import java.util.List;
import java.util.Set;

public class DatabaseManager {
    // CRUD
    // Create db and parse yaml to db
    // Read join/leave/death messages
    //private dbHandler;
    private final PlayerMessages plugin;
    public DatabaseManager(PlayerMessages plugin)
    {
        this.plugin = plugin;
    }

    public PlayerMessages getPlugin() {
        return plugin;
    }

    public void parseYamlDb()
    {
        List<String> join_mess = this.plugin.getConfig().getStringList("join-messages");
        List<String> leave_mess = this.plugin.getConfig().getStringList("leave-messages");
        List<String> death_mess = this.plugin.getConfig().getStringList("death-messages");
        // Write to tables

        Set<String> keys = this.plugin.getConfig().getConfigurationSection("players").getKeys(false);
        for (String i : keys) {
            Bukkit.getLogger().info("key: " + i);
            // Write to table
        }

    }

}
