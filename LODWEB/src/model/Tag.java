package model;

public class Tag {
	private int id;
	private String name;

	public Tag() { }
	
	public Tag(String name) {
		this.name = name;
	}
	
	public Tag(int id, String name) {
		this.name = name;
		this.id = id;
	}
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
