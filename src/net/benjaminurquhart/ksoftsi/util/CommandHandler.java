package net.benjaminurquhart.ksoftsi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.commands.Command;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.entities.User;
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
		System.out.println("Registered " + (command.hide() ? "private " : "") + "command " + command.getName());
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
			try{
				command.handle(event, self);
			}
			catch(Exception e){
				event.getChannel().sendMessage("Oh no! Something went wrong while executing that command!\nThis incident has been reported.\n" + e).queue();
				User owner = event.getJDA().getUserById("273216249021071360");
				if(!(owner == null)){
					owner.openPrivateChannel().queue(
					(channel) ->{
						String out = "```" + e.toString();
						for(StackTraceElement trace : e.getStackTrace()){
							out += "\n" + trace.toString();
						}
						if(out.length() > 1990){
							out = out.substring(0, 1990) + "...";
						}
						channel.sendMessage(out + "```").queue((m) -> {},
						(error) ->{
							event.getChannel().sendMessage("Failed to report the incident!\n" + error).queue();
						});
						channel.sendMessage("Command: `" + event.getMessage().getContentRaw() + "`").queue((m) -> {}, (error) -> {});
					});
				}
			}
			return;
		}
	}
}
