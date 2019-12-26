/* There are 3 ways to service register
1. By default, the Angular CLI command ng generate service registers a provider with the root injector for your service
   by including provider metadata in the @Injectable() decorator. When you provide the service at the root level, Angular creates a single,
   shared instance of HeroService and injects it into any class that asks for it. e.g.:
      @Injectable({
        providedIn: 'root',
      })
2. When you register a provider with a specific NgModule, the same instance of a service is available to all components in that NgModule.
   To register at this level, use the providers property of the @NgModule() decorator. e.g.:
      @NgModule({
        providers: [
          BackendService,
          Logger
        ],
        ...
      })
3. When you register a provider at the component level, you get a new instance of the service with each new instance of that component.
    At the component level, register a service provider in the providers property of the @Component() metadata. e.g.:
      @Component({
        selector:    'app-hero-list',
        templateUrl: './hero-list.component.html',
        providers:  [ HeroService ]
      })
*/




// options = {
//   chart: {
//     type: 'spline'                          //指定图表的类型，默认是折线图（line, spline, bar, pie,）
//   },
//   title: {
//       text: 'first chart'                 // 标题
//   },
//   xAxis: {
//       categories: ['datetime']   // x 轴分类
//   },
//   yAxis: {
//       title: {
//           text: 'Price'                // y 轴标题
//       }
//   },
//   tooltip: {
//     backgroundColor: {
//       linearGradient: [0, 0, 0, 60],
//       stops: [
//         [0, '#FFFFFF'],
//         [1, '#E0E0E0']
//       ]
//     },
//     borderWidth: 1,
//     borderColor: '#AAA',
//     crosshairs: [{            // 设置准星线样式
//       width: 1,
//       color: 'green'
//   }, {
//       width: 1,
//       color: 'red',
//       dashStyle: 'longdashdot',
//       zIndex: 100
//   }]
//   },
//   series: [{  // 数据列
//       name: '50021', // 数据列名
//       data: [1, 0, 4, 6, 9, 10, 4, 5, 7, 14, 16, 20]  // 数据
//   }, {
//       name: '50022',
//       data: [5, 7, 3, 6, 9, 12, 8, 11, 15, 19, 14, 17]
//   }]
// };
