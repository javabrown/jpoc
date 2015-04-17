package com.jbrown.json.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.LocalDateTime;

@JsonDeserialize(using = ArrayMapDeserializer.class)
public class DeserializorDemo {

	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();

		om.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
				true);

		
		Map<LocalDateTime, Object> map = new TreeMap<LocalDateTime, Object>();

		map.put(new LocalDateTime(), Integer.valueOf(0));

		String json = om.writeValueAsString(map);

		System.out.println(json);
		
		TypeReference<TreeMap<LocalDateTime, Object>> typeRef = new TypeReference<TreeMap<LocalDateTime, Object>>() {
		};

		Map<LocalDateTime, Object> map2 = om.readValue(json, typeRef);
	}
}


class MapDeserializer extends JsonDeserializer<Map<LocalDateTime, Object>> {

    @Override
    public Map<LocalDateTime, Object> deserialize(JsonParser jp, DeserializationContext context)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        if (jp.getCurrentToken().equals(JsonToken.START_OBJECT)) {
            return mapper.readValue(jp, new TypeReference<HashMap<LocalDateTime, Object>>() {
            });
        } else {
       
            mapper.readTree(jp);
            return new HashMap<LocalDateTime, Object>();
        }
    }
}