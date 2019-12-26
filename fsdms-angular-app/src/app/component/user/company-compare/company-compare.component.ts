import { Component, OnInit, OnDestroy } from '@angular/core';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { FormBuilder, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { LogService } from '../../../service/log.service';
import { CompanyService } from '../../../service/company.service';
import { GenerateChartService } from '../../../service/generate-chart.service';
import { SectorService } from '../../../service/sector.service';
import { Company } from '../../../model/company.model';
import { Sector } from '../../../model/sector.model';
import { StockPrice } from '../../../model/stockprice.mode';
import { Consolidation } from '../../../model/consolidation.model';
import { finalize } from 'rxjs/operators';
import { Subject } from 'rxjs';


@Component({
  selector: 'app-company-compare',
  templateUrl: './company-compare.component.html',
  styleUrls: ['./company-compare.component.less'],
  providers: [GenerateChartService]
})
export class CompanyCompareComponent implements OnInit, OnDestroy {

  // compareName: string; // An indicator to decide whether to use service or not. Don't need it at the moment.
  consolData: Consolidation;

  debounceText = new Subject<string>();
  debounceResults: Array<Company>;

  compareForm = this.formBuilder.group({
    compareCategory: ['', Validators.required],
    stockExchange: ['', Validators.required],
    companyName: ['', Validators.required],
    companyStockCode: ['', Validators.required],
    sectorName: ['', Validators.required],
    fromPeriod: [''],
    toPeriod: [''],
    debounceRes: [new Company()]
  });

  // SelectControlValueAccessor - Customizing option selection
  compareFn(c1: Company, c2: Company): boolean {
    return c1 && c2 ? c1.companyName === c2.companyName : c1 === c2;
  }

  get compareCategory() { return this.compareForm.get('compareCategory'); }
  get stockExchange() { return this.compareForm.get('stockExchange'); }
  get companyName() { return this.compareForm.get('companyName'); }
  get companyStockCode() { return this.compareForm.get('companyStockCode'); }
  get sectorName() { return this.compareForm.get('sectorName'); }
  get fromPeriod() { return this.compareForm.get('fromPeriod'); }
  get toPeriod() { return this.compareForm.get('toPeriod'); }
  get debounceRes() { return this.compareForm.get('debounceRes'); }

  constructor(private formBuilder: FormBuilder, private datePipe: DatePipe,
              private companySrv: CompanyService, private SectorSrv: SectorService,
              private genChartSrv: GenerateChartService, private logSrv: LogService) { }

  ngOnInit() {
    this.logSrv.log('CompanyCompareComponent-ngOnInit');

    // subscribe
    this.debounceSubscription();
  }

  ngOnDestroy() { // unsubscribe
    this.debounceText.unsubscribe();
  }

  debounceSearch(comsearch: string) { // Debouncing search requests
    this.debounceText.next(comsearch);
  }

  debounceSubscription() {
    this.debounceText.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap(
        comsearch => this.companySrv.getCompaniesLikeDebounce(comsearch))
    ).subscribe(
      response => {
        if (response.status === 200) {
          this.logSrv.log('debounceSubscription = ', response.data.result);
          this.debounceResults = response.data.result;
        }
      }
    );
  }

  debounceSelectChange() {
    this.logSrv.log('debounceSelectChange happened');
    if (this.debounceRes.value) {
      this.compareForm.patchValue({
        stockExchange: this.debounceRes.value.stockExchange,
        companyName: this.debounceRes.value.companyName,
        companyStockCode: this.debounceRes.value.companyStockCode,
        sectorName: this.debounceRes.value.sectorName
      });
    }
    this.debounceResults = null;
  }

  onSubmit() {
    // submit for company stockprice
    if (this.compareCategory.value === 'COMPANY') {
      if ((this.fromPeriod.value && this.fromPeriod.value !== '') && (this.toPeriod.value && this.toPeriod.value !== '')) {
        const fromDateTime = this.datePipe.transform(this.fromPeriod.value, 'yyyy-MM-dd HH:mm:ss');
        const toDateTime = this.datePipe.transform(this.toPeriod.value, 'yyyy-MM-dd HH:mm:ss');
        this.getPricesWithStockCodeAndDateTime(this.companyName.value, this.companyStockCode.value, fromDateTime, toDateTime);
      } else {
        this.getPricesWithStockCodeOnly(this.companyName.value, this.companyStockCode.value);
      }
    }

    // submit for sector stockprice
    if (this.compareCategory.value === 'SECTOR') {
      if ((this.fromPeriod.value && this.fromPeriod.value !== '') && (this.toPeriod.value && this.toPeriod.value !== '')) {
        const fromDateTime = this.datePipe.transform(this.fromPeriod.value, 'yyyy-MM-dd HH:mm:ss');
        const toDateTime = this.datePipe.transform(this.toPeriod.value, 'yyyy-MM-dd HH:mm:ss');
        this.getPricesWithSectorNameAndDateTime(this.sectorName.value, fromDateTime, toDateTime);
      } else {
        this.getPricesWithSectorNameOnly(this.sectorName.value);
      }
    }
  }

  getPricesWithStockCodeOnly(companyName: string, companyStockCode: string) {
    this.companySrv.getPricesByStockCode(companyStockCode).pipe(
      finalize(() => {
        this.compareForm.reset();
      })
    ).subscribe( // must call subscribe() or nothing happens. Just call post does not initiate the expected request
      response => {
        if (response.status === 200) {
          this.logSrv.log('getPricesWithStockCodeOnly = ', response.data.result);
          const result: Array<StockPrice> = response.data.result;
          this.consolData = {...this.consolidate(companyName, result)};

          // pass consolData to child directly and don't need go through service hrere
          // this.genChartSrv.ccShareData = {...this.consolidate(companyName, result)};
          // this.compareName = companyName;
        }
      }
    );
  }

  getPricesWithStockCodeAndDateTime(companyName: string, companyStockCode: string, dtStart: string, dtEnd: string) {
    this.companySrv.getPricesByStockCodeAndDateTime(
      companyStockCode, { start: dtStart, end: dtEnd }
    ).pipe(
      finalize(() => {
        this.compareForm.reset();
      })
    ).subscribe( // must call subscribe() or nothing happens. Just call post does not initiate the expected request
      response => {
        if (response.status === 200) {
          this.logSrv.log('getPricesWithStockCodeAndDateTime = ', response.data.result);
          const result: Array<StockPrice> = response.data.result;
          this.consolData = {...this.consolidate(companyName, result)};

          // pass consolData to child directly and don't need go through service hrere
          // this.genChartSrv.ccShareData = {...this.consolidate(companyName, result)};
          // this.compareName = companyName;
        }
      }
    );
  }

  getPricesWithSectorNameOnly(sectorName: string) {
    this.SectorSrv.getSectorPricesBySectorName(sectorName).pipe(
      finalize(() => {
        this.compareForm.reset();
      })
    ).subscribe( // must call subscribe() or nothing happens. Just call post does not initiate the expected request
      response => {
        if (response.status === 200) {
          this.logSrv.log('getPricesWithSectorNameOnly = ', response.data.result);
          const result: Array<Sector> = response.data.result;
          this.consolData = {...this.consolidate(sectorName, result)};

          // pass consolData to child directly and don't need go through service hrere
          // this.genChartSrv.ccShareData = {...this.consolidate(sectorName, result)};
          // this.compareName = sectorName;
        }
      }
    );
  }

  getPricesWithSectorNameAndDateTime(sectorName: string, dtStart: string, dtEnd: string) {
    this.SectorSrv.getSectorPricesBySectorNameAndStockDateTime(
      sectorName, { start: dtStart, end: dtEnd }
    ).pipe(
      finalize(() => {
        this.compareForm.reset();
      })
    ).subscribe( // must call subscribe() or nothing happens. Just call post does not initiate the expected request
      response => {
        if (response.status === 200) {
          this.logSrv.log('getPricesWithSectorNameAndDateTime = ', response.data.result);
          const result: Array<Sector> = response.data.result;
          this.consolData = {...this.consolidate(sectorName, result)};

          // pass consolData to child directly and don't need go through service hrere
          // this.genChartSrv.ccShareData = {...this.consolidate(sectorName, result)};
          // this.compareName = sectorName;
        }
      }
    );
  }

  consolidate(consolName: string, respRes: Array<any>) {
    if (this.compareCategory.value === 'COMPANY') {
      const result: Array<StockPrice> = respRes;
      const compareDataTemp = [];
      result.forEach((element, index, array) => {
        compareDataTemp.push([new Date(element.stockDateTime).getTime(), element.currentPrice]);
      });
      // consolidate & pass to the generate-chart
      const genDataTemp: Consolidation = {
        compareName: consolName,
        compareData: compareDataTemp
      };
      return genDataTemp;
    }

    if (this.compareCategory.value === 'SECTOR') {
      const result: Array<Sector> = respRes;
      const compareDataTemp = [];
      result.forEach((element, index, array) => {
        compareDataTemp.push([new Date(element.sectorDateTime).getTime(), element.sectorPrice]);
      });
      // consolidate & pass to the generate-chart
      const genDataTemp: Consolidation = {
        compareName: consolName,
        compareData: compareDataTemp
      };
      return genDataTemp;
    }
  }

}
