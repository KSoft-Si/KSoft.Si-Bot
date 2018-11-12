package net.benjaminurquhart.ksoftsi.util;

import net.benjaminurquhart.ksoftsi.KSoftSi;
import net.benjaminurquhart.ksoftsi.commands.Command;

public class Usage {

	public static String getUsage(Command command, String... args){
		String out = "Usage: " + KSoftSi.prefix + command.getName() + " ";
		for(int i = 0; i < args.length; i++){
			out += "<%s> ";
		}
		return String.format(out.trim(), (Object[])args);
	}
}
