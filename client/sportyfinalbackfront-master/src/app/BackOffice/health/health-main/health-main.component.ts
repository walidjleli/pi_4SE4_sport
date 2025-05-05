import { Component } from '@angular/core';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Health } from 'src/app/models/Health';
import { HealthServiceService } from 'src/app/services/HealthServiceService';
@Component({
  selector: 'app-health-main',
  templateUrl: './health-main.component.html',
  styleUrls: ['./health-main.component.css']
})
export class HealthMainComponent implements OnInit{
  
  listHealth !:Health[];
  health !:Health ;
  idr!:any;
  idUsername!:any;
  imageUrl: SafeUrl | null = null;
  selectedFile!: File ;

  ngOnInit(): void {
    this.hs.getHealthlist().subscribe(
      (data: Health[]) => {
        this.listHealth = data.map(health => {
          // No need to transform the data since file is already part of the model
          return health;
        });
        console.log(this.listHealth); // Check if data is being received
      },
      (error) => {
        console.error('Error fetching health data', error);
      }
    );
    this.health = new Health();
  }
  
  // Helper function to create safe image URLs
  getImageUrl(health: Health): SafeUrl | null {
    if (health.file?.data) {
      return this.sanitizer.bypassSecurityTrustUrl(
        `data:${health.file.type};base64,${health.file.data}`
      );
    }
    return null;
  }

    constructor(private hs: HealthServiceService,private sanitizer: DomSanitizer){
    }
    captchaResolved = false;
    handleCaptcha(response: string) {
      this.captchaResolved = !!response; // Will be true when captcha is completed
      console.log('Captcha resolved:', response);
    }
    
    
    onFileSelected(event: Event): void {
      const input = event.target as HTMLInputElement;
      if (input.files && input.files.length > 0) {
        this.selectedFile = input.files[0];
      }
    }
  
  
    saveHealth() {
      if (!this.captchaResolved) return;

      this.hs.addHealth(this.health, this.selectedFile).subscribe({
        next: (response) => {
          console.log('User created', response);
          
          this.health = new Health();
          this.captchaResolved = false;
        },
        error: (err) => {
          console.error('Error creating health', err);
          alert('Error creating health');
        }
      });
    }
  


    updateHealth(id: number, data?: Health, file?: File) {
      // If no data provided, we're in "fetch mode" - get the record to edit
      if (!data) {
        this.hs.getHealthByID(id).subscribe(healthData => {
          this.health = healthData;
          this.health.id = id; // Ensure ID is set
        });
        return;
      }
    
      // Otherwise, we're in "update mode"
      if (!this.captchaResolved) {
        alert('Please complete the captcha');
        return;
      }
    
      const formData = new FormData();
      formData.append('health', new Blob([JSON.stringify(data)], { type: 'application/json' }));
      
      if (file) {
        formData.append('file', file);
      }
    
      this.hs.updateHealth(data, file).subscribe({
        next: (response) => {
          console.log('Update successful', response);
          this.health = new Health(); // Reset form
          this.captchaResolved = false;
          this.refreshHealthList();
        },
        error: (err) => {
          console.error('Update failed', err);
          alert('Error updating health record');
        }
      });
    }
    
    private refreshHealthList() {
      this.hs.getHealthlist().subscribe(
        (data: Health[]) => this.listHealth = data,
        (error) => console.error('Error refreshing list', error)
      );
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
