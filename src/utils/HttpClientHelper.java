package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientHelper {

  public static HttpResponse<String> putRequest(String baseUrl, Map<String, String> headers,
      String jsonBody) {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest.Builder builder = HttpRequest.newBuilder()
        .uri(URI.create(baseUrl));

    for (Entry<String, String> item : headers.entrySet()) {
      builder.header(item.getKey(), item.getValue());
    }
    HttpRequest request = builder.PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();

    try {
      HttpResponse<String> response = httpClient.send(request,
          HttpResponse.BodyHandlers.ofString());
      return response;
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static HttpResponse<String> getRequest(String baseUrl, Map<String, String> headers) {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest.Builder builder = HttpRequest.newBuilder()
        .uri(URI.create(baseUrl));

    for (Entry<String, String> item : headers.entrySet()) {
      builder.header(item.getKey(), item.getValue());
    }
    HttpRequest request = builder.GET().build();

    try {
      HttpResponse<String> response = httpClient.send(request,
          HttpResponse.BodyHandlers.ofString());
      return response;
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

}
