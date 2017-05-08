package br.com.tcc.VO;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.tcc.Interfaces.IDominioPersistente;

@XmlRootElement(name = "usuario_instagram")
public class UsuarioInstagramVO implements IDominioPersistente<String> {

	private static final long serialVersionUID = 4374802465050907192L;
	private String id;
	private String username;
	private String bio;
	private String profile_picture;
	private String full_name;
	private int media;
	private int followed_by;
	private int follows;
	
	public UsuarioInstagramVO() {
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public int getMedia() {
		return media;
	}

	public void setMedia(int media) {
		this.media = media;
	}

	public int getFollowed_by() {
		return followed_by;
	}

	public void setFollowed_by(int followed_by) {
		this.followed_by = followed_by;
	}

	public int getFollows() {
		return follows;
	}

	public void setFollows(int follows) {
		this.follows = follows;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
