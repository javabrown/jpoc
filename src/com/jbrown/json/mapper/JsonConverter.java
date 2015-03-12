package com.jbrown.json.mapper;

import java.io.IOException;
 
import java.util.logging.Logger;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This class is for the conversation of a Java object to/from JSON between
 * different serial-version.
 *
 * This implementation will be helpful on reading old cached object with
 * different serial-version-id from memcached
 *
 * @author rkhan
 *
 */
public class JsonConverter extends ObjectMapper {
  static Logger _logger = Logger.getLogger(JsonConverter.class.getName());

  public JsonConverter() {
    super();
    super.setVisibility(JsonMethod.FIELD, Visibility.ANY);
    super.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public <T extends Object> T fromJson(String jsonString, Class<T> klass) {
    T object = null;

    try {
      object = this.readValue(jsonString, klass);
    } catch (IOException e) {
      _logger.info(String.format(
          "Error in BizJsonConverter.fromJson-> json:[%s] | to=%s", jsonString,
          klass.getName()));
      e.printStackTrace();
    }

    return object;
  }

  public <T extends Object> String toJson(T object) {
    String jsonString = null;
    try {
      jsonString = this.writeValueAsString(object);
    } catch (IOException e) {
      _logger.info(String.format("Error in BizJsonConverter.toJson: %s",
          e.getMessage()));
      e.printStackTrace();
    }
    return jsonString;
  }
}
