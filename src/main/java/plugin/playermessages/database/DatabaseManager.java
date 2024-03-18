package plugin.playermessages.database;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import plugin.playermessages.PlayerMessages;
import plugin.playermessages.utils.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatabaseManager {
    private final PlayerMessages plugin;
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
    private final Connection connection;
    public DatabaseManager(PlayerMessages plugin) throws SQLException {
        this.plugin = plugin;

        if (!this.plugin.getDataFolder().exists())
        {
            this.plugin.getDataFolder().mkdirs();
        }
        this.connection = DriverManager.getConnection(
                "jdbc:sqlite:" + this.plugin.getDataFolder().getAbsolutePath() + Config.filename
        );

        try (Statement statement = this.connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS " + Config.players_table + ";");
            statement.execute("CREATE TABLE IF NOT EXISTS " + Config.players_table + " (" +
                    Config.player_colum + " TEXT PRIMARY KEY, " +
                    Config.mess_join_column + " TEXT NOT NULL," +
                    Config.mess_leave_column + " TEXT NOT NULL," +
                    Config.mess_death_column + " TEXT NOT NULL);"
            );
        }
    }
    public void closeConnection() throws SQLException {
        if(this.connection != null && !this.connection.isClosed()){
            this.connection.close();
        }
    }

    public void parseYamlDb() throws SQLException {
        // Does not have to be in Database
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

            try(Statement statement = this.connection.createStatement()){
                statement.execute("INSERT INTO "+ Config.players_table +" ("+Config.player_colum +","+ Config.mess_join_column +","+ Config.mess_leave_column +","+ Config.mess_death_column + ")" +
                        " VALUES('"+i+"','"+gson.toJson(join_mess)+"','"+gson.toJson(leave_mess)+"','"+gson.toJson(death_mess)+"');"
                );
            }
        }
    }

    public String getData(String player, String column) {
        String result = "";
        try(Statement statement = this.connection.createStatement()){
            ResultSet res = statement.executeQuery("SELECT " + column + " FROM "+ Config.players_table +
                    " WHERE " + Config.player_colum + "='" + player + "'"
            );
            while (res.next()){
                result = res.getString(column);
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info("[PlayerMessages] Error retrieving data: " + e.getMessage());
        }
        return result.isEmpty() ? null : result;
    }
}
