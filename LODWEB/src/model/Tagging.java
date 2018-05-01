package model;

import tagging.Document;
import tagging.User2222;

public class Tagging {

	int id;
	Document document;
	User2222 user;
	Tag tag;

	public Tagging(Document document, User2222 user, Tag tag) {
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

	public User2222 getUser() {
		return user;
	}

	public void setUser(User2222 user) {
		this.user = user;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
