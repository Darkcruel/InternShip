import axios from "axios";

export const rasaChatInstance = axios.create({
  baseURL: "http://localhost:5005/webhooks/rest/webhook",
});

export const rasaAdminInstance = axios.create({
  baseURL: "http://192.168.0.240:9090",
});

// test
// export const rasaAdminTestInstance = axios.create({
//   baseURL: "",
// });
