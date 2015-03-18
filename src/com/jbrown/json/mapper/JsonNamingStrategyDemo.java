package com.jbrown.json.mapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.MapperConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.map.introspect.AnnotatedField;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.map.introspect.AnnotatedParameter;

import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.map.DeserializationConfig.Feature;

import org.apache.commons.io.IOUtils;

/**
 * Demo for JSON Naming Strategy with compress/decompress JSON Serialization.
 * @author rkhan
 *
 */
public class JsonNamingStrategyDemo {
	static ObjectMapper _mapper = new ObjectMapper();

	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		initialize();
		Employee emp = new Employee("Raja", "Khan", new Department("IT"));
		byte[] json = toJson(emp);
		byte[] compressJson = compress(json);
		byte[] decompressJson = decompress(compressJson);

		System.out.println("JSON=" + json);
		System.out.println("COMPRESS-JSON=" + compressJson);
		System.out.println("DECOMPRESS-JSON=" + decompressJson);

		Employee emp1 = fromJson(decompressJson, Employee.class);

		if (emp1 != null) {
			System.out.println("deserial value = " + emp1);
		} else {
			System.out.println("NULL found");
		}

	}

	// private static byte[] compress(byte[] bytes) {
	// byte[] input = "compression string".getBytes();
	//
	// Deflater compressor = new Deflater();
	// compressor.setLevel(Deflater.DEFAULT_COMPRESSION);
	//
	// compressor.setInput(input);
	// compressor.finish();
	//
	// ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
	//
	// byte[] buf = new byte[1024];
	// while (!compressor.finished()) {
	// int count = compressor.deflate(buf);
	// bos.write(buf, 0, count);
	// }
	// try {
	// bos.close();
	// } catch (IOException e) {
	// }
	//
	// byte[] compressedData = bos.toByteArray();
	//
	// return compressedData;
	// }

	public static byte[] compress(byte[] bytes) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			GZIPOutputStream gzipOutputStream = new GZIPOutputStream(
					byteArrayOutputStream);
			gzipOutputStream.write(bytes);
			gzipOutputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.printf("Compression ratio %f\n",
				(1.0f * bytes.length / byteArrayOutputStream.size()));
		return byteArrayOutputStream.toByteArray();
	}

	public static byte[] decompress(byte[] compressedBytes) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(
					compressedBytes)), out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}

	static void initialize() {
		_mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		_mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		_mapper.configure(Feature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		_mapper.configure(Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		_mapper.configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		_mapper.configure(Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		_mapper.enableDefaultTyping();
		_mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL,
				As.WRAPPER_OBJECT);

		_mapper.configure(Feature.UNWRAP_ROOT_VALUE, false);

		_mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

		_mapper.setVisibilityChecker(_mapper.getSerializationConfig()
				.getDefaultVisibilityChecker()
				.withFieldVisibility(Visibility.ANY)
				.withGetterVisibility(Visibility.NONE)
				.withSetterVisibility(Visibility.NONE)
				.withCreatorVisibility(Visibility.NONE)
				.withIsGetterVisibility(Visibility.NONE));
	}

	static byte[] toJson(Object object) throws JsonGenerationException,
			JsonMappingException, IOException {
		if (object instanceof XNamingStrategyI) {
			DefaultJsonWriter jsonStrategy = new DefaultJsonWriter(
					(XNamingStrategyI) object);
			XNamingStrategyI xStrategy = jsonStrategy.getNamingStrategy();

			if (xStrategy != null && xStrategy.getFieldNamingStrategy() != null) {
				_mapper.setPropertyNamingStrategy(jsonStrategy);
			}
		}

		return _mapper.writeValueAsBytes(object);// _mapper.writeValueAsString(object);
	}

	static <T extends Object> T fromJson(byte[] jsonBytes, Class klass)
			throws InstantiationException, IllegalAccessException,
			JsonParseException, IOException {
		Object object = klass.newInstance();

		if (object instanceof XNamingStrategyI) {
			DefaultJsonWriter jsonStrategy = new DefaultJsonWriter(
					(XNamingStrategyI) object);
			XNamingStrategyI xStrategy = jsonStrategy.getNamingStrategy();

			if (xStrategy != null && xStrategy.getFieldNamingStrategy() != null) {
				_mapper.setPropertyNamingStrategy(jsonStrategy);
			}
		}

		return (T) _mapper.readValue(jsonBytes, klass);
	}
}

class DefaultJsonWriter extends BrownAbstractJsonWriter {
	private XNamingStrategyI _xNamingStrategyI;

	public DefaultJsonWriter(XNamingStrategyI xNamingStrategyI) {
		super();
		_xNamingStrategyI = xNamingStrategyI;
	}

	@Override
	XNamingStrategyI getNamingStrategy() {
		return _xNamingStrategyI;
	}
}

abstract class BrownAbstractJsonWriter extends PropertyNamingStrategy {
	@Override
	public String nameForConstructorParameter(MapperConfig<?> config,
			AnnotatedParameter ctorParam, String defaultName) {
		return this.convert(defaultName);
	}

	@Override
	public String nameForField(MapperConfig<?> config, AnnotatedField field,
			String defaultName) {
		return this.convert(defaultName);
	}

	@Override
	public String nameForGetterMethod(MapperConfig<?> config,
			AnnotatedMethod method, String defaultName) {
		return this.convert(defaultName);
	}

	@Override
	public String nameForSetterMethod(MapperConfig<?> config,
			AnnotatedMethod method, String defaultName) {
		return this.convert(defaultName);
	}

	private String convert(String defaultName) {
		String newName = this.getNamingStrategy().getFieldNamingStrategy()
				.get(defaultName);

		if (newName != null && newName.length() > 0) {
			return newName;
		}

		return defaultName;
	}

	abstract XNamingStrategyI getNamingStrategy();
}

interface XNamingStrategyI {
	Map<String, String> getFieldNamingStrategy();
}

class Employee implements XNamingStrategyI {
	private String _firstName;
	private String _lastName;
	private Department _dept;

	public Employee() {
		_firstName = null;
		_lastName = null;
		_dept = null;
	}

	public Employee(String firstName, String lastName, Department dept) {
		_firstName = firstName;
		_lastName = lastName;
		_dept = dept;
	}

	@Override
	public Map<String, String> getFieldNamingStrategy() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("_firstName", "fn");
		map.put("_lastName", "ln");
		return map;
	}

	@Override
	public String toString() {
		return "Employee [_firstName=" + _firstName + ", _lastName="
				+ _lastName + ", _dept=" + _dept + "]";
	}

	public boolean isTest() {
		return true;
	}
}

class Department {
	public String _deptName;

	public Department() {
		_deptName = null;
	}

	public Department(String deptName) {
		_deptName = deptName;
	}

	@Override
	public String toString() {
		return "Department [_deptName=" + _deptName + "]";
	}
}