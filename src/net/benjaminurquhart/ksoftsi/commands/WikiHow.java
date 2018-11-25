package net.benjaminurquhart.ksoftsi.commands;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.util.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Wikihow;

public class WikiHow extends Command {

	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		KSoftAPI api = self.getAPI();
		Wikihow wikihow = api.getRandomWikihow().allowNsfw(channel.isNSFW()).execute();
		EmbedBuilder eb = EmbedUtils.getEmbed(event.getGuild(), wikihow.getImage(), "Is this Wikihow NSFW? " + (wikihow.isNsfw() ? "yes" : "no"), event.getAuthor());
		eb.setTitle(wikihow.getTitle());
		eb.setDescription(wikihow.getArticleUrl());
		channel.sendMessage(eb.build()).queue();
	}

	@Override
	public String getDescription() {
		return "gets a random wikihow";
	}

}
