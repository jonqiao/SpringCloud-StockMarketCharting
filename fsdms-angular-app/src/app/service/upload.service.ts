import { Injectable } from '@angular/core';
import { FileUploader } from 'ng2-file-upload';
import { StorageService } from './storage.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private storageSrv: StorageService) { }

  baseUrl: string = environment.getBaseUrl('upl');

  token: string = 'Bearer ' + this.storageSrv.getItem('token');

  getUploader(): FileUploader {
    const uploader = new FileUploader({
      url: this.baseUrl + 'upload/admin/importexcel',
      method: 'POST',
      itemAlias: 'excelfile',
      authToken: this.token,
      authTokenHeader: 'Authorization',
      headers: [
        { name: 'customer-header', value: this.token }
      ]
      // notes: Backend service use Multipart but not stream for now.
      // disableMultipart: true, // 'DisableMultipart' must be 'true' for formatDataFunction to be called.
      // formatDataFunctionIsAsync: true,
      // formatDataFunction: async (item) => {
      //   return new Promise( (resolve, reject) => {
      //     resolve({
      //       name: item._file.name,
      //       length: item._file.size,
      //       contentType: item._file.type,
      //       date: new Date()
      //     });
      //   });
      // }
    });

    return uploader;
  }

}
