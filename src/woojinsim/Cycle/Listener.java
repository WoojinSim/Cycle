package woojinsim.Cycle;

import java.io.IOException;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import woojinsim.Cycle.Commands.*;

public class Listener extends ListenerAdapter{
	private String command;

	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if (args[0].startsWith(Main.prefix)) {
			command = args[0].toLowerCase().substring(1);
			
			if (command.equals("정보") || command.equals("info")) {
				Info.run(event, args);
			} else if (command.equals("청소") || command.equals("삭제") || command.equals("delete") || command.equals("del")) {
				Clear.run(event, args);
			} else if (command.equals("워프레임") || command.equals("워프래임") || command.equals("웦") || command.equals("웦렘") || command.equals("웦램") || command.equals("warframe") || command.equals("wf")) {
				Warframe.run(event, args);
			} else if (command.equals("코로나")) {
				try {
					Corona.run(event, args);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
