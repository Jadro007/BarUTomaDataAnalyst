package cz.muni.fi.PB138.main.entities;

/**
 * BarUTomaDataAnalyst
 */
public class Bar {

    private String name;
    private String info;
    private long id;
    private long ownerId;

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

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
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

    public Bar(String name, String info, long id, long ownerId) {
        this.name = name;
        this.info = info;
        this.id = id;
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bar bar = (Bar) o;

        if (id != bar.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
