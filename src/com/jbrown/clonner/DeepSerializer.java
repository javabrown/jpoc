package com.jbrown.clonner;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.ImmutableFieldKeySorter;
import com.thoughtworks.xstream.converters.reflection.Sun14ReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;


public class DeepSerializer {
	public static void main(String[] args) throws JSONException{
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
	
	static String __xml = "<com.jbrown.clonner.Outer><__id>1</__id><__name>first-object</__name><__innerValue><__id>2</__id><__name>Inner: first-object-2</__name></__innerValue></com.jbrown.clonner.Outer>";
}



class BrownXStream extends XStream {
	
	public BrownXStream(){
		super(new Sun14ReflectionProvider(
				  new FieldDictionary(new ImmutableFieldKeySorter())),
				  new DomDriver("utf-8"));
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

class Outer {
    private int _id;
    private String _name; 
    private Inner _innerValue;
    private String _extra;
    
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