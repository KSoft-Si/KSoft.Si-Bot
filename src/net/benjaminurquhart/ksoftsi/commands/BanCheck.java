package net.benjaminurquhart.ksoftsi.commands;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.util.Usage;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.KSoftAPI;

public class BanCheck extends Command{

	public BanCheck(){
		super("check", "user id");
	}
	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		KSoftAPI api = self.getAPI();
		String[] args = event.getMessage().getContentRaw().toLowerCase().split(" ");
		if(args.length < 3){
			channel.sendMessage(Usage.getUsage(this, "user id"));
			return;
		}
		try{
			channel.sendMessage("User is " + (api.getBan().setUserId(args[2]).execute().isBanned() ? "" : "not ") + "banned on KSoft.Si").queue();
		}
		catch(Exception e){
			channel.sendMessage(e.toString()).queue();
		}
	}
}
