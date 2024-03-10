package src;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

	//Instance variables
	private int id, sum_of_all_reviews, userReviews;
	private String name, shortDesc, desc, cover, sysRequire, metaScore, metaURL;
	private List<List<String>> comments;

	public Game(@JsonProperty("_id") int id, @JsonProperty("name") String name, @JsonProperty("short_description") String shortDesc, @JsonProperty("description") String desc, @JsonProperty("cover_art") String cover, 
			@JsonProperty("pc_requirements") String sysRequire, @JsonProperty("meta_score") String metaScore, @JsonProperty("meta_link") String metaURL, @JsonProperty("sum_of_all_reviews") int sum_of_all_reviews, 
			@JsonProperty("user_reviews") int userReviews, @JsonProperty("comments")List<List<String>> comments) {
		this.id = id;
		this.name = name;
		this.shortDesc = shortDesc;
		this.desc = desc;
		this.cover = cover;
		this.sysRequire = sysRequire;
		this.metaScore = metaScore;
		this.metaURL = metaURL;
		this.sum_of_all_reviews = sum_of_all_reviews;
		this.userReviews = userReviews;
		this.comments = comments;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getSysRequire() {
		return sysRequire;
	}

	public void setSysRequire(String sysRequire) {
		this.sysRequire = sysRequire;
	}

	public String getMetaScore() {
		return metaScore;
	}

	public void setMetaScore(String metaScore) {
		this.metaScore = metaScore;
	}

	public String getMetaURL() {
		return metaURL;
	}

	public void setMetaURL(String metaURL) {
		this.metaURL = metaURL;
	}
	
	public int getNumOfReviews() {
		return sum_of_all_reviews;
	}

	public void setNumOfReviews(int numOfReviews) {
		this.sum_of_all_reviews = numOfReviews;
	}

	public int getUserReviews() {
		return userReviews;
	}

	public void setUserReviews(int userReviews) {
		this.userReviews = userReviews;
	}
	
	public List<List<String>> getComment() {
		return this.comments;
	}
	
	public void addFirstComment(List<String> comment) {
		this.comments.add(0, comment);
	}

	//The two methods below only change on the index so we can combine them into 1 if needed
	public List<String> getAllReccomandationComments(){
		
		List<String> copy = new ArrayList<>();
		
		for(int i = 0; i < this.comments.size(); i++) {
			
			copy.add(this.comments.get(i).get(1));
			
		}
		
		return copy;
	}
	
	public List<String> getAllReccomandationUser(){
		
		List<String> copy = new ArrayList<>();
		
		for(int i = 0; i < this.comments.size(); i++) {
			
			copy.add(this.comments.get(i).get(0));
			
		}
		
		return copy;
	}
	

	public String getOneReccomandationComment(int index){
		
		return this.comments.get(index).get(1);
	}
	
	public String getOneReccomandationUser(int index){
		
		return this.comments.get(index).get(0);
	}
	
	public void addCommentToUserRecommandation(User user, String message, int reccomandationIndex) {
		
		//empty list
		List<String> toAdd = new ArrayList<>();
		
		//the first index will have the username and the seccond will have the message of the comment
		toAdd.add(user.getUsername());
		toAdd.add(message);
		
		//adding the comment with all the info to the recommendation
		this.comments.get(reccomandationIndex).addAll(toAdd);
		
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return " name=" + name + "\n id=" + id + "\n shortDesc=" + shortDesc + "\n desc=" + desc + "\n cover=" + cover
				+ "\n sysRequire=" + sysRequire + "\n metaScore=" + metaScore + "\n metaURL=" + metaURL;
	}
	
	
}
