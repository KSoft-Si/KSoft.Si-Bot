package net.benjaminurquhart.ksoftsi.commands;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.util.Usage;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command {

	private String name;
	private String[] args;
	
	public Command(){
		this(null);
	}
	public Command(String name, String... args){
		this.name = name;
		this.args = args;
	}
	public String getHelpMenu(){
		return Usage.getUsage(this, args);
	}
	public String getName(){
		return this.name;
	}
	public abstract void handle(GuildMessageReceivedEvent event, KSoftSi self);
}
