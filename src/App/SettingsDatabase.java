package App;

import java.sql.*;

public class SettingsDatabase {
    private Connection con;
    public void getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:settings.db");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        try {
            getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='settings'");
            if (!res.next()) {
                Statement stmt2 = con.createStatement();
                stmt2.execute("CREATE TABLE settings(volume integer," + "color_index integer,"+"current_directory varchar(50));");
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase();
        }
    }

    public int getSettingsInformation(String label) {
        int result=0;
        try {
            getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM settings");
            while (res.next()) {
                result = res.getInt(label);
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase();
        }
        return result;
    }

    public String getCurrentDirectory() {
        String path=null;
        try {
            getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM settings");
            while (res.next()) {
                path = res.getString("current_directory");
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase();
        }
        return path;
    }

    public void saveSettings(int volume, int color, String path) {
        getConnection();
        try {
            PreparedStatement prep = con.prepareStatement("INSERT INTO settings values(?,?,?);");
            prep.setInt(1, volume);
            prep.setInt(2, color);
            prep.setString(3, path);
            prep.execute();
            prep.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeDatabase();
        }
    }

    public void resetDatabase() {
        try {
            getConnection();
            PreparedStatement prep = con.prepareStatement("DELETE from settings;");
            prep.execute();
            prep.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDatabase();
        }
    }

    public void closeDatabase() {
        try {
            if (con != null && con.isClosed() == false) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

