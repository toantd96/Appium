package base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.serenitybdd.rest.SerenityRest;
import utils.RSAUtil;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class BaseAPI {
  public static RequestSpecification requestSpecification(
      String url, HashMap<String, String> header) {
    return SerenityRest.given().baseUri(url).headers(header).when();
  }

  public static Response postAPI(String baseURL, Object data, HashMap<String, String> header) {
    return requestSpecification(baseURL, header)
        .contentType("text/xml")
        .body(data)
        .log()
        .all()
        .post()
        .prettyPeek();
  }

  public static Response postAPI(String baseURL, Object data) {
    return SerenityRest.given()
        .baseUri(baseURL)
        .contentType(ContentType.JSON)
        .when()
        .body(data)
        .log()
        .all()
        .post()
        .prettyPeek();
  }

  public static JsonNode readJson(String json) {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode;
    try {
      jsonNode = objectMapper.readTree(json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return jsonNode;
  }

  public static JsonNode readJson(File json) {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode;
    try {
      jsonNode = objectMapper.readTree(json);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return jsonNode;
  }

  public static ObjectNode readFromJsonFilePath(String jsonFilePath) {
    return (ObjectNode) readJson(new File(jsonFilePath));
  }
  public static List<ObjectNode> getListObjectNodeFrom(ObjectNode objNode,String withKeyArray) {
    ArrayNode arrayNode = (ArrayNode)objNode.get(withKeyArray);
    int size = arrayNode.size();
    List<ObjectNode> rs = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      ObjectNode item = (ObjectNode) arrayNode.get(i);
      rs.add(item);
    }
    return rs;
  }

  public static HashMap<String, Object> readParam(String json) {
    ObjectMapper mapper = new ObjectMapper();
    HashMap<String, Object> map;
    try {
      map = mapper.readValue(json, new TypeReference<>() {});
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return map;
  }

  public String convertToBase64(String value) {
    String base64PublicKey =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz1zqQHtHvKczHh58ePiRNgOyiHEx6lZDPlvwBTaHmkNlQyyJ06SIlMU1pmGKxILjT7n06nxG7LlFVUN5MkW/jwF39/+drkHM5B0kh+hPQygFjRq81yxvLwolt+Vq7h+CTU0Z1wkFABcTeQQldZkJlTpyx0c3+jq0o47wIFjq5fwIDAQAB";
    RSAUtil rsa = new RSAUtil();
    String encrypted;
    try {
      encrypted = Base64.getEncoder().encodeToString(rsa.encrypt(value, base64PublicKey));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return encrypted;
  }

  public String convertFileToBase64(String filePath){
    try {
      byte[] fileBytes = Files.readAllBytes(Path.of(filePath));
      return Base64.getEncoder().encodeToString(fileBytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
