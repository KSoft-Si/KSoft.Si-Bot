package net.benjaminurquhart.ksoftsi.util;

import java.util.HashMap;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.commands.Command;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CommandHandler extends ListenerAdapter{

	private KSoftSi self;
	private HashMap<String, Command> commands;
	private GuildMessageReceivedEvent event;
	
	public CommandHandler(KSoftSi self){
		this.self = self;
		this.commands = new HashMap<>();
	}
	public void registerCommand(Command command){
		commands.put(command.getName(), command);
	}
	protected GuildMessageReceivedEvent getEvent(){
		return event;
	}
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event){
		if(!event.getChannel().canTalk()){
			return;
		}
		String msg = event.getMessage().getContentRaw().toLowerCase();
		if(event.getAuthor().isBot() || !msg.startsWith(KSoftSi.prefix)){
			return;
		}
		String cmd = msg.substring(KSoftSi.prefix.length()).trim();
		if(cmd.contains(" ")){
			cmd = cmd.split(" ")[0];
		}
		Command command = commands.get(cmd);
		if(!(command == null)){
			command.handle(event, self);
			return;
		}
	}
}
