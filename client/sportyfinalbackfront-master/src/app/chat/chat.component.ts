import { Component } from '@angular/core';
import { ChatService } from '../chat.service';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html'
})
export class ChatComponent {
  userMessage: string = '';
  botReply: string = '';

  constructor(private chatService: ChatService) {}

  sendMessage() {
    if (this.userMessage.trim()) {
      this.chatService.sendMessage(this.userMessage).subscribe({
        next: (response) => {
          this.botReply = response.reply;
        },
        error: (err) => {
          console.error('Error from backend', err);
        }
      });
    }
  }
}
