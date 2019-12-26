import { Injectable } from '@angular/core';
import { Consolidation } from '../model/consolidation.model';


@Injectable()
export class GenerateChartService {

  // company-compare
  ccShareData: Consolidation;

  // sector-compare
  scShareData: Consolidation;

  constructor() { }
}
