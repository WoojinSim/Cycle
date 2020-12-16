package woojinsim.Cycle.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Info {
	public static void run(GuildMessageReceivedEvent event, String[] args) {
		EmbedBuilder output = new EmbedBuilder();
		output.setColor(0x578BD2);
		output.setTitle("🔹 안녕하세요 저는 종합 게임 봇 사이클입니다");
		output.setDescription(" ▫ 저는 2020-12-07 부터 Woojin Sim 님에 의해 개발되었습니다.\n"
				+ " ▫ 저는 오픈소스로 개발되고 있습니다. 저의 [깃허브](https://github.com/WoojinSim/Cycle)를 참조해주세요.");
		output.setFooter(event.getMessage().getAuthor().getAsTag() + " 님께서 실행함", event.getMessage().getAuthor().getAvatarUrl());
		event.getChannel().sendMessage(output.build()).queue();
	}
}
