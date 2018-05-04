package model;

import tagging.Usersss;

public class Rating {

	int id;
	Document document;
	Usersss user;
	int rating;

	public Rating(Document document, Usersss user, int rating) {
		this.document = document;
		this.user = user;
		this.rating = rating;
	}
	
	public Rating(Document document, Usersss user) {
		this.document = document;
		this.user = user;
	}

	public int getId() {
		return id;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}