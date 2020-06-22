package Main;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class Window implements ItemListener {
	private MemberDAO dao;
	private JFrame frame;
	private JTextField txtID, IDtf, PWtf, NAMEtf, m_titleTF, m_yearTF, m_starTF;

	private JPanel nextpanel_1, nextpanel_genre, nextpanel_year;

	private JTable tablelist, table_genreselect, table_yearSelected;
	static JScrollPane scroll, scroll_genreselect, scroll_yearSelected;
	static DefaultTableModel model, model_genreselect, model_yearSelected;
	static String[][] tabledata, tabledata_genreselect, tabledata_yearSelected;

	private JCheckBox[] checkbox = new JCheckBox[12];
	String[] genre = { "판타지", "액션", "로맨스", "개그", "일상", "모험", "순정", "아이돌", "스포츠", "SF", "스릴러", "추리" };

	String year[] = { "-------", "2020년", "2019년", "2018년", "2017년", "2016년", "2010년대", "2000년대", "1990년대",
			"1990년대 이전" };
	String Section[] = { "-------", "1분기", "2분기", "3분기", "4분기" };

	String checked;

	private Choice yearChoice, SectionChoice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		dao = new MemberDAO();
		frame = new JFrame("LAFTEL");
		frame.setSize(650, 900);
		// frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);

		LogIn();

	}

	public void LogIn() { // 로그인 화면
		JPanel logpanel = new JPanel();
		logpanel.setBounds(0, 0, 632, 853);
		// panel을 frame에 추가
		frame.getContentPane().add(logpanel);
		logpanel.setLayout(null);
		logpanel.setBackground(Color.WHITE);

		JLabel IDlabel = new JLabel(" ID : ");
		IDlabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		IDlabel.setBounds(128, 100, 52, 37);
		logpanel.add(IDlabel);

		txtID = new JTextField();
		txtID.setFont(new Font("굴림", Font.PLAIN, 17));
		txtID.setBounds(223, 103, 175, 37);
		logpanel.add(txtID);
		txtID.setColumns(10);

		JLabel Passwordlabel = new JLabel(" PASSWORD : ");
		Passwordlabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		Passwordlabel.setBounds(26, 150, 166, 39);
		logpanel.add(Passwordlabel);

		JPasswordField txtpsw = new JPasswordField();
		txtpsw.setFont(new Font("굴림", Font.PLAIN, 17));
		txtpsw.setColumns(10);
		txtpsw.setBounds(223, 152, 175, 37);
		logpanel.add(txtpsw);

		JButton logbtn = new JButton("Log In");
		logbtn.setIcon(null);
		logbtn.setFont(new Font("Arial Black", Font.PLAIN, 17));
		logbtn.setBounds(437, 100, 140, 89);
		logpanel.add(logbtn);

		// 로그인 버튼에 기능 추가
		logbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// String id = "Gong";
				// String pass = "1234";
				// txtID.setText("");
				// txtpsw.setText("");

				ArrayList<MemberVo> list = dao.list(txtID.getText());

				for (int i = 0; i < list.size(); i++) {
					MemberVo data = (MemberVo) list.get(i);

					String L_ID = data.getL_ID();
					String L_PW = data.getL_PW();

					if (txtID.getText().equals(L_ID) && txtpsw.getText().equals(L_PW)) {

						JOptionPane.showMessageDialog(null, "You have logged in successfully");
						logpanel.setVisible(false);

						nextpanel_1();

					} else {
						JOptionPane.showMessageDialog(null, "You failed to log in");

					}

					if (txtID.getText().equals("") || txtpsw.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "You failed to log in");
					}
				}
				System.out.println("Click");

			}

		});

		JLabel Ifyoulabel = new JLabel("아직 Laftel회원이 아니신가요?");
		Ifyoulabel.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		Ifyoulabel.setVerticalAlignment(SwingConstants.TOP);
		Ifyoulabel.setBounds(194, 223, 244, 37);
		logpanel.add(Ifyoulabel);

		JButton newBtn = new JButton("Register");
		newBtn.setFont(new Font("Arial Black", Font.PLAIN, 13));
		newBtn.setBounds(437, 223, 140, 37);
		logpanel.add(newBtn);

		newBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logpanel.setVisible(false);
				Register();

			}

		});

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\ejgon\\Desktop\\laftel.jpg"));
		lblNewLabel.setBounds(26, 14, 429, 93);
		logpanel.add(lblNewLabel);

		JButton btnManagement = new JButton("management");
		btnManagement.setFont(new Font("Arial Black", Font.PLAIN, 10));
		btnManagement.setBounds(437, 271, 140, 37);
		logpanel.add(btnManagement);

		JLabel managerlabel = new JLabel("관리자 로그인");
		managerlabel.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		managerlabel.setBounds(314, 272, 124, 36);
		logpanel.add(managerlabel);

		btnManagement.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				logpanel.setVisible(false);
				Manager();
			}

		});

	}

	// 회원 등록
	public void Register() {
		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setBackground(Color.WHITE);
		RegisterPanel.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(RegisterPanel);
		RegisterPanel.setLayout(null);

		JLabel IDregister = new JLabel("ID");
		IDregister.setBounds(63, 118, 62, 18);
		RegisterPanel.add(IDregister);

		IDtf = new JTextField();
		IDtf.setBounds(170, 115, 116, 24);
		RegisterPanel.add(IDtf);
		IDtf.setColumns(10);

		JLabel PWregister = new JLabel("PASSWORD");
		PWregister.setBounds(63, 187, 62, 18);
		RegisterPanel.add(PWregister);

		PWtf = new JTextField();
		PWtf.setBounds(170, 184, 116, 24);
		RegisterPanel.add(PWtf);
		PWtf.setColumns(10);

		JLabel NAMEregister = new JLabel("이름");
		NAMEregister.setBounds(63, 275, 62, 18);
		RegisterPanel.add(NAMEregister);

		JLabel GENREregister = new JLabel("좋아하는 장르");
		GENREregister.setBounds(63, 359, 62, 18);
		RegisterPanel.add(GENREregister);

		Choice GENREchoice = new Choice();
		GENREchoice.setBounds(170, 359, 116, 24);
		RegisterPanel.add(GENREchoice);

		String[] genre = { "판타지", "액션", "로맨스", "개그", "일상", "모험", "순정", "아이돌", "스포츠", "SF", "스릴러", "추리" };

		for (int i = 0; i < genre.length; i++) {
			GENREchoice.add(genre[i]);
		}

		NAMEtf = new JTextField();
		NAMEtf.setColumns(10);
		NAMEtf.setBounds(170, 272, 116, 24);
		RegisterPanel.add(NAMEtf);

		Button button = new Button("중복확인");
		button.setBounds(341, 111, 87, 25);
		RegisterPanel.add(button);

		JButton REGISTER = new JButton("JOIN US");
		REGISTER.setBounds(225, 518, 105, 27);
		RegisterPanel.add(REGISTER);

		REGISTER.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				ArrayList<MemberVo> list_6 = dao.list_6(IDtf.getText(), PWtf.getText(), NAMEtf.getText(),
						GENREchoice.getSelectedItem());
				for (int i = 0; i < list_6.size(); i++) {
					MemberVo data = (MemberVo) list_6.get(i);
					String L_ID = IDtf.getText();
					String L_PW = PWtf.getText();
					String L_NAME = NAMEtf.getText();
					String FAVORITE_GENRE = GENREchoice.getSelectedItem();
					// String GENRE = ();
					System.out.println(L_ID + " : " + L_PW + " : " + L_NAME + " : " + FAVORITE_GENRE);
				}

			}

		});

	}

	// manager 작품 등록

	public void Manager() {

		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(RegisterPanel);
		RegisterPanel.setLayout(null);

		JLabel m_title = new JLabel("작품명");
		m_title.setBounds(106, 131, 62, 18);
		RegisterPanel.add(m_title);

		m_titleTF = new JTextField();
		m_titleTF.setBounds(289, 127, 116, 24);
		RegisterPanel.add(m_titleTF);
		m_titleTF.setColumns(10);

		JLabel m_genre = new JLabel("장르");
		m_genre.setBounds(106, 201, 62, 18);
		RegisterPanel.add(m_genre);

		Choice GENREchoice = new Choice();
		GENREchoice.setBounds(289, 195, 116, 24);
		RegisterPanel.add(GENREchoice);

		String[] genre = { "판타지", "액션", "로맨스", "개그", "일상", "모험", "순정", "아이돌", "스포츠", "SF", "스릴러", "추리" };

		for (int i = 0; i < genre.length; i++) {
			GENREchoice.add(genre[i]);
		}

		JLabel m_year = new JLabel("연도");
		m_year.setBounds(106, 268, 62, 18);
		RegisterPanel.add(m_year);

		m_yearTF = new JTextField();
		m_yearTF.setColumns(10);
		m_yearTF.setBounds(287, 265, 116, 24);
		RegisterPanel.add(m_yearTF);

		JLabel m_star = new JLabel("평가(별점)");
		m_star.setBounds(106, 331, 62, 18);
		RegisterPanel.add(m_star);

		m_starTF = new JTextField();
		m_starTF.setColumns(10);
		m_starTF.setBounds(287, 328, 116, 24);
		RegisterPanel.add(m_starTF);

		JButton InsertBtn = new JButton("\uB4F1\uB85D\uD558\uAE30");
		InsertBtn.setBounds(198, 450, 105, 27);
		RegisterPanel.add(InsertBtn);

		InsertBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ArrayList<MemberVo_1> list_7 = dao.list_7(m_titleTF.getText(), GENREchoice.getSelectedItem(),
						m_yearTF.getText(), m_starTF.getText());
				for (int i = 0; i < list_7.size(); i++) {
					MemberVo_1 data = (MemberVo_1) list_7.get(i);
					String TITLE = m_titleTF.getText();
					String GENRE = GENREchoice.getSelectedItem();
					String L_YEAR = m_yearTF.getText();
					String STAR = m_starTF.getText();
					// String GENRE = ();
					System.out.println(TITLE + " : " + GENRE + " : " + L_YEAR + " : " + STAR);
				}
			}

		});

	}

	// 로그인 성공 화면
	public void nextpanel_1() {
		nextpanel_1 = new JPanel();
		nextpanel_1.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(nextpanel_1);
		nextpanel_1.setLayout(null);
		nextpanel_1.setBackground(Color.WHITE);

		JLabel welcome = new JLabel(txtID.getText() + "님 반가워요");
		welcome.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		welcome.setBounds(31, 25, 340, 55);
		nextpanel_1.add(welcome);

		tablelist();

		JButton searchothers = new JButton("다른 작품 만나보기");
		searchothers.setBounds(209, 705, 241, 110);
		nextpanel_1.add(searchothers);

		JLabel Hellolabel = new JLabel(txtID.getText() + "님이 좋아하실 만한 작품");
		Hellolabel.setFont(new Font("굴림", Font.PLAIN, 17));
		Hellolabel.setBounds(31, 89, 260, 36);
		nextpanel_1.add(Hellolabel);

		searchothers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextpanel_1.setVisible(false);
				nextpanel_2();

			}

		});

	}

	// 반가워요페이지 테이블
	private void tablelist() {

		ArrayList<MemberVo> list_2 = (ArrayList<MemberVo>) dao.list_2(txtID.getText());
		tabledata = new String[list_2.size()][2];
		for (int i = 0; i < list_2.size(); i++) {
			MemberVo data = (MemberVo) list_2.get(i);
			String TITLE = data.getTITLE();
			String STAR = data.getSTAR();

			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					tabledata[i][j] = TITLE;
				} else if (j == 1) {
					tabledata[i][j] = STAR;
				}
			}

			System.out.println(TITLE + " : " + STAR);
		}

		String column[] = { "제목", "별점" };

		model = new DefaultTableModel(tabledata, column);
		tablelist = new JTable(model);
		// tablelist.getTableHeader().setReorderingAllowed(false);
		tablelist.setBounds(14, 91, 454, 428);
		scroll = new JScrollPane(tablelist);
		scroll.setBounds(31, 137, 573, 532);
		nextpanel_1.add(scroll);

	}

	// 장르별 or 시대별 선택 화면

	public void nextpanel_2() {

		JPanel nextpanel_2 = new JPanel();
		nextpanel_2.setBackground(Color.WHITE);
		nextpanel_2.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(nextpanel_2);
		nextpanel_2.setLayout(null);

		JLabel GenreTag = new JLabel("\uC7A5\uB974\uBCC4 \uC778\uAE30\uC791 \uB9CC\uB098\uBCF4\uAE30");
		GenreTag.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		GenreTag.setBounds(206, 12, 214, 39);
		nextpanel_2.add(GenreTag);

		JLabel YearTag = new JLabel("\uC2DC\uB300\uBCC4 \uC778\uAE30\uC791 \uB9CC\uB098\uBCF4\uAE30");
		YearTag.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		YearTag.setBounds(206, 442, 214, 39);
		nextpanel_2.add(YearTag);

		JButton GenreGo = new JButton("GO!!");
		GenreGo.setFont(new Font("Arial Black", Font.PLAIN, 19));
		GenreGo.setBounds(500, 18, 105, 35);
		nextpanel_2.add(GenreGo);

		JButton YearGo = new JButton("GO!!");
		YearGo.setFont(new Font("Arial Black", Font.PLAIN, 19));
		YearGo.setBounds(500, 446, 105, 35);
		nextpanel_2.add(YearGo);

		GenreGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextpanel_2.setVisible(false);
				nextpanel_genre();

			}

		});

		YearGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextpanel_year();
				nextpanel_2.setVisible(false);
			}

		});

		JButton beforebtn = new JButton("뒤로가기");
		beforebtn.setBounds(14, 12, 89, 36);
		nextpanel_2.add(beforebtn);

		beforebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextpanel_2.setVisible(false);
				nextpanel_1();
			}

		});

	}

	// 장르별 인기작 만나보기 화면

	public void nextpanel_genre() {
		nextpanel_genre = new JPanel();
		nextpanel_genre.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(nextpanel_genre);
		nextpanel_genre.setLayout(null);
		nextpanel_genre.setBackground(Color.WHITE);

		JLabel gernelabel = new JLabel("장르별 작품 찾아보기");
		gernelabel.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		gernelabel.setBounds(199, 12, 243, 37);
		nextpanel_genre.add(gernelabel);

		nextpanel_genre.setVisible(true);

		JButton beforebtn = new JButton("뒤로가기");
		beforebtn.setBounds(14, 12, 89, 36);
		nextpanel_genre.add(beforebtn);

		beforebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextpanel_genre.setVisible(false);
				nextpanel_2();

			}

		});

		for (int i = 0; i < checkbox.length; i++) {
			checkbox[i] = new JCheckBox(genre[i]);
			checkbox[i].addItemListener(this);
			checkbox[i].setFont(new Font("맑은 고딕", Font.BOLD, 17));
			checkbox[i].setBackground(Color.WHITE);
			checkbox[i].setForeground(Color.BLACK);
			nextpanel_genre.add(checkbox[i]);

		}

		// checkbox setBounds
		checkbox[0].setBounds(10, 54, 131, 27);
		checkbox[1].setBounds(10, 85, 131, 27);
		checkbox[2].setBounds(10, 116, 131, 27);
		checkbox[3].setBounds(248, 54, 131, 27);
		checkbox[4].setBounds(487, 54, 131, 27);
		checkbox[5].setBounds(248, 85, 131, 27);
		checkbox[6].setBounds(248, 116, 131, 27);
		checkbox[7].setBounds(487, 85, 131, 27);
		checkbox[8].setBounds(487, 116, 131, 27);
		checkbox[9].setBounds(10, 147, 131, 27);
		checkbox[10].setBounds(248, 147, 131, 27);
		checkbox[11].setBounds(487, 147, 131, 27);

	}

	// 테이블 세팅 메서드
	private void tablelist_genreselect(String checked) {

		// System.out.println(checkbox[i]);

		ArrayList<MemberVo> list_3 = dao.list_3(checked);

		tabledata_genreselect = new String[list_3.size()][2];

		for (int i = 0; i < list_3.size(); i++) {
			MemberVo data = (MemberVo) list_3.get(i);
			String TITLE = data.getTITLE();
			String STAR = data.getSTAR();

			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					tabledata_genreselect[i][j] = TITLE;
				} else if (j == 1) {
					tabledata_genreselect[i][j] = STAR;
				}
			}

			System.out.println(TITLE + " : " + STAR);
		}
		String col[] = { "작품명", "별점" };
		model_genreselect = new DefaultTableModel(tabledata_genreselect, col);
		table_genreselect = new JTable(model_genreselect);

		table_genreselect.setBounds(14, 91, 454, 428);

		scroll_genreselect = new JScrollPane(table_genreselect);
		scroll_genreselect.setBounds(10, 199, 608, 642);

		// table_genreselect.getTableHeader().setReorderingAllowed(false);
		// scroll_genreselect.setViewportView(table_genreselect);

		nextpanel_genre.add(scroll_genreselect);

