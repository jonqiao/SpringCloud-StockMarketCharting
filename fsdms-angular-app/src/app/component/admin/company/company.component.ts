import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { LogService } from '../../../service/log.service';
import { CompanyService } from '../../../service/company.service';
import { Company } from '../../../model/company.model';
import { finalize } from 'rxjs/operators';

declare var $: any;

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.less']
})
export class CompanyComponent implements OnInit {
  companyArr: Array<Company> = [];
  updateFlag = false;

  companyForm = this.formBuilder.group({
    companyName: ['', Validators.required],
    turnover: ['', Validators.required],
    ceo: ['', Validators.required],
    boardOfDirectors: ['', Validators.required],
    stockExchange: ['', Validators.required],
    sectorName: ['', Validators.required],
    briefWriteup: ['', Validators.required],
    companyStockCode: ['', Validators.required],
    active: ['', Validators.required]
  });

  constructor(
    private formBuilder: FormBuilder,
    private CompanySrv: CompanyService,
    private logSrv: LogService
  ) { }

  ngOnInit() {
    this.logSrv.log('CompanyComponent-ngOnInit');
    this.refreshTable();
  }

  get companyName() {
    return this.companyForm.get('companyName');
  }
  get turnover() {
    return this.companyForm.get('turnover');
  }
  get ceo() {
    return this.companyForm.get('ceo');
  }
  get boardOfDirectors() {
    return this.companyForm.get('boardOfDirectors');
  }
  get stockExchange() {
    return this.companyForm.get('stockExchange');
  }
  get sectorName() {
    return this.companyForm.get('sectorName');
  }
  get briefWriteup() {
    return this.companyForm.get('briefWriteup');
  }
  get companyStockCode() {
    return this.companyForm.get('companyStockCode');
  }
  get active() {
    return this.companyForm.get('active');
  }

  comModalNewOpen() {
    this.companyFormReset();
  }

  comModalUptOpen() {
    if ($('#companyTable').bootstrapTable('getSelections').length > 0) {
      this.companyForm.patchValue({
        companyName: $('#companyTable').bootstrapTable('getSelections')[0]
          .companyName,
        turnover: $('#companyTable').bootstrapTable('getSelections')[0]
          .turnover,
        ceo: $('#companyTable').bootstrapTable('getSelections')[0].ceo,
        boardOfDirectors: $('#companyTable').bootstrapTable('getSelections')[0]
          .boardOfDirectors,
        stockExchange: $('#companyTable').bootstrapTable('getSelections')[0]
          .stockExchange,
        sectorName: $('#companyTable').bootstrapTable('getSelections')[0]
          .sectorName,
        briefWriteup: $('#companyTable').bootstrapTable('getSelections')[0]
          .briefWriteup,
        companyStockCode: $('#companyTable').bootstrapTable('getSelections')[0]
          .companyStockCode,
        active: $('#companyTable').bootstrapTable('getSelections')[0].active
      });
      this.companyName.disable();
      this.active.disable();
    }
  }

  comActive() {
    if ($('#companyTable').bootstrapTable('getSelections').length > 0) {
      this.logSrv.log($('#companyTable').bootstrapTable('getSelections')[0]);
      const companyName = $('#companyTable').bootstrapTable('getSelections')[0]
        .companyName;
      this.CompanySrv.activeCompany(companyName).subscribe(response => {
        if (response.status === 200) {
          this.logSrv.log('activeCompany-result = ', response.data.result);
        }
      });
    }
  }

  comDeactive() {
    if ($('#companyTable').bootstrapTable('getSelections').length > 0) {
      this.logSrv.log($('#companyTable').bootstrapTable('getSelections')[0]);
      const companyName = $('#companyTable').bootstrapTable('getSelections')[0]
        .companyName;
      this.CompanySrv.deactiveCompany(companyName).subscribe(response => {
        if (response.status === 200) {
          this.logSrv.log('deactiveCompany-result = ', response.data.result);
        }
      });
    }
  }

  onSubmit() {
    if (this.companyForm.valid) {
      this.logSrv.log('comNewForm value = ', this.companyForm.value);
      const com = new Company();
      com.companyName = this.companyName.value;
      com.turnover = this.turnover.value;
      com.ceo = this.ceo.value;
      com.boardOfDirectors = this.boardOfDirectors.value;
      com.stockExchange = this.stockExchange.value;
      com.sectorName = this.sectorName.value;
      com.briefWriteup = this.briefWriteup.value;
      com.companyStockCode = this.companyStockCode.value;
      com.active = this.active.value;

      if (this.updateFlag) {
        this.CompanySrv.updateCompany(com.companyName, com)
          .pipe(
            finalize(() => {
              this.companyFormReset();
            })
          )
          .subscribe(
            // must call subscribe() or nothing happens. Just call post does not initiate the expected request
            response => {
              if (response.status === 200) {
                this.logSrv.log(
                  'updateCompany result = ',
                  response.data.result
                );
              }
            }
          );
      } else {
        this.CompanySrv.newCompany(com)
          .pipe(
            finalize(() => {
              this.companyFormReset();
            })
          )
          .subscribe(response => {
            if (response.status === 200) {
              this.logSrv.log('newCompany result = ', response.data.result);
            }
          });
      }
    }
  }

  companyFormReset() {
    // NOTES: this其实是一个Html 元素。but $this 只是个变量名，加$是为说明其是个jquery对象。
    // 而$(this)是个转换，将this表示的dom对象转为jquery对象，这样就可以使用jquery提供的方法操作。
    $('#companyModal').modal('hide');

    this.updateFlag = false;
    this.companyForm.reset();
    this.companyForm.enable();
    if ($('#companyTable').bootstrapTable('getSelections').length > 0) {
      $('#companyTable').bootstrapTable('uncheckAll');
    }
  }

  clickEvent() {
    if ($('#companyTable').bootstrapTable('getSelections').length > 0) {
      this.updateFlag = true;
    } else {
      this.updateFlag = false;
    }
  }

  refreshTable() {
    this.CompanySrv.getCompanies().subscribe(response => {
      if (response.status === 200) {
        this.companyArr = response.data.result;
        $('#companyTable')
          .bootstrapTable('load', this.companyArr)
          .bootstrapTable('refresh');
      }
    });
  }
}
