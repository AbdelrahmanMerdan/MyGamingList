package src;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Game {

	//Instance variables
	private int id;
	private String name, shortDesc, desc, cover, sysRequire, metaScore, metaURL;

	public Game(@JsonProperty("_id") int id, @JsonProperty("name") String name, @JsonProperty("short_description") String shortDesc, @JsonProperty("description") String desc, @JsonProperty("cover_art") String cover, @JsonProperty("pc_requirements") String sysRequire, @JsonProperty("meta_score") String metaScore, @JsonProperty("meta_link") String metaURL) {
		this.id = id;
		this.name = name;
		this.shortDesc = shortDesc;
		this.desc = desc;
		this.cover = cover;
		this.sysRequire = sysRequire;
		this.metaScore = metaScore;
		this.metaURL = metaURL;
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
