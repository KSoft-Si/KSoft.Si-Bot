package net.benjaminurquhart.ksoftsi.commands;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {

	private String name;
	
	public Command(){
		this(null);
	}
	public Command(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	public abstract void handle(GuildMessageReceivedEvent event, KSoftSi self);
}
