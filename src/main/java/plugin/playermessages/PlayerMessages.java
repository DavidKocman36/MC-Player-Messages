package plugin.playermessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.playermessages.database.DatabaseManager;
import plugin.playermessages.database.SQLiteDriver;
import plugin.playermessages.listeners.PlayerDeathListener;
import plugin.playermessages.listeners.PlayerJoinedListener;
import plugin.playermessages.listeners.PlayerLeftListener;

public final class PlayerMessages extends JavaPlugin {

    private DatabaseManager db = null;
    @Override
    public void onEnable() {
        // Change the dependency in the constructor if other db is used
        this.db = new DatabaseManager(this, new SQLiteDriver(this));

        getServer().getPluginManager().registerEvents(new PlayerJoinedListener(this.db), this);
        getServer().getPluginManager().registerEvents(new PlayerLeftListener(this.db), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this.db), this);

        this.saveDefaultConfig();
        db.parseYamlDb();

        Bukkit.getLogger().info("[PlayerMessages] The plugin has started!");
    }

    @Override
    public void onDisable() {
        this.db.closeConnection();
        Bukkit.getLogger().info("[PlayerMessages] The plugin has been disabled!");
    }
}
