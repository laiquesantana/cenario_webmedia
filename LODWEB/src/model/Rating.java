package model;

import tagging.Document;
import tagging.User2222;

public class Rating {

	int id;
	Document document;
	User2222 user;
	int rating;

	public Rating(Document document, User2222 user, int rating) {
		this.document = document;
		this.user = user;
		this.rating = rating;
	}
	
	public Rating(Document document, User2222 user) {
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

	public User2222 getUser() {
		return user;
	}

	public void setUser(User2222 user) {
		this.user = user;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}