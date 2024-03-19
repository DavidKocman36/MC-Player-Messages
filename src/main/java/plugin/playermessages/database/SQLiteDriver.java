package plugin.playermessages.database;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import plugin.playermessages.PlayerMessages;
import plugin.playermessages.utils.Config;

import java.sql.*;
import java.util.List;

public class SQLiteDriver implements IDatabase{
    private Connection connection;
    private final PlayerMessages plugin;

    public SQLiteDriver(PlayerMessages plugin){
        this.plugin = plugin;
    }
    @Override
    public void createConnection() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + this.plugin.getDataFolder().getAbsolutePath() + Config.filename
            );
        } catch (SQLException e) {
            Bukkit.getLogger().info("[PlayerMessages] " + e.getMessage() + " Continuing without db.");
        }
    }

    @Override
    public void createTable() {
        if(this.connection == null) return;

        try (Statement statement = this.connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS " + Config.players_table + ";");
            statement.execute("CREATE TABLE IF NOT EXISTS " + Config.players_table + " (" +
                    Config.player_colum + " TEXT PRIMARY KEY, " +
                    Config.mess_join_column + " TEXT NOT NULL," +
                    Config.mess_leave_column + " TEXT NOT NULL," +
                    Config.mess_death_column + " TEXT NOT NULL);"
            );
        } catch (SQLException e) {
            Bukkit.getLogger().info("[PlayerMessages] " + e.getMessage() + " Continuing without db.");
        }
    }
    @Override
    public void closeConnection(){
        try {
            if(this.connection != null && !this.connection.isClosed()){
                this.connection.close();
            }
        } catch (SQLException e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }

    @Override
    public void insertData(Gson gson, String i, List<String> join_mess, List<String> leave_mess, List<String> death_mess) {
        if(this.connection == null) return;

        try(Statement statement = this.connection.createStatement()){
            statement.execute("INSERT INTO "+ Config.players_table +" ("+Config.player_colum +","+ Config.mess_join_column +","+ Config.mess_leave_column +","+ Config.mess_death_column + ")" +
                    " VALUES('"+i+"','"+gson.toJson(join_mess)+"','"+gson.toJson(leave_mess)+"','"+gson.toJson(death_mess)+"');"
            );
        } catch (SQLException e) {
            Bukkit.getLogger().info("[PlayerMessages] " + e.getMessage() + " Continuing without db.");
        }
    }

    @Override
    public String getData(String player, String column) {
        if(this.connection == null) return "";

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
