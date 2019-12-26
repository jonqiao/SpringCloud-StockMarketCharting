import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { LogService } from '../../../service/log.service';
import { ExchangeService } from '../../../service/exchange.service';
import { Exchange } from '../../../model/exchange.model';
import { finalize } from 'rxjs/operators';

declare var $: any;
@Component({
  selector: 'app-stock-exchange',
  templateUrl: './stock-exchange.component.html',
  styleUrls: ['./stock-exchange.component.less']
})
export class StockExchangeComponent implements OnInit {

  exchangeArr: Array<Exchange> = [];
  updateFlag = false;

  exchangeForm = this.formBuilder.group({
    stockExchange: ['', Validators.required],
    brief: ['', Validators.required],
    contactAddress: ['', Validators.required],
    remarks: ['', Validators.required]
  });

  constructor(private formBuilder: FormBuilder, private ExchangeSrc: ExchangeService, private logSrv: LogService) { }

  ngOnInit() {
    this.logSrv.log('StockExchangeComponent-ngOnInit');
    this.refreshTable();
  }

  get stockExchange() { return this.exchangeForm.get('stockExchange'); }
  get brief() { return this.exchangeForm.get('brief'); }
  get contactAddress() { return this.exchangeForm.get('contactAddress'); }
  get remarks() { return this.exchangeForm.get('remarks'); }

  excModalNewOpen() {
    this.exchangeFormReset();
  }

  excModalUptOpen() {
    if ($('#exchangeTable').bootstrapTable('getSelections').length > 0) {
      this.exchangeForm.patchValue({
        stockExchange: $('#exchangeTable').bootstrapTable('getSelections')[0].stockExchange,
        brief: $('#exchangeTable').bootstrapTable('getSelections')[0].brief,
        contactAddress: $('#exchangeTable').bootstrapTable('getSelections')[0].contactAddress,
        remarks: $('#exchangeTable').bootstrapTable('getSelections')[0].remarks
      });
      this.stockExchange.disable();
    }
  }

  onSubmit() {
    if (this.exchangeForm.valid) {
      this.logSrv.log('exchangeForm value = ', this.exchangeForm.value);
      const exc = new Exchange();
      exc.stockExchange = this.stockExchange.value;
      exc.brief = this.brief.value;
      exc.contactAddress = this.contactAddress.value;
      exc.remarks = this.remarks.value;

      if (this.updateFlag) {
        this.ExchangeSrc.updateExchange(exc.stockExchange, exc).pipe(
          finalize(() => {
            this.exchangeFormReset();
          })
        ).subscribe( // must call subscribe() or nothing happens. Just call post does not initiate the expected request
          response => {
            if (response.status === 200) {
              this.logSrv.log('updateExchange result = ', response.data.result);
            }
          }
        );
      } else {
        this.ExchangeSrc.newExchange(exc).pipe(
          finalize(() => {
            this.exchangeFormReset();
          })
        ).subscribe(
          response => {
            if (response.status === 200) {
              this.logSrv.log('newExchange result = ', response.data.result);
            }
          }
        );
      }
    }
  }

  exchangeFormReset() {
    // NOTES: this其实是一个Html 元素。but $this 只是个变量名，加$是为说明其是个jquery对象。
    // 而$(this)是个转换，将this表示的dom对象转为jquery对象，这样就可以使用jquery提供的方法操作。
    $('#exchangeModal').modal('hide');

    this.updateFlag = false;
    this.exchangeForm.reset();
    this.exchangeForm.enable();
    if ($('#exchangeTable').bootstrapTable('getSelections').length > 0) {
      $('#exchangeTable').bootstrapTable('uncheckAll');
    }
  }

  clickEvent() {
    if ($('#exchangeTable').bootstrapTable('getSelections').length > 0) {
      this.updateFlag = true;
    } else {
      this.updateFlag = false;
    }
  }

  refreshTable() {
    this.ExchangeSrc.getExchanges().subscribe(
      response => {
        if (response.status === 200) {
          this.exchangeArr = response.data.result;
          $('#exchangeTable').bootstrapTable('load', this.exchangeArr).bootstrapTable('refresh');
        }
      }
    );
  }

}
