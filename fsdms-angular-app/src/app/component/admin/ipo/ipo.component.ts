import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { LogService } from '../../../service/log.service';
import { CompanyService } from '../../../service/company.service';
import { Ipo } from '../../../model/ipo.model';
import { finalize } from 'rxjs/operators';

declare var $: any;

@Component({
  selector: 'app-ipo',
  templateUrl: './ipo.component.html',
  styleUrls: ['./ipo.component.less']
})
export class IpoComponent implements OnInit {

  ipoArr: Array<Ipo> = [];
  updateFlag = false;

  ipoForm = this.formBuilder.group({
    companyName: ['', Validators.required],
    stockExchange: ['', Validators.required],
    pricePerShare: ['', Validators.required],
    totalNumberOfShares: ['', Validators.required],
    openDateTime: ['', Validators.required],
    remarks: ['', Validators.required]
  });

  constructor(private formBuilder: FormBuilder, private CompanySrv: CompanyService, private logSrv: LogService) { }

  ngOnInit() {
    this.logSrv.log('IpoComponent-ngOnInit');
    this.refreshTable();
  }

  get companyName() { return this.ipoForm.get('companyName'); }
  get stockExchange() { return this.ipoForm.get('stockExchange'); }
  get pricePerShare() { return this.ipoForm.get('pricePerShare'); }
  get totalNumberOfShares() { return this.ipoForm.get('totalNumberOfShares'); }
  get openDateTime() { return this.ipoForm.get('openDateTime'); }
  get remarks() { return this.ipoForm.get('remarks'); }


  ipoModalNewOpen() {
    this.ipoFormReset();
  }

  ipoModalUptOpen() {
    if ($('#ipoTable').bootstrapTable('getSelections').length > 0) {
      this.ipoForm.patchValue({
        companyName: $('#ipoTable').bootstrapTable('getSelections')[0].companyName,
        stockExchange: $('#ipoTable').bootstrapTable('getSelections')[0].stockExchange,
        pricePerShare: $('#ipoTable').bootstrapTable('getSelections')[0].pricePerShare,
        totalNumberOfShares: $('#ipoTable').bootstrapTable('getSelections')[0].totalNumberOfShares,
        openDateTime: $('#ipoTable').bootstrapTable('getSelections')[0].openDateTime,
        remarks: $('#ipoTable').bootstrapTable('getSelections')[0].remarks
      });
      this.companyName.disable();
    }
  }

  onSubmit() {
    if (this.ipoForm.valid) {
      this.logSrv.log('ipoForm value = ', this.ipoForm.value);
      const ipo = new Ipo();
      ipo.companyName = this.companyName.value;
      ipo.stockExchange = this.stockExchange.value;
      ipo.pricePerShare = this.pricePerShare.value;
      ipo.totalNumberOfShares = this.totalNumberOfShares.value;
      ipo.openDateTime = this.openDateTime.value;
      ipo.remarks = this.remarks.value;

      if (this.updateFlag) {
        this.CompanySrv.updateIpo(ipo.companyName, ipo).pipe(
          finalize(() => {
            this.ipoFormReset();
          })
        ).subscribe( // must call subscribe() or nothing happens. Just call post does not initiate the expected request
          response => {
            if (response.status === 200) {
              this.logSrv.log('updateIpo result = ', response.data.result);
            }
          }
        );
      } else {
        this.CompanySrv.newIpo(ipo).pipe(
          finalize(() => {
            this.ipoFormReset();
          })
        ).subscribe(
          response => {
            if (response.status === 200) {
              this.logSrv.log('newIpo result = ', response.data.result);
            }
          }
        );
      }
    }
  }

  ipoFormReset() {
    // NOTES: this其实是一个Html 元素。but $this 只是个变量名，加$是为说明其是个jquery对象。
    // 而$(this)是个转换，将this表示的dom对象转为jquery对象，这样就可以使用jquery提供的方法操作。
    $('#ipoModal').modal('hide');

    this.updateFlag = false;
    this.ipoForm.reset();
    this.ipoForm.enable();
    if ($('#ipoTable').bootstrapTable('getSelections').length > 0) {
      $('#ipoTable').bootstrapTable('uncheckAll');
    }
  }

  clickEvent() {
    if ($('#ipoTable').bootstrapTable('getSelections').length > 0) {
      this.updateFlag = true;
    } else {
      this.updateFlag = false;
    }
  }

  refreshTable() {
    this.CompanySrv.getIpos().subscribe(
      response => {
        if (response.status === 200) {
          this.ipoArr = response.data.result;
          $('#ipoTable').bootstrapTable('load', this.ipoArr).bootstrapTable('refresh');
        }
      }
    );
  }

}
