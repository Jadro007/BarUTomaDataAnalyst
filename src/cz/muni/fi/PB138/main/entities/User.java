package cz.muni.fi.PB138.main.entities;

/**
 * BarUTomaDataAnalyst
 */
public class User {

    private long id;
    private String name;
    private Currency defaultCurrency;
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", defaultCurrency=" + defaultCurrency +
                ", role='" + role + '\'' +
                '}';
    }

    public User(long id, String role, Currency defaultCurrency, String name) {
        this.id = id;
        this.role = role;
        this.defaultCurrency = defaultCurrency;
        this.name = name;
    }
}
