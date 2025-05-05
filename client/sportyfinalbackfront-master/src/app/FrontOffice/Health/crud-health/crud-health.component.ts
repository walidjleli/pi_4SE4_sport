import { Component } from '@angular/core';
import { Health } from 'src/app/models/Health';
import { HealthServiceService } from 'src/app/services/HealthServiceService';

@Component({
  selector: 'app-crud-health',
  templateUrl: './crud-health.component.html',
  styleUrls: ['./crud-health.component.css']
})
export class CrudHealthComponent implements OnInit {

  listHealth !:Health[];
  health !:Health ;
  idr!:any;
  captchaResolved = false;

  ngOnInit(): void {
    this.hs.getHealthlist().subscribe(
      (data: Health[]) => {
        this.listHealth = data;
        console.log(this.listHealth); // Check if data is being received
      },
      (error) => {
        console.error('Error fetching health data', error); // Log any errors
      }
    );
    this.health = new Health();
    
  }

  constructor(private hs: HealthServiceService){
  }

  handleCaptcha(response: string) {
    this.captchaResolved = !!response; // Will be true when captcha is completed
    console.log('Captcha resolved:', response);
  }

  selectedFile!: File ;
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  saveHealth(): void {
    if (!this.captchaResolved) return;

    this.hs.addHealth(this.health, this.selectedFile).subscribe({
      next: (response) => {
        console.log('health created', response);
        
        this.health = new Health();
        this.captchaResolved = false;
      },
      error: (err) => {
        console.error('Error creating health', err);
        alert('Error creating health');
      }
    });
  }







  updateHealth(id: number) {
    console.log(id);
    
    this.hs.getHealthByID(id).subscribe(data => {
      console.log(data);
      this.health = data;
    });
  }
  

  deleteHealth(id: number) {
   

    this.hs.deleteHealth(id).subscribe(
      () => {
        // Remove the deleted health record from the list
        this.listHealth = this.listHealth.filter(health => health.id !== id);
        console.log(`Health record with id ${id} deleted successfully.`);
      },
      (error) => {
        console.error('Error deleting health data', error); // Log any errors
      }
    );
  }
}
