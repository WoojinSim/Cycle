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
	
	public static void run(GuildMessageReceivedEvent event, String[] args) throws IOException {
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
		output.setThumbnail("https://i.imgur.com/FoNkhIF.png");
		
		output.addField("일일확진자", " ▫ **국내발생** " + df.format(Integer.valueOf(items.select(".info_02 em").text()))
				+ "명\n ▫ **해외유입** "  + df.format(Integer.valueOf((items.select(".info_03 em").text()))) + "명", true);
		
		items = doc.select(".status_info");
		String[] infection = items.select(".info_01 p").text().split(" ");
		String[] death = items.select(".info_04 p").text().split(" ");
		output.addField("국내현황", " ▫ **확진환자**\n ▫ **검사중**\n ▫ **격리해제**\n ▫ **사망자**", true);
		output.addField("", infection[0] + "명\n"
				+ items.select(".info_02 p").text() + "명\n"
				+ items.select(".info_03 p").text() + "명\n"
				+ death[0] + "명\n", true);
		event.getChannel().sendMessage(output.build()).queue();
    }
}
