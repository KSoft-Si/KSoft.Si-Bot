package net.benjaminurquhart.ksoftsi.commands;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.util.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Reddit;
import net.explodingbush.ksoftapi.enums.ImageType;

public class Meme extends Command {

	public Meme() {
		super("meme");
	}

	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		KSoftAPI api = self.getAPI();
		Reddit image = api.getRedditImage(ImageType.RANDOM_MEME).execute();
		EmbedBuilder eb = EmbedUtils.getEmbed(event.getGuild(), image.getImageUrl(), image.getSubreddit(), event.getAuthor());
		channel.sendMessage(eb.build()).queue();
	}
	
}
