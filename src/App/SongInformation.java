package App;

public class SongInformation {
    private String name;
    private String path;
    private int millis;

    public SongInformation(String name, String path, int millis)
    {
        this.name = name;
        this.path = path;
        this.millis = millis;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getSongMillis() {
        return millis;
    }
}
