package tagging;

public class Tagging {

	int id;
	Document document;
	User user;
	Tag tag;

	public Tagging(Document document, User user, Tag tag) {
		this.document = document;
		this.user = user;
		this.tag = tag;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
