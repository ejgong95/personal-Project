package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class MemberDAO { // 데이터 베이스 연결
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:kosea";
	String user = "kosea";
	String password = "kosea2019a";

	private Connection con;
	private Statement stmt;
	private ResultSet rs;

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

	public ArrayList<MemberVo> list_3() {
		ArrayList<MemberVo> list_3 = new ArrayList<MemberVo>();

		try {
			connDB();

			String query = "SELECT * FROM LAFTEL_CONTENTS "; //1. 장르전체출력

			rs = stmt.executeQuery(query);
			rs.first();
			System.out.println("rs.getRow() : " + rs.getRow());
			
			//if checkbox 값
			
			
			if (rs.getRow() == 0) {
				System.out.println("0 row selected.....");
			} else {
				System.out.println(rs.getRow() + " row selected...");
				rs.previous();

				while (rs.next()) {

					String TITLE = rs.getString("TITLE");
					String STAR = rs.getString("STAR");
					String GENRE = rs.getString("GENRE");
					
					MemberVo data = new MemberVo(TITLE, STAR, GENRE);
					list_3.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_3;
	}

	public ArrayList<MemberVo> list_4(String clicked_year) { // 연도
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
			//	rs.previous();

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

	public ArrayList<MemberVo> list_7(String TITLE, String GENRE, String L_YEAR, String STAR) {
		// TODO Auto-generated method stub
		ArrayList<MemberVo> list_7 = new ArrayList<MemberVo>();

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
			list_7.add(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list_7;
	}
}
