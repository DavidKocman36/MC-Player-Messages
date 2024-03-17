package plugin.playermessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.playermessages.database.DatabaseManager;
import plugin.playermessages.listeners.PlayerDeathListener;
import plugin.playermessages.listeners.PlayerJoinedListener;
import plugin.playermessages.listeners.PlayerLeftListener;

import java.sql.SQLException;

public final class PlayerMessages extends JavaPlugin {

    private DatabaseManager db = null;
    @Override
    public void onEnable() {
        try {
            this.db = new DatabaseManager(this);
        } catch (SQLException e) {
            Bukkit.getLogger().info("[PlayerMessages] Error accessing the database: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        getServer().getPluginManager().registerEvents(new PlayerJoinedListener(this.db), this);
        getServer().getPluginManager().registerEvents(new PlayerLeftListener(this.db), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this.db), this);

        this.saveDefaultConfig();

        try {
            db.parseYamlDb();
        } catch (SQLException e) {
            Bukkit.getLogger().info("[PlayerMessages] Error parsing config: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getLogger().info("[PlayerMessages] The plugin has started!");
    }

    @Override
    public void onDisable() {
        try {
            this.db.closeConnection();
        } catch (SQLException e) {
            Bukkit.getLogger().info("[PlayerMessages] Error closing database: " + e.getMessage());
        }
        Bukkit.getLogger().info("[PlayerMessages] The plugin has been disabled!");
    }
}
