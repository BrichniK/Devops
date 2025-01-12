package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long numCourse;

	@Enumerated(EnumType.STRING)
	TypeCourse typeCourse;

	@Enumerated(EnumType.STRING)
	Support support;

	@Min(0)
	@Max(100)
	int level;

	@NotNull
	@DecimalMin("0.0")
	Float price;

	int timeSlot;

	@JsonIgnore
	@OneToMany(mappedBy = "course")
	Set<Registration> registrations;

	// Add a name field to store the course name
	private String name;  // Add this line

	// Constructor with name field
	public Course(String name, String basic, String online, float price, int timeSlot) {
		this.name = name;  // Initialize the name property
		// Other initializations can go here
	}

	// Getters and setters for the 'name' property
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
