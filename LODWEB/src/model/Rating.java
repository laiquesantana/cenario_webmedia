package model;

import tagging.Document;
import tagging.User;

public class Rating {

	int id;
	Document document;
	User user;
	int rating;

	public Rating(Document document, User user, int rating) {
		this.document = document;
		this.user = user;
		this.rating = rating;
	}
	
	public Rating(Document document, User user) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}