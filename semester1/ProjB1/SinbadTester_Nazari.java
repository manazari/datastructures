import core.data.*;

public class SinbadTester_Nazari {
    public static void main(String[] args) {
        DataSource ds = DataSource.connect("http://weather.gov/xml/current_obs/KATL.xml");
        ds.setCacheTimeout(10 * 60);
        ds.load();

        System.out.println("Copyright URL: " + ds.fetchString("copyright_url"));
        System.out.println(ds.fetchString("observation_time") + "\n");

        System.out.println("  > station ID: " + ds.fetchString("station_id"));
        System.out.println("  > credit: "     + ds.fetchString("credit"));
        System.out.println("  > disclaimer: " + ds.fetchString("disclaimer_url"));
        System.out.println(" Location: "      + ds.fetchString("location"));
        System.out.println(" Lat/Long: ("     + ds.fetchString("latitude") + ", " + ds.fetchString("longitude") + ")");
        System.out.println(" Pressure: "      + ds.fetchString("pressure_string"));
        System.out.println(" Humidity: "      + ds.fetchString("relative_humidity"));
        System.out.println(" Temp: "          + ds.fetchString("temperature_string"));
        System.out.println(" Weather: "       + ds.fetchString("weather"));
        System.out.println(" Wind: "          + ds.fetchString("wind_string") + ", " + ds.fetchString("windchill_string"));
    }
}