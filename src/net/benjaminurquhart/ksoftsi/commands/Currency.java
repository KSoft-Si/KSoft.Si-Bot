package net.benjaminurquhart.ksoftsi.commands;

import net.explodingbush.ksoftapi.entities.KCurrency;
import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.util.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.KSoftAPI;

public class Currency extends Command {

	public Currency(){
		super("currency", "from", "to", "amount");
	}
	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		KSoftAPI api = self.getAPI();
		String[] args = event.getMessage().getContentRaw().toUpperCase().split(" ", 5);
		if(args.length < 5){
			channel.sendMessage(this.getHelpMenu()).queue();
			return;
		}
		try{
			KCurrency conversion = api.getKumo().getCurrencyAction().from(args[2]).to(args[3]).amount(Double.parseDouble(args[4])).execute();
			EmbedBuilder eb = EmbedUtils.getEmbed(event.getGuild(), null, "Currency Conversion", event.getAuthor());
			eb.setDescription(String.format("```%.2f %s = %s```", conversion.originalAmount(), conversion.from().getCurrencyCode().toUpperCase(), conversion.convertedAmountPretty()));
			channel.sendMessage(eb.build()).queue();
		}
		catch(NumberFormatException e){
			channel.sendMessage("Invalid amount of money").queue();
		}
		catch(IllegalArgumentException e){
			channel.sendMessage("Invalid currency code").queue();
		}
		catch(Exception e){
			channel.sendMessage(e.toString()).queue();
		}
	}
	@Override
	public String getDescription(){
		return "converts currency";
	}
}
