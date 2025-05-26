package com.example.demo.entity;



	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;

	@Entity

	public class Employee {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		private String username;
		private String email;
		private String role;
		private long salary;
		private String password;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public long getSalary() {
			return salary;
		}
		public void setSalary(long salary) {
			this.salary = salary;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

		
		

		

		
		

	}


