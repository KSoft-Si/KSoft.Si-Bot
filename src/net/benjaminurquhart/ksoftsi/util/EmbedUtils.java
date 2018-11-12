package net.benjaminurquhart.ksoftsi.util;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageEmbed.Footer;
import net.dv8tion.jda.core.entities.User;

public class EmbedUtils {

	public static EmbedBuilder addAttribution(EmbedBuilder eb){
		Footer footer = eb.build().getFooter();
		eb.setFooter(footer.getText() + " | Powered by KSoft.Si", footer.getIconUrl());
		return eb;
	}
	public static EmbedBuilder getEmbed(Guild guild, String image, String footer, User user){
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(guild.getSelfMember().getColor());
		eb.setImage(image);
		eb.setFooter(footer, user.getAvatarUrl());
		return addAttribution(eb);
	}
}
