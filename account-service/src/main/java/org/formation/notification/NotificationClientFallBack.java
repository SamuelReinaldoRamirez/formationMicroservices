package org.formation.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationClientFallBack implements NotificationClient{

	@Override
	public String envoiCourrier(Courriel courriel) {
		// TODO Auto-generated method stub
		return "FALL-BACK";
	}

}
