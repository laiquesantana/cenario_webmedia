package tagging;

public class User {

	int id;
	String first;

	public User(int id, String first) {
		super();
		this.id = id;
		this.first = first;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

}
