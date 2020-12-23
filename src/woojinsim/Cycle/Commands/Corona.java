package woojinsim.Cycle.Commands;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Instant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Corona {
	private final static String address = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=%EC%BD%94%EB%A1%9C%EB%82%98+%ED%99%95%EC%A7%84%EC%9E%90";
	
	public static void run(GuildMessageReceivedEvent event, String[] args) {
		event.getChannel().sendTyping().queue();
		try {
			DecimalFormat df = new DecimalFormat("#,###");
			Document doc = Jsoup.connect(address).get();
			Elements items = doc.select(".status_today");
			System.out.println(items.select(".info_02").attr("em"));
			
			EmbedBuilder output = new EmbedBuilder();
			output.setColor(0x578BD2);
			output.setTitle("🔹 코로나 현황");
			output.setDescription("현재 코로나바이러스감염증-19의 현황입니다.");
			output.setTimestamp(Instant.now());
			output.setFooter(event.getMessage().getAuthor().getAsTag() + " 님께서 실행함", event.getMessage().getAuthor().getAvatarUrl());
			output.setThumbnail("https://i.imgur.com/Z7o8tau.png");
			
			output.addField("일일확진자", String.format(" ▫ **국내발생** %s 명\n ▫ **해외유입** %s 명", df.format(Integer.valueOf(items.select(".info_02 em").text())), df.format(Integer.valueOf((items.select(".info_03 em").text())))), true);
			items = doc.select(".status_info");
			String[] infection = items.select(".info_01 p").text().split(" ");
			String[] death = items.select(".info_04 p").text().split(" ");
			output.addField("국내현황", String.format(" ▫ **확진환자** %s 명\n ▫ **검사중**　 %s 명\n ▫ **격리해제** %s 명\n ▫ **사망자**　 %s 명", infection[0], items.select(".info_02 p").text(), items.select(".info_03 p").text(), death[0]), true);
			output.addField("세계현황", String.format(" ▫ **확진환자** %s 명\n ▫ **사망자**　 %s 명\n ▫ **발생국가** %s 국가", infection[1], death[1], death[2]), false);
			event.getChannel().sendMessage(output.build()).queue();
		} catch (IOException e) {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("오류가 발생했습니다");
			error.setDescription(e.toString());
			event.getChannel().sendMessage(error.build()).queue();
		}
    }
}
