import { Component, OnInit, OnDestroy } from '@angular/core';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { FormBuilder, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { LogService } from '../../../service/log.service';
import { SectorService } from '../../../service/sector.service';
import { Sector } from '../../../model/sector.model';
import { Consolidation } from '../../../model/consolidation.model';
import { finalize } from 'rxjs/operators';
import { Subject } from 'rxjs';


@Component({
  selector: 'app-sector-compare',
  templateUrl: './sector-compare.component.html',
  styleUrls: ['./sector-compare.component.less']
})
export class SectorCompareComponent implements OnInit, OnDestroy {

  // compareName: string; // An indicator to decide whether to use service or not. Don't need it at the moment.
  consolData: Consolidation;

  debounceText = new Subject<string>();
  debounceResults: Array<Sector>;

  compareForm = this.formBuilder.group({
    sectorName: ['', Validators.required],
    fromPeriod: [''],
    toPeriod: [''],
    debounceRes: [new Sector()]
  });

  get sectorName() { return this.compareForm.get('sectorName'); }
  get fromPeriod() { return this.compareForm.get('fromPeriod'); }
  get toPeriod() { return this.compareForm.get('toPeriod'); }
  get debounceRes() { return this.compareForm.get('debounceRes'); }

  constructor(private formBuilder: FormBuilder, private datePipe: DatePipe,
              private SectorSrv: SectorService, private logSrv: LogService) { }

  ngOnInit() {
    this.logSrv.log('SectorCompareComponent-ngOnInit');

    // subscribe
    this.debounceSubscription();
  }

  ngOnDestroy() { // unsubscribe
    this.debounceText.unsubscribe();
  }

  debounceSearch(sctsearch: string) { // Debouncing search requests
    this.debounceText.next(sctsearch);
  }

  debounceSubscription() {
    this.debounceText.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap(
        sctsearch => this.SectorSrv.getSectorsLikeDebounce(sctsearch))
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
        sectorName: this.debounceRes.value.sectorName
      });
    }
    this.debounceResults = null;
  }

  onSubmit() {
    // submit for sector stockprice
    if ((this.fromPeriod.value && this.fromPeriod.value !== '') && (this.toPeriod.value && this.toPeriod.value !== '')) {
      const fromDateTime = this.datePipe.transform(this.fromPeriod.value, 'yyyy-MM-dd HH:mm:ss');
      const toDateTime = this.datePipe.transform(this.toPeriod.value, 'yyyy-MM-dd HH:mm:ss');
      this.getPricesWithSectorNameAndDateTime(this.sectorName.value, fromDateTime, toDateTime);
    } else {
      this.getPricesWithSectorNameOnly(this.sectorName.value);
    }
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
        }
      }
    );
  }

  consolidate(consolName: string, respRes: Array<any>) {
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
