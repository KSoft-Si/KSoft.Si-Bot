package net.benjaminurquhart.ksoftsi.commands;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class ThrowError extends Command {

	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		if(event.getAuthor().getId().equals("273216249021071360")){
			throw new RuntimeException("testing");
		}
	}
	@Override
	public boolean hide(){
		return true;
	}
}
