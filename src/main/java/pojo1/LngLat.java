package pojo1;



public class LngLat {
    String lng ;
    String lat;
    String city;
    String title;

    public LngLat() {
    }

    public LngLat(String lng, String lat, String city, String title) {
        this.lng = lng;
        this.lat = lat;
        this.city = city;
        this.title = title;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
