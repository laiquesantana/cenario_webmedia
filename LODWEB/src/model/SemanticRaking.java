package model;

public class SemanticRaking {

	private int uri1;
	private int uri2;
	private double score;
	private int userid;
	private String type;

	public SemanticRaking(int uri1, int uri2, String type, double score, int userid) {
		super();
		this.uri1 = uri1;
		this.uri2 = uri2;
		this.score = score;
		this.userid = userid;
		this.type = type;

	}

	public int getUri1() {
		return uri1;
	}

	public void setUri1(int uri1) {
		this.uri1 = uri1;
	}

	public int getUri2() {
		return uri2;
	}

	public void setUri2(int uri2) {
		this.uri2 = uri2;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
