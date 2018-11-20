package net.benjaminurquhart.ksoftsi;

import java.io.File;
import java.io.FileInputStream;
//import java.util.Arrays;
import java.util.Set;

import org.json.JSONObject;
import org.reflections.Reflections;

import net.benjaminurquhart.ksoftsi.commands.*;
import net.benjaminurquhart.ksoftsi.util.CommandHandler;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.explodingbush.ksoftapi.KSoftAPI;

public class KSoftSi {

	private static String token, imgenToken;
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
		if(json.has("imgen")){
			imgenToken = json.getString("imgen");
		}
		else{
			imgenToken = null;
		}
	}
	public KSoftAPI getAPI(){
		return api;
	}
	public String getImgenToken(){
		/*
		boolean calledByImgen = Arrays.asList(Thread.currentThread().getStackTrace()).stream().filter((t) -> t.toString().contains("ImgGen")).count() > 0;
		if(!calledByImgen){
			throw new SecurityException("Unauthorized");
		}*/
		return imgenToken;
	}
	public static void main(String[] args) throws Exception{
		KSoftSi self = new KSoftSi();
		CommandHandler cmdHandler = new CommandHandler(self);
		Reflections reflections = new Reflections("net.benjaminurquhart.ksoftsi.commands");
        Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> i : commandClasses) {
            try {
                Command cls = i.getDeclaredConstructor().newInstance();
                cmdHandler.registerCommand(cls);
            } 
            catch (Exception e) {
            	e.printStackTrace();
            }
        }
		new JDABuilder(token).addEventListener(cmdHandler).setGame(Game.watching("people get the prefix wrong (it's 'ksoft')")).build();
	}
}
