package com.jbrown.clonner;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.jbrown.json.mapper.JsonConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.ImmutableFieldKeySorter;
import com.thoughtworks.xstream.converters.reflection.Sun14ReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;


public class DeepSerializer {
	static Outer _outer = new Outer(1, "first-object", "Extra");
	
	public static void main(String[] args) throws JSONException {
		doJsonTransformTest();
	}
	
	public static void doJsonTransformTest() throws JSONException {
		JsonConverter mapper = new JsonConverter();
		  
		System.out.println(mapper.toJson(_outer));
		 
		Outer user = mapper.fromJson(__json, Outer.class);
  
		System.out.println("Object converted from JSON=" + user);
	}
	
	public static void doXstreamTransformTest() throws JSONException {
		BrownXStream xstream = new BrownXStream();
		
	    String xml = xstream.toXML(new Outer(1, "first-object", "Extra"));
	    
	  
	    Outer outerx = (Outer) xstream.fromXML(__xml);
	    if(outerx != null){
	    	System.out.printf("xst %s\n", outerx);
	    }
	    
	    JSONObject json = XML.toJSONObject(xml);
	    String xmlFromJson = XML.toString(json);
	    		
	    Outer outer = (Outer) xstream.fromXML(xml);
	    Outer outer1 = (Outer) xstream.fromXML(xmlFromJson);
	    
	    System.out.println(xml+"\n");
	    
	    System.out.println(json+"\n");
	    
	    if(outer != null){
	    	System.out.printf("1st %s\n", outer);
	    }
	    
	    if(outer1 != null){
	    	System.out.printf("2st %s\n", outer1);
	    }
	}
	
	static String __json = "{\"_id\":1,\"_name\":\"first-object\",\"_innerValue\":{\"_id\":2,\"_name\":\"Inner: first-object-2\",\"name\":\"Inner: first-object-2\",\"id\":2},\"_extra\":\"Extra\"}";
	static String __xml = "<com.jbrown.clonner.Outer><__id>1</__id><__name>first-object</__name><__innerValue><__id>2</__id><__name>Inner: first-object-2</__name></__innerValue></com.jbrown.clonner.Outer>";
}



class BrownXStream extends XStream {
	
	public BrownXStream(){
		super(new Sun14ReflectionProvider(
				  new FieldDictionary(new ImmutableFieldKeySorter())),
				  new DomDriver("utf-8"));
		//super(new PureJavaReflectionProvider( new FieldDictionary(new ImmutableFieldKeySorter())));
		//super( new DomDriver("utf-8") );
	}
	
	@Override
	protected MapperWrapper wrapMapper(MapperWrapper next) {
		return new MapperWrapper(next) {
			@Override
			public boolean shouldSerializeMember(Class definedIn,
					String fieldName) {
				if (definedIn == Object.class) {
					return false;
				}
				return super.shouldSerializeMember(definedIn, fieldName);
			}
		};
	}
}

@JsonIgnoreProperties(value = { "_extra" })
class Outer {
    private int _id;
    private String _name; 
    private Inner _innerValue;
    private String _extra;
    
    public Outer(){
 
    }
    
	public Outer(int id, String name, String extra) {
		super();
		_id = id;
		_name = name;
		_extra = extra;
		int innerInt = ++id;
		_innerValue = new Inner(innerInt, "Inner: "+_name+"-"+innerInt);
	}

	@Override
	public String toString() {
		return "Outer [_id=" + _id + ", _name=" + _name + ", _innerValue="
				+ _innerValue + ", _extra=" + _extra + "]";
	}
}

class Inner {
	private int _id;
	private String _name;

	public Inner() {
		this._id = 0;
		this._name = "";
	}
	
	public Inner(int id, String name) {
		this._id = id;
		this._name = name;
	}

	public int getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	@Override
	public String toString() {
		return "Inner [_id=" + _id + ", _name=" + _name + "]";
	}
}