package Main;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class MemberDAO { // 데이터 베이스 연결
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:kosea";
	String user = "kosea";
	String password = "kosea2019a";
	JCheckBox[] checkbox = new JCheckBox[12];
	String[] genre = { "판타지", "액션", "로맨스", "개그", "일상", "모험", "순정", "아이돌", "스포츠", "SF", "스릴러", "추리" };
	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	
	// 회원 로그인 정보확인-----------------------------------------------------------------------
	public ArrayList<MemberVo> list(String txtID) {
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();

		try {
			connDB();

			String query = "SELECT * FROM LAFTEL_MEMBERS ";
			if (txtID != null) {
				query += "where L_ID = '" + txtID + "'";
			}
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected.....");
			} else {
				System.out.println(rs.getRow() + " row selected...");
				rs.previous();

				while (rs.next()) {

					String L_NAME = rs.getString("L_NAME");
					String L_ID = rs.getString("L_ID");
					String L_PW = rs.getString("L_PW");

					MemberVo data = new MemberVo(L_NAME, L_ID, L_PW);
					list.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 좋아하는 작품 목록------------------------------------------------------------------------
	public ArrayList<MemberVo> list_2(String FavoriteGenre) {
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();

		try {
			connDB();

			String query = "SELECT *\r\n" + "FROM LAFTEL_MEMBERS, LAFTEL_CONTENTS\r\n"
					+ "WHERE LAFTEL_MEMBERS.FAVORITE_GENRE = LAFTEL_CONTENTS.GENRE AND LAFTEL_MEMBERS.L_ID = " + "'"
					+ FavoriteGenre + "'" + " ORDER BY STAR desc";

			rs = stmt.executeQuery(query);
			rs.first();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected.....");
			} else {
				System.out.println(rs.getRow() + " row selected...");
				rs.previous();

				while (rs.next()) {

					String TITLE = rs.getString("TITLE");
					String STAR = rs.getString("STAR");

					MemberVo data = new MemberVo(TITLE, STAR);
					list.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 장르별 작품 조회------------------------------------------------------------------------
	public ArrayList<MemberVo> list_3() {

		ArrayList<MemberVo> list_3 = new ArrayList<MemberVo>();

		try {
			connDB();

			String A = "";

			if (Window.checkbox[0].isSelected()) {
				A += " OR GENRE = '" + Window.checkbox[0].getText() + "'";

			}
			if (Window.checkbox[1].isSelected()) {
				A += " OR GENRE = '" + Window.checkbox[1].getText() + "'";

			}

			String query = "SELECT * FROM LAFTEL_CONTENTS WHERE GENRE = ''" + A + "\r\n" + "ORDER BY GENRE";

			System.out.println(query);
			rs = stmt.executeQuery(query);
			rs.first();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected.....");
			} else {
				System.out.println(rs.getRow() + " row selected...");
				// rs.previous();

				while (rs.next()) {

					String TITLE = rs.getString("TITLE");
					String STAR = rs.getString("STAR");

					MemberVo data = new MemberVo(TITLE, STAR);
					list_3.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_3;
	}

	// 연도별 작품 조회----------------------------------------------------------------------
	public ArrayList<MemberVo> list_4(String clicked_year) { 
		ArrayList<MemberVo> list_4 = new ArrayList<MemberVo>();

		try {
			connDB();

			String query = "SELECT * FROM LAFTEL_CONTENTS WHERE L_YEAR LIKE '" + clicked_year + "%'";

			rs = stmt.executeQuery(query);
//			rs.first();
			System.out.println("rs.getRow() : " + rs.getRow());

//			if (rs.getRow() == 0) {
//				System.out.println("0 row selected.....");
//			} else {
//				System.out.println(rs.getRow() + " row selected...");
			// rs.previous();

			while (rs.next()) {

				String TITLE = rs.getString("TITLE");
				String STAR = rs.getString("STAR");

				MemberVo data = new MemberVo(TITLE, STAR);
				list_4.add(data);
			}
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_4;
	}

	// 분기별 작품 조회------------------------------------------------------------------------
	public ArrayList<MemberVo> list_5(String clicked_year, String clicked_Section) {
		ArrayList<MemberVo> list_5 = new ArrayList<MemberVo>();

		try {
			connDB();

			String query = "SELECT * FROM LAFTEL_CONTENTS WHERE L_YEAR LIKE '" + clicked_year + " " + clicked_Section
					+ "%'";

			rs = stmt.executeQuery(query);
			rs.first();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected.....");
			} else {
				System.out.println(rs.getRow() + " row selected...");
				// rs.previous();

				while (rs.next()) {

					String TITLE = rs.getString("TITLE");
					// String L_YEAR = rs.getString("L_YEAR");
					String STAR = rs.getString("STAR");

					MemberVo data = new MemberVo(TITLE, STAR);
					list_5.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_5;
	}
	

	// 회원 가입(Register)------------------------------------------------------------------------
	public ArrayList<MemberVo> list_6(String L_ID, String L_PW, String L_NAME, String FAVORITE_GENRE) {
		
		ArrayList<MemberVo> list_6 = new ArrayList<MemberVo>();

		try {
			connDB();

			String query = "insert into LAFTEL_MEMBERS (L_ID, L_PW, L_NAME, FAVORITE_GENRE) values (?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(query);

			System.out.println("SQL : " + query);

			pstmt.setString(1, L_ID);
			pstmt.setString(2, L_PW);
			pstmt.setString(3, L_NAME);
			pstmt.setString(4, FAVORITE_GENRE);

			rs = pstmt.executeQuery();

			MemberVo data = new MemberVo(L_ID, L_PW, L_NAME, FAVORITE_GENRE);
			list_6.add(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_6;
	}
	
	// 관리자 로그인 정보 확인---------------------------------------------------------
	public ArrayList<MemberVo> list_7(String M_id_tf) {
		ArrayList<MemberVo> list_7 = new ArrayList<MemberVo>();

		try {
			connDB();

			String query = "SELECT * FROM LAFTEL_MANAGER ";
			if (M_id_tf != null) {
				query += "where MANAGER_ID = '" + M_id_tf + "'";
			}
			System.out.println("SQL : " + query);

			rs = stmt.executeQuery(query);
			rs.last();
			System.out.println("rs.getRow() : " + rs.getRow());

			if (rs.getRow() == 0) {
				System.out.println("0 row selected.....");
			} else {
				System.out.println(rs.getRow() + " row selected...");
				rs.previous();

				while (rs.next()) {

					String MANAGER_ID = rs.getString("MANAGER_ID");
					String MANAGER_PW = rs.getString("MANAGER_PW");
					String MANAGER_NAME = rs.getString("MANAGER_NAME");

					MemberVo data = new MemberVo(MANAGER_ID, MANAGER_PW, MANAGER_NAME);
					list_7.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_7;
	}
	
	

	// 관리자 로그인 후 작품 레코드 삽입--------------------------------------------------
	public ArrayList<MemberVo> list_8(String TITLE, String GENRE, String L_YEAR, String STAR) {
		// TODO Auto-generated method stub
		ArrayList<MemberVo> list_8 = new ArrayList<MemberVo>();

		try {
			connDB();

			String query = "INSERT INTO LAFTEL_CONTENTS (TITLE, GENRE, L_YEAR, STAR) VALUES (?, ?, ?, ?)";

			PreparedStatement pstmt = con.prepareStatement(query);

			System.out.println("SQL : " + query);

			pstmt.setString(1, TITLE);
			pstmt.setString(2, GENRE);
			pstmt.setString(3, L_YEAR);
			pstmt.setString(4, STAR);

			rs = pstmt.executeQuery();

			MemberVo data = new MemberVo(TITLE, GENRE, L_YEAR, STAR);
			list_8.add(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_8;
	}

	// DB 연결 확인------------------------------------------------------------------------
	public void connDB() {
		try {
			Class.forName(driver);
			System.out.println("jdbc driver loading success.");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("oracle connection success.");
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			System.out.println("statement create success.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
