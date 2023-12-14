package com.project.service;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import com.project.entities.Publication;

@AllArgsConstructor
@Service
public class PublicationService {

    private final NotificationService notificationService;

    public Publication createPublication(Publication publication) {
        String message = publication.getTitle() + "\n\n" + publication.getText();
        
        notificationService.sendTransactionNotification(message);
        
        
        return null;
    }

    
    
}
