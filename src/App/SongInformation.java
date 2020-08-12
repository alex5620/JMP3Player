package App;

public class SongInformation {
    private String name;
    private String path;
    private String time;

    public SongInformation(String name, String path, String time)
    {
        this.name = name;
        this.path = path;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getTime() {
        return time;
    }
}
