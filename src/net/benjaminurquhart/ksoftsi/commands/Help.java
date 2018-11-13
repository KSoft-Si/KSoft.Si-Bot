package net.benjaminurquhart.ksoftsi.commands;

import java.util.List;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.util.CommandHandler;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Help extends Command{

	public Help(){
		super("help");
	}
	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		List<Command> commands = ((CommandHandler)event.getJDA().getRegisteredListeners().get(0)).getRegisteredCommands();
		String out = "```";
		for(Command command : commands){
			out += command.getHelpMenu().replace("Usage:", "").trim() + "\n";
		}
		out += "```";
		channel.sendMessage(out).queue();
	}

}
