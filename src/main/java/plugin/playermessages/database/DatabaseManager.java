package plugin.playermessages.database;

import com.google.gson.Gson;
import plugin.playermessages.PlayerMessages;

import java.util.List;
import java.util.Set;

public class DatabaseManager {
    private final PlayerMessages plugin;
    private final IDatabase implementor;
    private List<String> join_mess;
    private List<String> leave_mess;

    public List<String> getJoin_mess() {
        return join_mess;
    }

    public List<String> getLeave_mess() {
        return leave_mess;
    }

    public List<String> getDeath_mess() {
        return death_mess;
    }

    private List<String> death_mess;
    public DatabaseManager(PlayerMessages plugin, IDatabase implementor){
        this.plugin = plugin;
        this.implementor = implementor;

        if (!this.plugin.getDataFolder().exists())
        {
            this.plugin.getDataFolder().mkdirs();
        }

        this.implementor.createConnection();
        this.implementor.createTable();
    }

    public void parseYamlDb(){
        // Does not have to be in Database - small amount
        this.join_mess = this.plugin.getConfig().getStringList("join-messages");
        this.leave_mess = this.plugin.getConfig().getStringList("leave-messages");
        this.death_mess = this.plugin.getConfig().getStringList("death-messages");

        // Has to be in DB for more optimal search in case a lot of players
        // has custom messages
        Set<String> keys = this.plugin.getConfig().getConfigurationSection("players").getKeys(false);
        Gson gson = new Gson();

        for (String i : keys) {
            // Write to table
            List<String> join_mess = this.plugin.getConfig().getStringList("players."+i+".join-messages");
            List<String> leave_mess = this.plugin.getConfig().getStringList("players."+i+".leave-messages");
            List<String> death_mess = this.plugin.getConfig().getStringList("players."+i+".death-messages");

            this.implementor.insertData(gson, i, join_mess,  leave_mess,  death_mess);
        }
    }

    public void closeConnection(){
        this.implementor.closeConnection();
    }
    public String getData(String player, String column) {
        return this.implementor.getData(player, column);
    }
}
