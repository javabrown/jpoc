package com.jbrown.json.mapper;

import java.io.IOException;
import java.lang.annotation.Target;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This class is for the conversation of a Java object to/from JSON between
 * different serial-version.
 *
 */
public class JsonConverter extends ObjectMapper {
 
  public JsonConverter() {
    super();
    super.setVisibility(JsonMethod.FIELD, Visibility.ANY);
    super.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    super.configure(Feature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
    //super.configure(Feature.WRAP_EXCEPTIONS, true);
    //super.configure(Feature.UNWRAP_ROOT_VALUE, true);
    //super.configure(Feature.AUTO_DETECT_FIELDS, true);
    super.configure(Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    super.configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
    super.configure(Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);

    super.enableDefaultTyping(); // defaults for defaults (see below); include as wrapper-array, non-concrete types
    super.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL,  As.WRAPPER_OBJECT); // all non-final types
     
    super.setVisibilityChecker(getSerializationConfig()
        .getDefaultVisibilityChecker().withFieldVisibility(Visibility.ANY)
        .withGetterVisibility(Visibility.NONE)
        .withSetterVisibility(Visibility.ANY)
        .withCreatorVisibility(Visibility.ANY));
  }

  public <T extends Object> T fromJson(String jsonString, Class<T> klass) {
    T object = null;

    try {
      object = this.readValue(jsonString, klass);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return object;
  }

  public <T extends Object> String toJson(T object) {
    String jsonString = null;
    try {
      jsonString = this.writeValueAsString(object);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return jsonString;
  }
}