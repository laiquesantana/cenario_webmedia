package model;

import tagging.Usersss;

public class Tagging {

	int id;
	Document document;
	Usersss user;
	Tag tag;

	public Tagging(Document document, Usersss user, Tag tag) {
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

	public Usersss getUser() {
		return user;
	}

	public void setUser(Usersss user) {
		this.user = user;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
