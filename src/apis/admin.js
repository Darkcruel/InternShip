import * as http from "./http";

export async function loadRasaData() {
  return http.rasaAdminInstance.get("/rasa/getTrainData");
}

export async function loadFAQData() {
  return http.rasaAdminInstance.get("/bert/getData");
}

export async function saveRasaData(data) {
  return http.rasaAdminInstance.post("/rasa/toYamlAll", data);
}

export async function saveFAQData(data) {
  return http.rasaAdminInstance.post("/bert/setData", data);
}


