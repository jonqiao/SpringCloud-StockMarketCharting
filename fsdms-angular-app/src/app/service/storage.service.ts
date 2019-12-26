import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  getLocalStorage() {
    return window.localStorage;
  }

  getStorage() {
    return window.sessionStorage;
  }

  clear(): void {
    this.getStorage().clear();
  }

  setItem(key: string, value: string): void {
    this.getStorage().setItem(key, value);
  }

  getItem(key: string): string {
    return this.getStorage().getItem(key);
  }

  removeItem(key: string): void {
    this.getStorage().removeItem(key);
  }

}
