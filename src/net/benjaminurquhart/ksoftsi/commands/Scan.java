package net.benjaminurquhart.ksoftsi.commands;

import java.util.ArrayList;
import java.util.List;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Ban;

public class Scan extends Command{

	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		KSoftAPI api = self.getAPI();
		List<Long> ids = new ArrayList<>();
		for(Member member : event.getGuild().getMembers()){
			if(member.getUser().isBot()){
				continue;
			}
			ids.add(member.getUser().getIdLong());
		}
		try{
			List<Ban> bans = api.getBulkBanChecker().addIds(ids).execute();
			channel.sendMessage(bans.size() + " people are globally banned on KSoft.Si in this guild").queue();
		}
		catch(Exception e){
			channel.sendMessage(e.toString()).queue();
		}
	}

	@Override
	public String getDescription() {
		return "currently broken, don't use";
	}

}
