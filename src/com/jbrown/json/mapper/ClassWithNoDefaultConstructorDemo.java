package com.jbrown.json.mapper;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;


public class ClassWithNoDefaultConstructorDemo {
	public static void main(String[] args) {
		ClassWithNoDefaultConstructor cwc = new ClassWithNoDefaultConstructor("test-key",
				"test-value");

		JsonConverter converter = new JsonConverter();
		String json = converter.toJson(cwc);

		System.out.println("json = " + json);

		ClassWithNoDefaultConstructor emp = converter.fromJson(json,
				ClassWithNoDefaultConstructor.class);
		System.out.println(emp);
	}
}

@JsonDeserialize(using = ClassWithoutConstructorDeserializor.class)
class ClassWithNoDefaultConstructor  {
	private String _key;
	private String _value;
	
	public ClassWithNoDefaultConstructor(String key, String value){
		_key = key;
		_value = value;
	}

	@Override
	public String toString() {
		return "ClassWithoutConstructor [_key=" + _key + ", _value=" + _value
				+ "]";
	}
}

class ClassWithoutConstructorDeserializor extends JsonDeserializer<ClassWithNoDefaultConstructor> {

	@Override
	public ClassWithNoDefaultConstructor deserialize(JsonParser jsonParser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
	 
		jsonParser.nextToken();
		String tmp = jsonParser.getText(); // ==> {
		
		jsonParser.nextToken();
		String key = jsonParser.getText();
		
		jsonParser.nextToken();
		String value = jsonParser.getText();
		
		jsonParser.nextToken();
		tmp = jsonParser.getText(); // ==>}

		ClassWithNoDefaultConstructor pv = new ClassWithNoDefaultConstructor(key,value);
		return pv;
	}
}