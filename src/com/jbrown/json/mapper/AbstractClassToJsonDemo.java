package com.jbrown.json.mapper;

import org.codehaus.jackson.annotate.JsonTypeInfo;

public class AbstractClassToJsonDemo {
	public static void main(String[] args){
		Employee rk = new ITEmployee("Raja Khan", "White Plains", "Rail Europe");
		Employee ck = new ITEmployee("Shagufta Yasmin", "Edison", "P&G Products");
		 
		JsonConverter converter = new JsonConverter();
		String json = converter.toJson(rk);
		
		System.out.println("json = " + json);
		
		Employee emp = converter.fromJson(json, Employee.class);
		System.out.println(emp);
	}
}


//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
abstract class Employee{
	private String _name;
	private String _address;
	
	public Employee(){
		_name = "";
		_address = "";
	}
	
	public Employee(String name, String address){
		_name = name;
		_address = address;
	}

	@Override
	public String toString() {
		return "Employee [_name=" + _name + ", _address=" + _address + "]";
	}
	
}

class ITEmployee extends Employee {
	private String _project;
	
	public ITEmployee(){
		super();
		_project = "";
	}
	
	public ITEmployee(String name, String address, String project){
		super(name, address);
		_project = project;
	}

	@Override
	public String toString() {
		return "ITEmployee [_project=" + _project + ", toString()="
				+ super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
}

class SalesEmployee extends Employee{
	private String _brand;
	
	public SalesEmployee(){
		super();
		_brand = "";
	}
	
	public SalesEmployee(String name, String address, String brand){
		super(name, address);
		_brand = brand;
	}

	@Override
	public String toString() {
		return "SalesEmployee [_brand=" + _brand + ", toString()="
				+ super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
}