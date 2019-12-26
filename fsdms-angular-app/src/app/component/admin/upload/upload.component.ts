import { Component, OnInit } from '@angular/core';
import { UploadService } from '../../../service/upload.service';
import { FileUploader, FileItem } from 'ng2-file-upload';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.less']
})
export class UploadComponent implements OnInit {

  uploader: FileUploader;
  hasBaseDropZoneOver: boolean;
  hasAnotherDropZoneOver: boolean;
  response: any;
  consolidateArray: Array<object>;

  constructor(private uploadSrv: UploadService) {
    this.uploader = this.uploadSrv.getUploader();

    // Note: 必须移除cookie，否则跨越设置的'*'通配符将不会生效，现在backend设置是'*', 如果换成是具体到某个域名的话，这个问题将不会存在
    // 对于ng2-file-upload上传去掉cookie，只需在上传之前设置FileItem的属性withCredentials=false即可
    this.uploader.onBeforeUploadItem = (fileItem: FileItem) => {
      fileItem.withCredentials = false;
    };
    // this.uploader.onAfterAddingFile = (fileItem: FileItem) => {
    //   fileItem.withCredentials = false;
    // };

    this.hasBaseDropZoneOver = false;
    this.hasAnotherDropZoneOver = false;

    this.response = null;

    this.uploader.response.subscribe(
      res => {
        if (JSON.parse(res).status === 200) {
          this.consolidateArray = JSON.parse(res).data.result;
          this.response = null;
        } else {
          this.response = res;
        }

      }
    );

  }

  public fileOverBase(e: any): void {
    this.hasBaseDropZoneOver = e;
  }

  public fileOverAnother(e: any): void {
    this.hasAnotherDropZoneOver = e;
  }

  ngOnInit() { }

}
