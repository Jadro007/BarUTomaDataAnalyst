package cz.muni.fi.PB138.main.entities;

/**
 * BarUTomaDataAnalyst
 */
public class Bar {

    private String name;
    private String info;
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", id=" + id +
                '}';
    }

    public Bar(String name, String info, long id) {
        this.name = name;
        this.info = info;
        this.id = id;
    }
}
