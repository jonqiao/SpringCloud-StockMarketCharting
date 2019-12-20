package com.fsd.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JSONUtil {

  public static final ObjectMapper om = new ObjectMapper();
  //  private static Logger log = LoggerFactory.getLogger(JSONUtil.class);

  public static <T> T deserialize(String content, Class<T> type) {
    try {
      return JSONUtil.om.readValue(content, type);
    } catch (IOException e) {
      log.error("Failed to deserialize " + type + " from: " + content);
      return null;
    }
  }

  public static <T> T deserialize(String content, String name, Class<T> type) {
    try {
      return JSONUtil.om.readValue(JSONUtil.getByFullName(content, name), type);
    } catch (IOException e) {
      log.error("Failed to deserialize " + type + " with name: " + name);
      return null;
    }
  }

  public static String getByFullName(String content, String fullName) {
    String[] t = fullName.split("\\.");
    try {
      JsonNode j = JSONUtil.om.readTree(content);
      for (String s : t) {
        j = j.path(s);
      }
      return j.asText();
    } catch (IOException e) {
      log.error("Failed to read value with name: " + fullName);
      return "";
    }
  }

  public static JsonNode toJsonNode(Object o) {
    try {
      return JSONUtil.om.readTree(JSONUtil.om.writeValueAsBytes(o));
    } catch (IOException e) {
      log.warn("Failed to parse " + o.toString() + " to JsonNode.");
      e.printStackTrace();
      return JsonNodeFactory.instance.nullNode();
    }
  }

  public static JsonNode toJsonNode(String s) {
    try {
      return JSONUtil.om.readTree(s);
    } catch (IOException e) {
      log.warn("Failed to parse " + s + " to JsonNode.");
      e.printStackTrace();
      return JsonNodeFactory.instance.nullNode();
    }
  }

  public static String toString(Object o) {
    try {
      return JSONUtil.om.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      log.warn("Failed to write " + o + " as String.");
      e.printStackTrace();
      return "";
    }
  }

}
