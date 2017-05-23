package BOT;

import java.util.regex.*;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.groupadministration.KickChatMember;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class YouGen extends TelegramLongPollingBot{

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new YouGen());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
 
	@Override
	public String getBotUsername() {
		return "YouGenBot";
	}
 
	@Override
	public String getBotToken() {
		return "372513789:AAF92eynC8ygXCZ-OZqjSxWgNSGBIqbhDnc";
	}
 
	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		boolean flag = false;
		if (message != null && message.hasText()) {
			if (message.getText().equals("/help@YouGenBot"))
				sendMsg(message, "Привет, я робот будущей компании Yougen, создан специально для чата Error404, "
						+ "моя версия 1.0. В текущей версии моим преднозначением стало удалять спамеров,"
						+ "поэтому 10 раз подумайте, прежде чем кидать сюда инвайты, иначе сразу отправитесь в бан,"
						+ "хуев вам и срачей, удачи!");
			else{
				flag = searchUrls(message);
				if(flag == true){
					kickChatMemb(message);	
					dellMsg(message);
				}
								
			}
			
		}
	}
	
	public boolean searchUrls(Message message){	
		String str = message.getText();
		String[] slova = str.split("\\s|\\n");
		boolean res = false;
		for(String slovo : slova){
			Pattern p = Pattern.compile(".+(t.me|telegram.me).+");
	        Matcher m = p.matcher(slovo);  
	        res = m.matches();
		}
		return res; 
	}
	
	
	private void kickChatMemb(Message message) {
		KickChatMember kickChatMember = new KickChatMember();
		kickChatMember.setChatId(message.getChatId().toString());
		kickChatMember.setUserId(message.getFrom().getId());
		try {
			kickMember(kickChatMember);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
	}
	
	private void dellMsg(Message message) {
		DeleteMessage deleted = new DeleteMessage();
		deleted.setChatId(message.getChatId().toString());
		deleted.setMessageId(message.getMessageId());
		try {
			deleteMessage(deleted);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
	}

	private void sendMsg(Message message, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(message.getChatId().toString());
		sendMessage.setReplyToMessageId(message.getMessageId());
		sendMessage.setText(text);
		try {
			sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
}
