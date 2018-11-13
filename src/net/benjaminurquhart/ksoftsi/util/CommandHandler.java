package net.benjaminurquhart.ksoftsi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
		System.out.println("Registered command " + command.getName());
	}
	public List<Command> getRegisteredCommands(){
		List<Command> out = new ArrayList<>();
		for(Entry<String, Command> entry : commands.entrySet()){
			out.add(entry.getValue());
		}
		return out;
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
