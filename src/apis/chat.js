import http from "./http";

export async function chat(sender, message) {
  return http.rasaChatInstance.post("", {
    sender,
    message,
  });
}
