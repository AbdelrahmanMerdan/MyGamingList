package mygaminglist;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

	//Instance variables
	private int id, sum_of_all_reviews, num_of_reviews, average_of_reviews, players, peak;
	private String name, shortDesc, desc, cover, sysRequire, metaScore, metaURL;
	private List<Object> comments;

	public Game(@JsonProperty("_id") int id, @JsonProperty("name") String name, @JsonProperty("short_description") String shortDesc, @JsonProperty("description") String desc, @JsonProperty("cover_art") String cover, 
			@JsonProperty("pc_requirements") String sysRequire, @JsonProperty("meta_score") String metaScore, @JsonProperty("meta_link") String metaURL, @JsonProperty("sum_of_all_reviews") int sum_of_all_reviews, 
			@JsonProperty("num_of_reviews") int num_of_reviews, @JsonProperty("average_of_reviews") int average_of_reviews,@JsonProperty("comments")List<Object> comments, @JsonProperty("player_count") int players, @JsonProperty("twenty_four_hr_peak") int peak) {
		this.id = id;
		this.name = name;
		this.shortDesc = shortDesc;
		this.desc = desc;
		this.cover = cover;
		this.sysRequire = sysRequire;
		this.metaScore = metaScore;
		this.metaURL = metaURL;
		this.sum_of_all_reviews = sum_of_all_reviews;
		this.num_of_reviews = num_of_reviews;
		this.average_of_reviews = average_of_reviews;
		this.comments = comments;
		this.players = players;
		this.peak = peak;
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
	
	public int getSumOfAllReviews() {
		return sum_of_all_reviews;
	}

	public void setSumOfAllReviews(int SumOfReviews) {
		this.sum_of_all_reviews = SumOfReviews;
	}

	public int getNumOfReviews() {
		return num_of_reviews;
	}

	public void setNumOfReviews(int numReviews) {
		this.num_of_reviews = numReviews;
	}
	
	public List<Object> getComment() {
		return this.comments;
	}
	
	public void addComment(List<Object> comment) {
		this.comments.add(comment);
	}
	
	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
	}

	public int getPeak() {
		return peak;
	}

	public void setPeak(int peak) {
		this.peak = peak;
	}
	
	public int getAverage_of_reviews() {
		return average_of_reviews;
	}

	public void setAverage_of_reviews() {
		
		if(num_of_reviews > 0) {
			this.average_of_reviews = sum_of_all_reviews/num_of_reviews;
		}
		else {
			this.average_of_reviews = 0;
		}
		
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
		return "Game [id=" + id + ", sum_of_all_reviews=" + sum_of_all_reviews + ", num_of_reviews=" + num_of_reviews
				+ ", name=" + name + ", shortDesc=" + shortDesc + ", desc=" + desc + ", cover=" + cover
				+ ", sysRequire=" + sysRequire + ", metaScore=" + metaScore + ", metaURL=" + metaURL + ", comments="
				+ comments + "]";
	}

	
}
