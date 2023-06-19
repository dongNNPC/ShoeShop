package com.poly.asm.config.short_method;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poly.asm.dao.UserHistoryRepository;
import com.poly.asm.model.User;
import com.poly.asm.model.UserHistory;

@Component
public class History {
	@Autowired
	private UserHistoryRepository historyRepository;

	public void setHistory(String note, User user) {
		Date currentDate = new Date();
		UserHistory history = new UserHistory();
//		String noteString = "User với id là: " + user.getID() + " và tên là: " + user.getName()
//				+ " đã thực hiện hành động: " + note + " Vào lúc: " + currentDate;
		String noteString = "Bạn (" + user.getName() + ") đã thực hiện hành động: " + note + " Vào lúc: " + currentDate;
		history.setNote(noteString);
		history.setHistoryDate(currentDate);
		history.setUser(user);
		historyRepository.save(history);
	}
}
