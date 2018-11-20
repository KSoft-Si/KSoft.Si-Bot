package net.benjaminurquhart.ksoftsi.commands;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class ImgGen extends Command {

	public static final String API = "https://services.is-going-to-rickroll.me/api/%s?text=%s&avatar1=%s&avatar2=%s&username1=%s&username2=%s";
	public List<String> endpoints;
	
	public ImgGen(){
		super("imgen", "imagename", "text");
		this.endpoints = new ArrayList<>();
		try{
			refreshEndpoints();
		}
		catch(Exception e){}
	}
	private InputStream getStream(String link, String auth) throws IOException{
		URL url = new URL(link);
		URLConnection conn = url.openConnection();
		if(auth != null){
			conn.setRequestProperty("Authorization", auth);
		}
		conn.setRequestProperty("User-Agent", "KSoft.Si Bot");
		return conn.getInputStream();
	}
	public void refreshEndpoints() throws IOException{
		InputStream stream = getStream("https://services.is-going-to-rickroll.me/endpoints", null);
		StringBuilder sb = new StringBuilder();
		int c;
		while((c = stream.read()) != -1){
			sb.append((char)c);
		}
		String[] endpoints = sb.toString().split("<div class=\"card\">", 2)[1].split("<div class=\"card\">");
		for(int i = 0; i < endpoints.length; i++){
			endpoints[i] = endpoints[i].split("<span>")[1].split("</span>")[0].replace("\n", "");
		}
		this.endpoints.clear();
		this.endpoints.addAll(Arrays.asList(endpoints));
	}
	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		String[] args = event.getMessage().getContentRaw().toLowerCase().split(" ", 4);
		try{
			String endpoint = args[2].toLowerCase();
			if(endpoint.equals("refresh") && event.getAuthor().getId().equals("273216249021071360")){
				refreshEndpoints();
				channel.sendMessage(endpoints.toString()).queue();
				return;
			}
			if(!endpoints.contains(endpoint)){
				channel.sendMessage("Invalid image name. Valid images:\n" + endpoints.stream().reduce("", (out,point) -> out += point + " ")).queue();
				return;
			}
			List<User> users = event.getMessage().getMentionedUsers();
			String text = args[3];
			User user1 = (users.isEmpty() ? event.getAuthor() : users.get(0)), user2 = (users.size() < 2 ? user1 : users.get(1));
			text = text.replace(user1.getAsMention(), "").replace(user2.getAsMention(), "");
			InputStream image = getStream(String.format(API, endpoint, URLEncoder.encode(text, "UTF-8").replace("%2C", ","), user1.getAvatarUrl(), user2.getAvatarUrl(), user1.getName(), user2.getName()), self.getImgenToken());
			channel.sendFile(image, endpoint + ".png").queue();
		}
		catch(Exception e){
			channel.sendMessage(e.toString()).queue();
		}
	}
	@Override
	public String getDescription(){
		return "generates cool images for you!";
	}
}
