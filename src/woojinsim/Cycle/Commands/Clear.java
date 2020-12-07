package woojinsim.Cycle.Commands;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

public class Clear {
	public static void run(GuildMessageReceivedEvent event, String[] args) {
		if (args.length < 2) {
			EmbedBuilder usage = new EmbedBuilder();
			usage.setColor(0xff3923);
			usage.setTitle("올바르지 않은 사용법입니다");
			usage.setDescription("사용법: `" + args[0] + " [# 메세지 갯수]`");
			event.getChannel().sendMessage(usage.build()).queue();
		} else {
			try {
				// 성공
				List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
				event.getChannel().deleteMessages(messages).queue();
				EmbedBuilder success = new EmbedBuilder();
				success.setColor(0x578BD2);
				success.setTitle("성공적으로 " + messages.size() + " 개의 메세지를 삭제했습니다");
				success.setFooter(event.getMessage().getAuthor().getAsTag() + " 님께서 실행함", event.getMessage().getAuthor().getAvatarUrl());
				event.getChannel().sendMessage(success.build()).queue();
			} catch (IllegalArgumentException e) {
				if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
					// 너무 많은 메세지
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("너무 많은 메세지가 선택되었습니다");
					error.setDescription("한번에 1 ~ 100 개의 메세지만 삭제할 수 있습니다.");
					event.getChannel().sendMessage(error.build()).queue();
				} else {
					// 너무 오래된 메세지
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("선택된 메세지가 너무 오래되었습니다");
					error.setDescription("2주 내에 입력된 메세지만을 삭제할 수 있습니다.");
					event.getChannel().sendMessage(error.build()).queue();
				}
			} catch (InsufficientPermissionException e) {
				// 권한 없음
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0xff3923);
				usage.setTitle("명령을 수행할 수 없습니다");
				usage.setDescription("해당 명령어를 수행하기 위해 필요한 권한이 부족합니다.");
				event.getChannel().sendMessage(usage.build()).queue();
			}
		}
	}
}
