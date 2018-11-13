package net.benjaminurquhart.ksoftsi.commands;

import java.util.List;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.util.EmbedUtils;
import net.benjaminurquhart.ksoftsi.util.Usage;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Alert;
import net.explodingbush.ksoftapi.entities.Kumo;
import net.explodingbush.ksoftapi.enums.ReportType;
import net.explodingbush.ksoftapi.enums.Units;

public class Weather extends Command{
	
	public static final String WEATHER_FORMAT = "**Temperature:** %.2f\u00B0 C\n**Apparent Temperature:** %.2f\u00B0 C\n**Humidity:** %s\n**Visibilty:** %.2f km\n**Wind Speed:** %.2f km/s %s (Gusts: %.2f km/s)\n**Chance of precipitation:** %s";

	private String getDirection(int dir){
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
	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		KSoftAPI api = self.getAPI();
		String[] args = event.getMessage().getContentRaw().split(" ", 3);
		if(args.length < 3){
			channel.sendMessage(Usage.getUsage(this, "location")).queue();
			return;
		}
		try{
			channel.sendTyping().queue();
			Kumo data = api.getKumo().setLocationQuery(args[2]).setReportType(ReportType.CURRENTLY).setUnits(Units.SI).execute();
			EmbedBuilder eb = EmbedUtils.getEmbed(event.getGuild(), data.getIconUrl(), data.getLocation().getAddress(), event.getAuthor());
			eb.setTimestamp(data.getTime().minusHours(5));
			eb.setTitle(data.getSummary());
			eb.setDescription(
					String.format(
							Weather.WEATHER_FORMAT,
							data.getTemperature(),
							data.getApparentTemperature(),
							String.format("%.2f", data.getHumidity()*100.0) + "%",
							data.getVisibility(),
							data.getWindSpeed(),
							getDirection(data.getWindBearing()),
							data.getWindGust(),
							data.getPrecipProbability() + "%"
			));
			List<Alert> alerts = data.getAlerts();
			if(!alerts.isEmpty()){
				for(Alert alert : alerts){
					eb.addField(String.format("%s (Severity: %s)", alert.getTitle(), alert.getSeverity()), (alert.getDescription().length() > 1024 ? alert.getDescription().substring(0, 1020) + "..." : alert.getDescription()), true);
				}
			}
			channel.sendMessage(eb.build()).queue();
		}
		catch(Exception e){
			channel.sendMessage(e.toString()).queue();
		}
	}
	@Override
	public String getDescription(){
		return "Gets the current weather for the given location";
	}
	
}
