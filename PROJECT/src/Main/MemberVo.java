package Main;
public class MemberVo {
	private String L_NAME;
	private String L_ID;
	private String L_PW;
	private String GENRE;
	
	String TITLE;
	String STAR;
	String L_YEAR;
	
	
	public MemberVo() {
	
	}
	
	public MemberVo(String L_NAME, String L_ID, String L_PW) {
		this.L_NAME = L_NAME;
		this.L_ID = L_ID;
		this.L_PW = L_PW;
	}
	
	public String getL_NAME() {
		return L_NAME;
	}
	
	public String getL_ID() {
		return L_ID;
	}
	
	public String getL_PW() {
		return L_PW;
	}
	
	
	
	public MemberVo(String TITLE, String STAR) {
		this.TITLE = TITLE;
		this.STAR = STAR;
	}

	public MemberVo(String L_ID, String L_PW, String L_NAME, String GENRE) {
		this.L_ID = L_ID;
		this.L_PW = L_PW;
		this.L_NAME = L_NAME;
		this.GENRE = GENRE;
	}
/*
	public MemberVo(String TITLE, String GENRE, String L_YEAR, String STAR) {
		// TODO Auto-generated constructor stub
		this.TITLE = TITLE;
		this.GENRE = GENRE;
		this.L_YEAR = L_YEAR;
		this.STAR = STAR;
	}
	
	*/
	
	public String getTITLE() {
		return TITLE;
	}

	public String getSTAR() {
		return STAR;
	}

	public String getGENRE() {
		// TODO Auto-generated method stub
		return GENRE;
	}
	
	
	
	
	
}
