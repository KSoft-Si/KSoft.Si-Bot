package net.benjaminurquhart.ksoftsi.util;

import net.explodingbush.ksoftapi.entities.Weather;
import net.explodingbush.ksoftapi.enums.Units;

public class WeatherUtil {
	public static final String WEATHER_FORMAT = "**Temperature:** %.2f \u00B0{temp}\n**Apparent Temperature:** %.2f \u00B0{temp}\n**Humidity:** %s\n**Visibilty:** %.2f {dist}\n**Wind Speed:** %.2f {speed} %s (Gusts: %.2f {speed})\n**Chance of precipitation:** %s";

	private static String getDirection(int dir){
		if(dir > 337.5){
			return "N";
		}
		if(dir > 292.5){
			return "NW";
		}
		if(dir > 247.5){
			return "W";
		}
		if(dir > 202.5){
			return "SW";
		}
		if(dir > 157.5){
			return "S";
		}
		if(dir > 112.5){
			return "SE";
		}
		if(dir > 67.5){
			return "E";
		}
		if(dir > 22.5){
			return "NE";
		}
		return "N";
	}
	private static String getTempUnit(Units unit){
		switch(unit.toString().toLowerCase()){
		case "si": return "C";
		case "us": return "F";
		default: return "C";
		}
	}
	private static String getDistanceUnit(Units unit){
		switch(unit.toString().toLowerCase()){
		case "si": return "km";
		case "us": return "miles";
		default: return "km";
		}
	}
	private static String getSpeedUnit(Units unit){
		switch(unit.toString().toLowerCase()){
		case "si": return "km/h";
		case "us": return "mph";
		default: return "km/h";
		}
	}
	private static String setUnits(Units unit){
		return WEATHER_FORMAT
				.replace("{temp}", getTempUnit(unit))
				.replace("{dist}", getDistanceUnit(unit))
				.replace("{speed}", getSpeedUnit(unit));
	}
	public static String format(Weather data){
		return String.format(
				setUnits(data.getUnits()),
				data.getTemperature(),
				data.getApparentTemperature(),
				String.format("%.2f", data.getHumidity()*100.0) + "%",
				data.getVisibility(),
				data.getWindSpeed(),
				getDirection(data.getWindBearing()),
				data.getWindGust(),
				data.getPrecipProbability() + "%");
	}
}
