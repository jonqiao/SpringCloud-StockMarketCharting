function getBaseUrl(val: string): string {
  const gateSwitch = true; // gate switch between gateway and service-directly

  let baseUrl: string;
  // const baseGateWay = 'http://127.0.0.1:9049/fsdms/api/v1/';
  const baseGateWay = 'http://127.0.0.1/fsdms/api/v1/';

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
  production: true,
  debug: false,
  getBaseUrl
};
