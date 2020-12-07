package woojinsim.Cycle.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Info {
	public static void run(TextChannel Channel, String[] args) {
		Channel.sendTyping().queue();
		EmbedBuilder info = new EmbedBuilder();
		info.setColor(0x578BD2);
		info.setTitle("🔹 안녕하세요 저는 종합 게임 봇 사이클입니다");
		info.setDescription("저는 2020-12-07 부터 Woojin Sim 님에 의해 개발되었습니다.");
		Channel.sendMessage(info.build()).queue();
	}
}
