
import { Component } from '@angular/core';
import { Nutrition } from '../product/product';
import { AnalyticsService } from './analytics.service';
import { Chart } from 'chart.js/auto';




@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.scss']
})
export class AnalyticsComponent {

  public chart: any;

  constructor(private analyticsService: AnalyticsService) {
    
  }

  ngOnInit() {
    this.createChart();

    var months = this.getAllMonths();

    for (let i = 0; i < months.length; i++) {
      this.analyticsService.getNutrition(months[i].startTime, months[i].endTime).subscribe(nutrition => {
        this.chart.data.datasets[0].data[i] = nutrition.carbohydrates / 100.0
        this.chart.data.datasets[1].data[i] = nutrition.protein / 100.0
        this.chart.data.datasets[2].data[i] = nutrition.sugar / 100.0
        this.chart.data.datasets[3].data[i] = nutrition.fat / 100.0
        this.chart.update()
      })
    }
  }

  createChart(){
  
    this.chart = new Chart("MyChart", {
      type: 'bar',
      data: {// values on X-Axis
        labels: ['January','February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'], 
	       datasets: [
          {
            label: "Carbohydrates",
            data: [] = [0,0,0,0,0,0,0,0,0,0,0,0],
            backgroundColor: 'green'
          },
          {
            label: "Protein",
            data: [] = [0,0,0,0,0,0,0,0,0,0,0,0],
            backgroundColor: 'red'
          },
          {
            label: "Sugar",
            data: [] = [0,0,0,0,0,0,0,0,0,0,0,0],
            backgroundColor: 'yellow'
          },
          {
            label: "Fat",
            data: [] = [0,0,0,0,0,0,0,0,0,0,0,0],
            backgroundColor: 'purple'
          },
        ]
      },
      options: {
        maintainAspectRatio: false,
        scales: {
          y: {
            title: {
              display: true,
              text: "$ (Hundred g)"
            }
          }
        },
        plugins: {
          title: {
              display: true,
              text: 'Nutrition for Current Year'
          }
      }
      }
      
    });
  }

  





  private getAllMonths() {
    var date = new Date();

    var result = [];

    for (let i = 0; i < 12; i++) {
      result.push({
        startTime: new Date(date.getFullYear(), i, 1).toLocaleDateString('en-CA'),
        endTime: new Date(date.getFullYear(), i + 1, 0).toLocaleDateString('en-CA'),
        nutrition: new Nutrition(100, 0, 0, 0, 0, 0)
      })
    }

    return result;
  }



}
