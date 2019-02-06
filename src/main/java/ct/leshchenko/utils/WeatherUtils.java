package ct.leshchenko.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Vitalii Leshchenko on 10.01.2018.
 */
public class WeatherUtils {

    public static final double KELVIN_ZERO = 273.15;

    public static double KelvinToCelsius(double celsius) {
        return celsius - KELVIN_ZERO;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
