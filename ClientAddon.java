package your.package.here;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientAddon {

    /**
     * Create a player in the database.
     * @param uuid | Used to specify the uuid of the player.
     * @return boolean | Returns true if the player exists and false if not.
     */
    public static boolean playerExists(String uuid) {
        try {
            ResultSet rs = Main.mySQL.query("SELECT * FROM PlayerCapes WHERE UUID= '" + uuid + "'");
            if (rs.next()) {
                return (rs.getString("UUID") != null);
            }
            return true;
        } catch (SQLException | NullPointerException exception) {
            return false;
        }
    }

    /**
     * Creates a player identified by the uuid.
     * @param uuid | Used to specify the player.
     */
    public static void createPlayer(String uuid) {
        if (!playerExists(uuid)) {
            Main.mySQL.update("INSERT INTO PlayerCapes (UUID, ACTIVE) VALUES ('" + uuid + "', 'false');");
        }
    }

    /**
     * Sets the cape status boolean.
     * @param uuid | UUID used tp specify the player.
     * @param status | Boolean how the status should be set.
     */
    public static void setCapeStatus(String uuid, Boolean status) {
        if (ClientAddon.playerExists(uuid)) {
            Main.mySQL.update("UPDATE PlayerCapes SET KILLS= '" + status + "' WHERE UUID= '" + uuid + "';");
        } else {
            ClientAddon.createPlayer(uuid);
            setCapeStatus(uuid, status);
        }
    }

    /**
     * Get the cape status of a player.
     * @param uuid | UUID to specify the player.
     * @return status | Returns a boolean if the player has a cape or not.
     */
    public static Boolean getCapeStatus(String uuid) {
        boolean status;
        if (ClientAddon.playerExists(uuid)) {
            try {
                ResultSet rs = Main.mySQL.query("SELECT * FROM PlayerCapes WHERE UUID= '" + uuid + "'");
                status = rs.getBoolean("ACTIVE");
            } catch (SQLException exception) {
                System.out.println("[ERROR] An unhandled error occured while getting player Cape status.");
                System.out.println("[ERROR] Error: " + exception.getMessage());
                exception.printStackTrace();
                status = false;
                getCapeStatus(uuid);
            }
        } else {
            status = false;
            ClientAddon.createPlayer(uuid);
            getCapeStatus(uuid);
        }
        return status;
    }

}
