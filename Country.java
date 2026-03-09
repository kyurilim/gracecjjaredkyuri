// Country.java
public class Country {
    private String name;
    private double internetUsage;

    public Country(String name, double internetUsage) {
        this.name = name;
        this.internetUsage = internetUsage;
    }

    public String getName() { return name; }
    public double getInternetUsage() { return internetUsage; }
    public String toString() {
        return "{\"name\":\"" + name + "\",\"value\":" + internetUsage + "}";
    }
}