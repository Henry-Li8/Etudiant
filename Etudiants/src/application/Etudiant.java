package application;

public class Etudiant {
	private String prenom;
	private String nom;
	private String department;
	private Double age;
	
	
	public Etudiant() {
		this(null, null);
	}
	
	public Etudiant (String prenom, String nom) {
		this.prenom = prenom;
		this.nom = nom;
		this.department = "";
		this.age = 0.0;
		
	}
	
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Double getAge() {
		return age;
	}
	public void setAge(Double age) {
		this.age = age;
	}
	
}
