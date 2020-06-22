package Main;

public class MemberVo_1 {
	private String L_NAME;
	private String L_ID;
	private String L_PW;
	
	String TITLE;
	String STAR;
	private String GENRE;
	private String L_YEAR;
	
	public MemberVo_1() {
		
	}
	
	public MemberVo_1(String TITLE, String GENRE, String L_YEAR, String STAR) {
		// TODO Auto-generated constructor stub
		this.TITLE = TITLE;
		this.GENRE = GENRE;
		this.L_YEAR = L_YEAR;
		this.STAR = STAR;
	}
}
