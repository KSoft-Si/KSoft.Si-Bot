package net.benjaminurquhart.ksoftsi.commands;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.util.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Reddit;
import net.explodingbush.ksoftapi.enums.ImageType;

public class RandImage extends Command{

	public RandImage(){
		super("randimage", "subreddit");
	}
	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		KSoftAPI api = self.getAPI();
		String[] args = event.getMessage().getContentRaw().split(" ", 3);
		if(args.length < 3){
			channel.sendMessage(this.getHelpMenu()).queue();
			return;
		}
		try{
			Reddit image;
			do{
				image = api.getRedditImage(ImageType.RANDOM_REDDIT).setSubreddit(args[2].toLowerCase()).execute();
			}while(image.isNsfw() && !channel.isNSFW());
			EmbedBuilder eb = EmbedUtils.getEmbed(event.getGuild(), image.getImageUrl(), image.getSubreddit(), event.getAuthor());
			eb.setTitle(image.getTitle());
			eb.setAuthor(image.getAuthor(), image.getSourceUrl());
			channel.sendMessage(eb.build()).queue();
		}
		catch(Exception e){
			channel.sendMessage(e.toString()).queue();
		}
	}
	@Override
	public String getDescription(){
		return "gets a random image from the given subreddit";
	}
}
