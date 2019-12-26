import { Component, OnInit, Input } from '@angular/core';
import { GenerateChartService } from '../../../service/generate-chart.service';
import { LogService } from '../../../service/log.service';
import { Consolidation } from '../../../model/consolidation.model';

// import * as Highcharts from 'highcharts';
import * as Highcharts from 'highcharts/highstock';
import HC_themes from 'highcharts/themes/dark-unica';
HC_themes(Highcharts);
import HC_more from 'highcharts/highcharts-more';
HC_more(Highcharts);
import HC_exporting from 'highcharts/modules/exporting';
HC_exporting(Highcharts);
import HC_exportdata from 'highcharts/modules/export-data';
HC_exportdata(Highcharts);
import HC_windbarb from 'highcharts/modules/windbarb';
HC_windbarb(Highcharts);


import { testData1, testData2 } from '../../../model/test.data';

declare var $: any;

@Component({
  selector: 'app-generate-chart',
  templateUrl: './generate-chart.component.html',
  styleUrls: ['./generate-chart.component.less']
})
export class GenerateChartComponent implements OnInit {

  // Array<Consolidation> = [
  //   { compareName: 'I8M',
  //     compareData: [[201912050000, 20.99], [201912060000, 30.99], ...]
  //   },
  //   { compareName: 'IT'
  //     compareData: [[201912050000, 20.99], [201912070000, 30.99], ...]
  //   }
  // ];

  // tslint:disable-next-line: variable-name
  // private _inputName: string; // An indicator to decide whether to use service or not. Don't need it at the moment.
  // tslint:disable-next-line: variable-name
  private _inputData: Consolidation;

  genChart;
  newConstValue = 'stockChart';
  switchTypeValue = 'spline';

  constructor(
    // private genChartSrv: GenerateChartService,
    private logSrv: LogService) { }

  stockChartOptions: Highcharts.Options = {
    title: { text: 'STOCK PRICE COMPARATION' },
    subtitle: { text: 'NOT REAL-TIME DATA' },
    credits: { enabled: true, text: 'Power by Jon Q', href: 'http://localhost:4200/' },
    rangeSelector: {
      allButtonsEnabled: true,
      inputDateFormat: '%Y-%m-%d',
      inputEditDateFormat: '%Y-%m-%d',
      // inputDateParser: function (value) {
      //   return (new Date(value)).getTime();
      // }
    },
    chart: { type: this.switchTypeValue, zoomType: 'x', panning: true, panKey: 'shift' }, // 图表缩放和按键平移
    xAxis: { // X轴
      type: 'datetime',
      // title: { text: 'DateTime' },
      // labels: { align: 'center' },
      dateTimeLabelFormats: {
        millisecond: '%H:%M:%S.%L',
        second: '%H:%M:%S',
        minute: '%H:%M',
        hour: '%H:%M',
        day: '%m-%d',
        week: '%m-%d',
        month: '%Y-%m',
        year: '%Y'
      }
    },
    yAxis: { // Y轴
      title: { text: 'Stock Price' },
      labels: { format: '{value} USD' }
    },
    tooltip: { // 数据提示框
      dateTimeLabelFormats: {
        millisecond: '%H:%M:%S.%L',
        second: '%H:%M:%S',
        minute: '%H:%M',
        hour: '%H:%M',
        day: '%m-%d',
        week: '%m-%d',
        month: '%Y-%m',
        year: '%Y'
      }
    },
    series: []
  };

  Highcharts = Highcharts; // 必填, 类型为 Object. 这是一个 Highcharts 实例, 包含必需的核心、可选的模块、插件、映射、包装器和设置全局选项的 setOptions.
  // 'chart' - 标准的 Highcharts 构造函数 - 适用于任何 Highcharts 实例, 是默认值
  // 'stockChart' - Highstock 的构造函数 - 需要 Highstock 模块
  // 'mapChart' - Highmaps 的构造函数 - 需要 Highmaps 或 map 木块
  chartConstructor = this.newConstValue; // 可选, 类型为 String
  chartOptions = this.stockChartOptions; // 必填, 类型为 Object
  updateFlag = false; // 可选, 类型为 Boolean, 默认为 false.
  oneToOneFlag = true; // 可选, 类型为 Boolean, 默认为 false
  runOutsideAngular = false; // 可选, 类型为 Boolean, 默认为 false
  chartCallback = (chart) => { this.genChart = chart; }; // 可选, 类型为 Function, 默认为空, 这是图表创建完成后执行的回调函数. 该函数的的第一参数将保存创建的图表. 函数中的 this 默认指向图表.

