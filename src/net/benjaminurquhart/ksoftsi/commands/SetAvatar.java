package net.benjaminurquhart.ksoftsi.commands;

import java.net.URL;
import java.net.URLConnection;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.util.Usage;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SetAvatar extends Command{

	public SetAvatar(){
		super("setavatar");
	}
	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		if(!event.getAuthor().getId().equals("273216249021071360")){
			return;
		}
		TextChannel channel = event.getChannel();
		String[] args = event.getMessage().getContentRaw().split(" ");
		if(args.length < 3){
			channel.sendMessage(Usage.getUsage(this, "url"));
			return;
		}
		try{
			URLConnection conn = new URL(args[2]).openConnection();
			conn.setRequestProperty("User-Agent", "https://discordapp.com/oauth2/authorize?client_id=472922680958648331&permissions=3200&scope=bot");
			event.getJDA().getSelfUser().getManager().setAvatar(Icon.from(conn.getInputStream())).queue();
			channel.sendMessage(":ok_hand:").queue();
		}
		catch(Exception e){
			channel.sendMessage(e.toString()).queue();
		}
	}

}
