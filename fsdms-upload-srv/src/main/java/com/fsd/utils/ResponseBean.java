package com.fsd.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.LinkedHashMap;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean implements Serializable {

  private static final long serialVersionUID = -2818219614368513135L;

  private int status;
  private String message;
  private LinkedHashMap<String, JsonNode> data = new LinkedHashMap<String, JsonNode>();

  public ResponseBean(int status, String msg) {
    this.status = status;
    this.message = msg;
  }

  public static final ResponseBean status(int status) {
    return new ResponseBean(status, "");
  }

  public ResponseBean data(String name, Object value) throws Exception {
    this.data.put(name, JSONUtil.toJsonNode(value));
    return this;
  }

  public ResponseBean data(Object value) throws Exception {
    this.data.put("result", JSONUtil.toJsonNode(value));
    return this;
  }

  public ResponseBean error(Object value) throws Exception {
    this.data.put("error", JSONUtil.toJsonNode(value));
    return this;
  }

}
