package plugin.playermessages.database;

import com.google.gson.Gson;

import java.util.List;

/**
 * Bridging interface for allowing to easily change used dbs.
 * <p>
 * This interface provides the data manipulation used for player-specific
 * custom messages.
 */
public interface IDatabase {
    void createConnection();
    void createTable();
    void closeConnection();

    void insertData(Gson gson, String i, List<String> join_mess, List<String> leave_mess, List<String> death_mess);
    String getData(String player, String column);
}
