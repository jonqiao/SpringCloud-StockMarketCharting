// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.


// fsdms-zuul-srv: /               - http://127.0.0.1:9049/
// fsdms-upload-srv: /upl/**       - http://127.0.0.1:9050/fsdms/api/v1/
// fsdms-exchange-srv: /exc/**     - http://127.0.0.1:9051/fsdms/api/v1/
// fsdms-company-srv: /com/**      - http://127.0.0.1:9052/fsdms/api/v1/
// fsdms-sector-srv: /sct/**       - http://127.0.0.1:9053/fsdms/api/v1/
// fsdms-security-srv: /scs/**     - http://127.0.0.1:9054/fsdms/api/v1/

function getBaseUrl(val: string): string {
  const gateSwitch = false; // gate switch between gateway and service-directly

  let baseUrl: string;
  const baseGateWay = 'http://127.0.0.1:9049/fsdms/api/v1/';

  switch (val) {
    case 'upl':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'upl/';
      } else {
        baseUrl = 'http://127.0.0.1:9050/fsdms/api/v1/';
      }
      break;
    case 'exc':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'exc/';
      } else {
        baseUrl = 'http://127.0.0.1:9051/fsdms/api/v1/';
      }
      break;
    case 'com':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'com/';
      } else {
        baseUrl = 'http://127.0.0.1:9052/fsdms/api/v1/';
      }
      break;
    case 'sct':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'sct/';
      } else {
        baseUrl = 'http://127.0.0.1:9053/fsdms/api/v1/';
      }
      break;
    case 'scs':
      if (gateSwitch) {
        baseUrl = baseGateWay + 'scs/';
      } else {
        baseUrl = 'http://127.0.0.1:9054/fsdms/api/v1/';
      }
      break;
    default:
      baseUrl = 'http://127.0.0.1/';
  }
  return baseUrl;
}

export const environment = {
  production: false,
  debug: false,
  getBaseUrl
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
