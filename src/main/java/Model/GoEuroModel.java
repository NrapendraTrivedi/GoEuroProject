package Model;

/**
 * Created by NrapendraKumar on 12-07-2015.
 */
public class GoEuroModel {
    private long _id;
    private String name;
    private String type;
    private double latitude;
    private double longitude;

    private GoEuroModel() {
    }

    public static GoEuroModel createGoEuroModel() {
        return new GoEuroModel();
    }


    public long get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