  ngOnInit() {
    this.logSrv.log('GenerateChartComponent-ngOnInit');
  }


  @Input()
  set inputData(invalue: Consolidation) {
    this._inputData = invalue;

    if (invalue) {
      const exist = this.genChart.series.some((value, index) => value.name === invalue.compareName);
      if (exist) {
        this.logSrv.log('GenerateChartComponent-inputData-update', invalue);
        this.genChart.update({ // update according to id
          series: [{
            type: undefined,
            id: invalue.compareName,
            name: invalue.compareName,
            tooltip: { valueDecimals: 2 },
            data: invalue.compareData
          }]
        });
      } else {
        this.logSrv.log('GenerateChartComponent-inputData-addnew', invalue);
        this.genChart.addSeries({
          type: undefined,
          id: invalue.compareName,
          name: invalue.compareName,
          tooltip: { valueDecimals: 2 },
          data: invalue.compareData
        });
      }
    }
  }

  get inpuData() { return this._inputData; }


  // use inpuData directly instead of service. Don't need this method at the moment.
  // @Input()
  // set inputName(invalue: string) {
  //   this._inputName = invalue;

  //   if (invalue) {
  //     const exist = this.genChart.series.some((value, index) => value.name === this.genChartSrv.ccShareData.compareName);
  //     if (exist) {
  //       this.logSrv.log('GenerateChartComponent-inputName-update', this.genChartSrv.ccShareData);
  //       this.genChart.update({ // update according to id
  //         series: [{
  //           id: this.genChartSrv.ccShareData.compareName,
  //           name: this.genChartSrv.ccShareData.compareName,
  //           tooltip: { valueDecimals: 2 },
  //           data: this.genChartSrv.ccShareData.compareData
  //         }]
  //       });
  //     } else {
  //       this.logSrv.log('GenerateChartComponent-inputName-addnew', this.genChartSrv.ccShareData);
  //       this.genChart.addSeries({
  //         id: this.genChartSrv.ccShareData.compareName,
  //         name: this.genChartSrv.ccShareData.compareName,
  //         tooltip: { valueDecimals: 2 },
  //         data: this.genChartSrv.ccShareData.compareData
  //       });
  //     }
  //   }
  // }

  // get inputName(): string { return this._inputName; }


  newConstructor() {
    this.genChart.destroy();

    switch (this.newConstValue) {
      case 'chart':
        this.newConstValue = 'stockChart';
        this.genChart = Highcharts.stockChart(document.getElementById('hcidjon'), this.chartOptions, this.chartCallback);
        break;
      case 'stockChart':
        this.newConstValue = 'chart';
        this.genChart = Highcharts.chart(document.getElementById('hcidjon'), this.chartOptions, this.chartCallback);
        break;
    }
  }

  // for test only
  GenCharts() {
    const series1 = { id: 'sname1', name: 'sname1', // seriesType
      tooltip: { valueDecimals: 2 }, data: testData1 };
    const series2 = { id: 'sname2', name: 'sname2', // seriesType
      tooltip: { valueDecimals: 2 }, data: testData2 };

    const num = Math.random() * 10;
    if (num > 5) {
      this.genChart.addSeries(series1);
    } else {
      this.genChart.addSeries(series2);
    }
  }

  RmOneCharts() {
    if (this.genChart.series.length) {
      this.genChart.series[0].remove();
    }
  }

  RmAllCharts() {
    let i = this.genChart.series.length;
    while (i--) {
      this.genChart.series[i].remove();
    }
  }

  switchType() {
    switch (this.switchTypeValue) {
      case 'areaspline':
        this.genChart.update({ chart: {type: 'column'} }); // 柱状图
        this.switchTypeValue = 'column';
        break;
      case 'column':
        this.genChart.update({ chart: {type: 'spline'} }); // 曲线图
        this.switchTypeValue = 'spline';
        break;
      case 'spline':
        this.genChart.update({ chart: {type: 'area'} }); // 面积图
        this.switchTypeValue = 'area';
        break;
      case 'area':
        this.genChart.update({ chart: {type: 'scatter'} }); // 散点图
        this.switchTypeValue = 'scatter';
        break;
      case 'scatter':
        this.genChart.update({ chart: {type: 'line'} }); // 直线图
        this.switchTypeValue = 'line';
        break;
      case 'line':
        this.genChart.update({ chart: {type: 'areaspline'} }); // 曲线面积图
        this.switchTypeValue = 'areaspline';
        break;
    }
  }

}