//		tablelist_genreselect();
	}

	// 장르 checkbox 이벤트-------------------------------------
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

		Object obj = e.getSource();
		for (int i = 0; i < checkbox.length; i++) {
			if (obj == checkbox[i]) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("선택ok");
					checked = checkbox[i].getText();
					tablelist_genreselect(checked);

				} else {
					System.out.println("선택해제");
					table_genreselect.setVisible(false);
				}

			}
		}

		/*
		 * for (int i = 0; i < checkbox.length; i++) { if (e.getStateChange() ==
		 * ItemEvent.SELECTED) { if (e.getItem() == checkbox[i]) {
		 * System.out.println(checkbox[i].getText());
		 * 
		 * } } else { System.out.println(checkbox[i].getText() + "해제"); } }
		 * 
		 */

	}

	public void nextpanel_year() { // 시대별 인기작 만나보기 화면
		nextpanel_year = new JPanel();
		nextpanel_year.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(nextpanel_year);
		nextpanel_year.setBackground(Color.WHITE);
		nextpanel_year.setLayout(null);

		JLabel yearlabel = new JLabel("시대별 인기작 찾아보기");
		yearlabel.setBounds(181, 38, 278, 37);
		yearlabel.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		nextpanel_year.add(yearlabel);
		// 콤보박스 label--------------------------------------------
		JLabel YearLabel = new JLabel("연도");
		YearLabel.setBounds(61, 115, 49, 37);
		YearLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		nextpanel_year.add(YearLabel);

		JLabel SectionLabel = new JLabel("분기");
		SectionLabel.setBounds(348, 115, 49, 37);
		SectionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		nextpanel_year.add(SectionLabel);

		// year choice박스--------------------------------------------------------

		yearChoice = new Choice();
		yearChoice.setBounds(124, 115, 144, 36);
		yearChoice.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		for (int i = 0; i < year.length; i++) {
			yearChoice.add(year[i]);
			yearChoice.addItemListener(new MyItemListenr_year_choice());
		}
		nextpanel_year.add(yearChoice);

		// 분기 choicebox생성---------------------------------------------------

		SectionChoice = new Choice();
		SectionChoice.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		SectionChoice.setBounds(413, 116, 144, 37);
		for (int i = 0; i < Section.length; i++) {
			SectionChoice.add(Section[i]);
			SectionChoice.addItemListener(new MyItemListener_Section_choice());
		}
		nextpanel_year.add(SectionChoice);

	}

	// DB - JTable 생성(year)------------------------------------------------------
	private void yearTable(String clicked_year) {
		// dao = new MemberDAO();

		ArrayList<MemberVo> list_4 = (ArrayList<MemberVo>) dao.list_4(clicked_year);

		tabledata_yearSelected = new String[list_4.size()][2];

		for (int i = 0; i < list_4.size(); i++) {
			MemberVo data = (MemberVo) list_4.get(i);
			String TITLE = data.getTITLE();
			String STAR = data.getSTAR();

			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					tabledata_yearSelected[i][j] = TITLE;
				} else if (j == 1) {
					tabledata_yearSelected[i][j] = STAR;
				}
			}

			System.out.println(TITLE + " : " + STAR);
		}

		String col[] = { "작품명", "별점" };
		model_yearSelected = new DefaultTableModel(tabledata_yearSelected, col);
		table_yearSelected = new JTable(model_yearSelected);

		// table_yearSelected.setBounds(14, 164, 604, 677);

		scroll_yearSelected = new JScrollPane(table_yearSelected);
		scroll_yearSelected.setBounds(14, 164, 604, 677);

		nextpanel_year.add(scroll_yearSelected);

	}

	// year choice 이벤트-------------------------------------------------
	public class MyItemListenr_year_choice implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub

			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println(yearChoice.getSelectedItem());
				yearTable(yearChoice.getSelectedItem());

			} else {
				System.out.println("해제");
			}
		}

	}

	// DB - JTable (분기)------------------------------------------------
	private void SectionTable(String clicked_year, String clicked_Section) {
		// dao = new MemberDAO();

		ArrayList<MemberVo> list_5 = (ArrayList<MemberVo>) dao.list_5(clicked_year, clicked_Section);

		tabledata_yearSelected = new String[list_5.size()][2];

		for (int i = 0; i < list_5.size(); i++) {
			MemberVo data = (MemberVo) list_5.get(i);
			String TITLE = data.getTITLE();
			String STAR = data.getSTAR();

			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					tabledata_yearSelected[i][j] = TITLE;
				} else if (j == 1) {
					tabledata_yearSelected[i][j] = STAR;
				}
			}

			System.out.println(TITLE + " : " + STAR);
		}

		String col[] = { "작품명", "별점" };
		model_yearSelected = new DefaultTableModel(tabledata_yearSelected, col);
		table_yearSelected = new JTable(model_yearSelected);

		// table_yearSelected.setBounds(14, 164, 604, 677);

		scroll_yearSelected = new JScrollPane(table_yearSelected);
		scroll_yearSelected.setBounds(14, 164, 604, 677);

		nextpanel_year.add(scroll_yearSelected);

	}

	// 분기 choice 이벤트--------------------------------------------------
	public class MyItemListener_Section_choice implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println(SectionChoice.getSelectedItem());
				SectionTable(yearChoice.getSelectedItem(), SectionChoice.getSelectedItem());

			} else {
				System.out.println("해제");
			}
		}

	}
}
