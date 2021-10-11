import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Aid } from '../aid';
import { AidService } from '../aid.service';

@Component({
  selector: 'app-aid-details',
  templateUrl: './aid-details.component.html',
  styleUrls: ['./aid-details.component.css']
})
export class AidDetailsComponent implements OnInit {

  aid: Aid = new Aid();
  pageTitle: string;
  isRemaining: boolean;

  constructor(private route: ActivatedRoute,private router: Router,
    private aidService: AidService) { }

  ngOnInit(): void {
    this.aid = new Aid();
    this.aid = history.state.aid;
    
    this.aidService.getAid(this.aid)
      .subscribe(data => {
        console.log(data)
        this.aid = data;
        this.isRemaining = this.aid.payment.remaining > 0?true: false;
        this.pageTitle = `${this.aid.house.houseNo}, ${this.aid.house.address}`
      }, error => console.log(error));
  }

  updateAid(aid: Aid){
    this.router.navigate(['/updateAid'], {state: {aid: aid}});
  }

  /*onSubmit(){
    this.clearBalance();
  }*/
  clearBalance(){
    this.aid.payment.advance = this.aid.payment.costOfService;
    this.aid.payment.remaining = 0;
    this.aidService.updateAid(this.aid.house.houseId, this.aid)
      .subscribe(data => {
        console.log(data);
        this.aid = data;
        this.isRemaining = false;
        this.aidService.getAid(this.aid)
      }, error => console.log(error));
  }

}
