import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { LogService } from '../../../service/log.service';
import { SectorService } from '../../../service/sector.service';
import { Sector } from '../../../model/sector.model';
import { finalize } from 'rxjs/operators';

declare var $: any;

@Component({
  selector: 'app-sector',
  templateUrl: './sector.component.html',
  styleUrls: ['./sector.component.less']
})
export class SectorComponent implements OnInit {

  sectorArr: Array<Sector> = [];
  updateFlag = false;

  sectorForm = this.formBuilder.group({
    sectorName: ['', Validators.required],
    brief: ['', Validators.required]
  });

  constructor(private formBuilder: FormBuilder, private SectorSrv: SectorService, private logSrv: LogService) { }

  ngOnInit() {
    this.logSrv.log('SectorComponent-ngOnInit');
    this.refreshTable();
  }

  get sectorName() { return this.sectorForm.get('sectorName'); }
  get brief() { return this.sectorForm.get('brief'); }

  sctModalNewOpen() {
    this.sectorFormReset();
  }

  sctModalUptOpen() {
    if ($('#sectorTable').bootstrapTable('getSelections').length > 0) {
      this.sectorForm.patchValue({
        sectorName: $('#sectorTable').bootstrapTable('getSelections')[0].sectorName,
        brief: $('#sectorTable').bootstrapTable('getSelections')[0].brief
      });
      this.sectorName.disable();
    }
  }

  onSubmit() {
    if (this.sectorForm.valid) {
      this.logSrv.log('sectorForm value = ', this.sectorForm.value);
      const sector = new Sector();
      sector.sectorName = this.sectorName.value;
      sector.brief = this.brief.value;

      if (this.updateFlag) {
        this.SectorSrv.updateSector(sector.sectorName, sector).pipe(
          finalize(() => {
            this.sectorFormReset();
          })
        ).subscribe( // must call subscribe() or nothing happens. Just call post does not initiate the expected request
          response => {
            if (response.status === 200) {
              this.logSrv.log('updateSector result = ', response.data.result);
            }
          }
        );
      } else {
        this.SectorSrv.newSector(sector).pipe(
          finalize(() => {
            this.sectorFormReset();
          })
        ).subscribe(
          response => {
            if (response.status === 200) {
              this.logSrv.log('newSector result = ', response.data.result);
            }
          }
        );
      }
    }
  }

  sectorFormReset() {
    // NOTES: this其实是一个Html 元素。but $this 只是个变量名，加$是为说明其是个jquery对象。
    // 而$(this)是个转换，将this表示的dom对象转为jquery对象，这样就可以使用jquery提供的方法操作。
    $('#exchangeModal').modal('hide');

    this.updateFlag = false;
    this.sectorForm.reset();
    this.sectorForm.enable();
    if ($('#sectorTable').bootstrapTable('getSelections').length > 0) {
      $('#sectorTable').bootstrapTable('uncheckAll');
    }
  }

  clickEvent() {
    if ($('#sectorTable').bootstrapTable('getSelections').length > 0) {
      this.updateFlag = true;
    } else {
      this.updateFlag = false;
    }
  }

  refreshTable() {
    this.SectorSrv.getSectors().subscribe(
      response => {
        if (response.status === 200) {
          this.sectorArr = response.data.result;
          $('#sectorTable').bootstrapTable('load', this.sectorArr).bootstrapTable('refresh');
        }
      }
    );
  }
}
