package net.benjaminurquhart.ksoftsi.commands;
/*
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;*/
import java.util.List;
import java.util.stream.Collectors;
/*
import org.json.JSONArray;
import org.json.JSONObject;
*/
import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Ban;
import net.explodingbush.ksoftapi.entities.BulkBan;/*
import net.explodingbush.ksoftapi.entities.impl.BanImpl;
import net.explodingbush.ksoftapi.enums.Routes;*/

public class Scan extends Command{

	//@SuppressWarnings("unchecked")
	@Override
	public void handle(GuildMessageReceivedEvent event, KSoftSi self) {
		TextChannel channel = event.getChannel();
		KSoftAPI api = self.getAPI();
		BulkBan action = api.getBan().checkBulkBan();
		for(Member member : event.getGuild().getMembers()){
			if(member.getUser().isBot()){
				continue;
			}
			action.addId(member.getUser().getId());
		}
		//byte[] out = new JSONObject().put("users", ids).toString().getBytes();
		try{
			/*
			URLConnection conn = new URL(Routes.BAN_BULK.toString()).openConnection();
			conn.setRequestProperty("Authorization", "Bearer " + self.getKSoftToken());
			conn.setRequestProperty("Content-Length", ""+out.length);
			conn.setRequestProperty("User-Agent", "KSoft.Si Bot");
			conn.setDoOutput(true);
			conn.getOutputStream().write(out);
			InputStream stream = conn.getInputStream();
			StringBuilder sb = new StringBuilder();
			int i = 0;
			while((i = stream.read()) != -1){
				sb.append((char)i);
			}
			stream.close();
			JSONArray arr = new JSONArray(sb.toString());
			List<Ban> bans = new ArrayList<>();
			for(Object obj : arr){
	        	bans.add(new BanImpl(new JSONObject((java.util.HashMap<String, Object>)obj)));
	        }*/
			List<Ban> bans = action.set().execute().getBulkBanStream().filter((ban) -> ban.isBanned() && ban.isBanActive()).collect(Collectors.toList());
			channel.sendMessage(bans.size() + " people are globally banned on KSoft.Si in this guild").queue();
		}
		catch(Exception e){
			channel.sendMessage(e.toString()).queue();
		}
	}

	@Override
	public boolean hide(){
		return true;
	}
	@Override
	public String getDescription() {
		return "currently broken, don't use";
	}

}
