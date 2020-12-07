package woojinsim.Cycle.Commands;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.jsoup.Jsoup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Warframe {
	private final static String address = "https://api.warframestat.us/pc";
	
	public static void run(GuildMessageReceivedEvent event, String[] args) {
		JSONObject jsonObject = null;
		JSONArray jsonArray = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
		
		event.getChannel().sendTyping().queue();
		try {
			String rawJson = Jsoup.connect(address)
					.userAgent("Mozilla")
					.header("accept-language", "ko-KR")
					.ignoreContentType(true)
					.execute().body();
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(rawJson);
			jsonObject = (JSONObject) obj;
			
			if (args.length < 2) {
				JSONObject jsonObj = (JSONObject) jsonObject.get("arbitration");
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0x578BD2);
				usage.setTitle("🔹 워프레임 이벤트 트레커");
				usage.setDescription("현재 워프레임의 이벤트 현황입니다.\n");
				usage.setTimestamp(Instant.now());
				usage.setFooter(event.getMessage().getAuthor().getAsTag() + " 님께서 실행함", event.getMessage().getAuthor().getAvatarUrl());
				usage.setThumbnail("https://cdn.discordapp.com/emojis/714873205768060938.png");
				
				jsonArray = (JSONArray) jsonObject.get("news");
				String resultString = "";
				for(int i = 0; i < jsonArray.size(); i++) {
					JSONObject result = (JSONObject) jsonArray.get(i);
					resultString = resultString + " ▫ [" + result.get("message") + "](" + result.get("link") + ")\n";
				}
				if (jsonArray.size() < 1)
					resultString = " ▫ 전달할 소식이 없습니다.";
				usage.addField("뉴스", resultString, false);
				
				OffsetDateTime odt = OffsetDateTime.parse(jsonObj.get("expiry").toString(), formatter);
				System.out.println(DateTimeFormatter.ofPattern("MM/uuuu/dd hh:mm:ss a", Locale.KOREAN).format(odt));
				usage.addField("중재", "_**노드:**_ " + jsonObj.get("node") + " - " + jsonObj.get("type").toString().replace("Disruption", "교란") + "\n_**팩션:**_ " + jsonObj.get("enemy").toString().toLowerCase()
						.replace("orokin", "오로킨")
						.replace("grineer", "그리니어")
						.replace("corpus", "코퍼스")
						.replace("infested", "인페스티드"),
						true);
				usage.addBlankField(true);
				
				jsonObj = (JSONObject) jsonObject.get("sortie");
				usage.addField("출격 (개발중)", (String) jsonObj.get("boss"), true);
				
				event.getChannel().sendMessage(usage.build()).queue();
				
			} else if (args[1].equals("중재") || args[1].equals("중제")) {
				// TODO 따로 기능 나눠만들기
			}
			
		} catch (IOException | ParseException | NullPointerException e) {
			// 연결 불능
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("오류가 발생했습니다");
			error.setDescription(e.toString());
			event.getChannel().sendMessage(error.build()).queue();
		}
		
		
	}
}
