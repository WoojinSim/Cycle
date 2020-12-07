package woojinsim.Cycle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Main {
	private static JDA jda;
	public static String prefix = "!";
	private static String token;

	// Main method
	public static void main(String[] args) throws LoginException {
		File file = new File("./src/woojinsim/Cycle/token.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			token = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		jda = JDABuilder.createDefault(token).build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setActivity(Activity.watching("사용설명서"));
		
		jda.addEventListener(new Listener());
	}
}
