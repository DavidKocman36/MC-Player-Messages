package plugin.playermessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.playermessages.database.DatabaseManager;
import plugin.playermessages.listeners.PlayerDeathListener;
import plugin.playermessages.listeners.PlayerJoinedListener;
import plugin.playermessages.listeners.PlayerLeftListener;

public final class PlayerMessages extends JavaPlugin {

    @Override
    public void onEnable() {
        //new dbmanager
        DatabaseManager db = new DatabaseManager(this);
        getServer().getPluginManager().registerEvents(new PlayerJoinedListener(db), this);
        getServer().getPluginManager().registerEvents(new PlayerLeftListener(db), this);

        this.saveDefaultConfig();
        db.parseYamlDb();
        Bukkit.getLogger().info("[PlayerMessages] The plugin has started!");
    }
}
