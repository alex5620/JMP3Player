package App;


import java.sql.*;
import java.util.ArrayList;

public class PlaylistDatabase {
    private Connection con;
    public void getConnection() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:playlist.db");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void initialize() {
        try {
            getConnection();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='playlist'");
            if (!res.next()) {
                Statement stmt2 = con.createStatement();
                stmt2.execute("CREATE TABLE playlist(name varchar(50)," + "path varchar(50)," + "time varchar(10)," +"primary key(name));");
            }
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeDatabase();
        }
    }
    public ArrayList<SongInformation> getData() {
        try {
            getConnection();
            ArrayList<SongInformation> list= new ArrayList<>();
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM playlist");
            while(res.next())
            {
                list.add(new SongInformation(res.getString("name"),
                        res.getString("path"), res.getString("time")));
            }
            stmt.close();
            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
            closeDatabase();
        }
    }
    public void addSong(String name, String path, String time) {
        try {
            PreparedStatement prep = con.prepareStatement("INSERT INTO playlist values(?,?,?);");
            prep.setString(1, name);
            prep.setString(2, path);
            prep.setString(3, time);
            prep.execute();
            prep.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void resetDatabase() {
        try {
            getConnection();
            PreparedStatement prep = con.prepareStatement("DELETE from playlist;");
            prep.execute();
            prep.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeDatabase();
        }
    }

    public void closeDatabase()
    {
        try {
            if(con != null && con.isClosed()==false) {
                con.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
