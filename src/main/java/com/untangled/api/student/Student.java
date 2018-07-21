package com.untangled.api.student;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Student {
	
	@SuppressWarnings("deprecation")
	@GraphId private Long id;
	
	private String firstName;
	private String lastName;
	
	private Student() {}

	public Student(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@Relationship(type = "classmate", direction = Relationship.UNDIRECTED)
	public Set<Student> classmates;
	
	
	public void classMateWith(Student s) {
		if(classmates == null){
			classmates = new HashSet<Student>(); 
		}
		classmates.add(s);
	}
 
	public String toString() {
		return this.firstName + " " + this.lastName + "'s classmates => "
				+ Optional.ofNullable(this.classmates).orElse(Collections.emptySet()).stream().map(
								student -> student.getFirstName()).collect(Collectors.toList());
	}
	
	public String getFirstName(){
		return this.firstName;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getLastName(){
		return this.lastName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}

}
