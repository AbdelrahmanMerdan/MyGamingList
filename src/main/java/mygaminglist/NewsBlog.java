package mygaminglist;

public class NewsBlog {
	
	String title;
	String url;
	String content;
	String author;
	String website;
	String date;
	
	public NewsBlog() {
		
	}
	
	public NewsBlog(String tit, String aut, String ur, String con, String web, String dat) {
		
		title = tit;
		url = ur;
		content = con;
		author = aut;
		website = web;
		date = dat;
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "NewsBlog [title=" + title + ", url=" + url + ", content=" + content + ", author=" + author
				+ ", website=" + website + ", date=" + date + "]";
	}	
}

