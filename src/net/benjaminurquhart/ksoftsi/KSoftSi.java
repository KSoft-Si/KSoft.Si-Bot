package net.benjaminurquhart.ksoftsi;

import java.io.File;
import java.io.FileInputStream;

import org.json.JSONObject;

import net.benjaminurquhart.ksoftsi.commands.*;
import net.benjaminurquhart.ksoftsi.util.CommandHandler;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.explodingbush.ksoftapi.KSoftAPI;

public class KSoftSi {

	private static String token;
	private static KSoftAPI api;
	public static String prefix;
	
	public KSoftSi() throws Exception{
		FileInputStream stream = new FileInputStream(new File("ksoft-config.json"));
		String data = "";
		int latest = stream.read();
		while(latest != -1){
			data += (char)latest;
			latest = stream.read();
		}
		stream.close();
		JSONObject json = new JSONObject(data);
		token = json.getString("token");
		prefix = json.getString("prefix");
		api = new KSoftAPI(json.getString("ksoft"));
	}
	public KSoftAPI getAPI(){
		return api;
	}
	public static void main(String[] args) throws Exception{
		KSoftSi self = new KSoftSi();
		CommandHandler cmdHandler = new CommandHandler(self);
		cmdHandler.registerCommand(new Meme());
		cmdHandler.registerCommand(new Lyrics());
		cmdHandler.registerCommand(new WikiHow());
		cmdHandler.registerCommand(new SetAvatar());
		new JDABuilder(token).addEventListener(cmdHandler).setGame(Game.watching("things")).build();
	}
}
