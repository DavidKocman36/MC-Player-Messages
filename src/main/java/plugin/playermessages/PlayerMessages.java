package plugin.playermessages;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.playermessages.listeners.PlayerDeathListener;
import plugin.playermessages.listeners.PlayerJoinedListener;

public final class PlayerMessages extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoinedListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);

        Bukkit.getLogger().info("[PlayerMessages] The plugin has started!");
    }
}
