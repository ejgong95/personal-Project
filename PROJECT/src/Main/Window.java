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

	private JPanel logpanel, nextpanel_1, nextpanel_genre, nextpanel_year;

	// 이미지출력---------------
	ImageIcon[] GenreImage = new ImageIcon[5];
	int index_g = 0;
	JLabel GenreMeet = new JLabel(GenreImage[index_g]);

	ImageIcon[] YearImage = new ImageIcon[2];
	int index_y = 0;
	JLabel YearMeet = new JLabel(YearImage[index_y]);

	// jtable-----------------
	private JTable tablelist, table_genreselect, table_yearSelected;
	static JScrollPane scroll, scroll_genreselect, scroll_yearSelected;
	static DefaultTableModel model, model_genreselect, model_yearSelected;
	static String[][] tabledata, tabledata_genreselect, tabledata_yearSelected;
	static JCheckBox[] checkbox = new JCheckBox[12];
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

	// 로그인 화면--------------------------------------------------
	public void LogIn() {
		logpanel = new JPanel();
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

		// 로그인 버튼에 기능 추가-----------------------------------------------------
		logbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println(txtID.getText());
				System.out.println(txtpsw.getText());

				if (txtID.getText().equals("") || txtpsw.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 입력하세요");
				} else {
					ArrayList<MemberVo> list = dao.list(txtID.getText());
					
					//txtID.setText(txtID.getText());
					
					if (list.size() == 0) {
						JOptionPane.showMessageDialog(null, "잘못된 아이디 혹은 비밀번호 입니다.");

					} else {

						JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다.");
						
						logpanel.setVisible(false);
						nextpanel_1();

					}
				}
			}

		});

		JLabel Ifyoulabel = new JLabel("아직 Laftel회원이 아니신가요?");
		Ifyoulabel.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		Ifyoulabel.setVerticalAlignment(SwingConstants.TOP);
		Ifyoulabel.setBounds(194, 228, 244, 37);
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

		JLabel LogoLabel = new JLabel();
		LogoLabel.setIcon(new ImageIcon("logo_image.jpg"));
		LogoLabel.setBounds(26, 26, 140, 50);
		logpanel.add(LogoLabel);

		JLabel ImageLabel = new JLabel(new ImageIcon("main_image.jpg"));
		// ImageLabel.setBackground(new Color(255, 255, 255));
		ImageLabel.setBounds(0, 325, 632, 528);
		logpanel.add(ImageLabel);

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
				ManagerLogIn();

			}

		});

	}

	// 회원 등록-------------------------------------------------------------------
	public void Register() {
		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setBackground(Color.WHITE);
		RegisterPanel.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(RegisterPanel);
		RegisterPanel.setLayout(null);

		JLabel IDregister = new JLabel("ID :");
		IDregister.setBounds(182, 177, 45, 18);
		IDregister.setFont(new Font("Arial Black", Font.PLAIN, 22));
		RegisterPanel.add(IDregister);
		
		IDtf = new JTextField();
		IDtf.setBounds(277, 173, 153, 35);
		RegisterPanel.add(IDtf);
		IDtf.setColumns(10);

		JLabel PWregister = new JLabel("PASSWORD :");
		PWregister.setBounds(66, 246, 161, 18);
		PWregister.setFont(new Font("Arial Black", Font.PLAIN, 22));
		RegisterPanel.add(PWregister);

		PWtf = new JTextField();
		PWtf.setBounds(277, 242, 153, 35);
		RegisterPanel.add(PWtf);
		PWtf.setColumns(10);
		
		JLabel NAMEregister = new JLabel("이름");
		NAMEregister.setBounds(165, 303, 58, 34);
		NAMEregister.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		RegisterPanel.add(NAMEregister);

		NAMEtf = new JTextField();
		NAMEtf.setColumns(10);
		NAMEtf.setBounds(277, 307, 153, 35);
		RegisterPanel.add(NAMEtf);

		JLabel GENREregister = new JLabel("좋아하는 장르");
		GENREregister.setBounds(66, 371, 161, 24);
		GENREregister.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		RegisterPanel.add(GENREregister);

		Choice GENREchoice = new Choice();
		GENREchoice.setBounds(277, 371, 153, 35);
		GENREchoice.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		RegisterPanel.add(GENREchoice);

		String[] genre = { "-------", "판타지", "액션", "로맨스", "개그", "일상", "모험", "순정", "아이돌", "스포츠", "SF", "스릴러", "추리" };

		for (int i = 0; i < genre.length; i++) {
			GENREchoice.add(genre[i]);
		}


		Button CheckBtn = new Button("중복확인");
		CheckBtn.setBounds(485, 173, 96, 35);
		RegisterPanel.add(CheckBtn);

		CheckBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}

		});

		JButton REGISTER = new JButton(new ImageIcon("joinus.png"));
		REGISTER.setBounds(213, 509, 217, 217);
		RegisterPanel.add(REGISTER);

		JButton beforebtn = new JButton("뒤로가기");
		beforebtn.setBounds(14, 12, 89, 36);
		RegisterPanel.add(beforebtn);

		beforebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterPanel.setVisible(false);
				logpanel.setVisible(true);
			}

		});

		REGISTER.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (IDtf.getText().equals("") || PWtf.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호를 입력해주세요");
				} else if (NAMEtf.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "이름을 입력해주세요");
				} else if (GENREchoice.getSelectedItem().toString().equals("-------")) {
					JOptionPane.showMessageDialog(null, "좋아하는 장르를 선택해주세요");
				} else {
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
			}
		});

	}

	// manager 로그인 메인 -------------------------------------------------------

	public void ManagerLogIn() {
		JPanel ManagerLogInPanel = new JPanel();
		ManagerLogInPanel.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(ManagerLogInPanel);
		ManagerLogInPanel.setLayout(null);
		ManagerLogInPanel.setBackground(Color.WHITE);
		
		JLabel areyoumanager = new JLabel("관리자 로그인");
		areyoumanager.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		areyoumanager.setBounds(256, 115, 148, 60);
		ManagerLogInPanel.add(areyoumanager);
		
		JLabel M_id = new JLabel("ID :");
		M_id.setFont(new Font("Arial Black", Font.PLAIN, 27));
		M_id.setBounds(117, 206, 113, 47);
		ManagerLogInPanel.add(M_id);

		JTextField M_id_tf = new JTextField();
		M_id_tf.setBounds(316, 206, 206, 47);
		ManagerLogInPanel.add(M_id_tf);
		M_id_tf.setColumns(10);

		JLabel M_pw = new JLabel("PASSWORD :");
		M_pw.setFont(new Font("Arial Black", Font.PLAIN, 22));
		M_pw.setBounds(117, 299, 185, 47);
		ManagerLogInPanel.add(M_pw);

		JPasswordField M_pw_tf = new JPasswordField();
		M_pw_tf.setColumns(10);
		M_pw_tf.setBounds(316, 299, 206, 47);
		ManagerLogInPanel.add(M_pw_tf);

		JButton M_logbtn = new JButton("LOG IN");
		M_logbtn.setFont(new Font("Arial Black", Font.PLAIN, 21));
		M_logbtn.setBounds(261, 396, 128, 67);
		ManagerLogInPanel.add(M_logbtn);

		M_logbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(M_id_tf.getText());
				System.out.println(M_pw_tf.getText());

				if (M_id_tf.getText().equals("") || M_pw_tf.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 입력하세요");
				} else {
					
					M_id_tf.setText(M_id_tf.getText());
					
					ArrayList<MemberVo> list_7 = dao.list_7(M_id_tf.getText());
					
					if (list_7.size() == 0) {
						JOptionPane.showMessageDialog(null, "잘못된 아이디 혹은 비밀번호 입니다.");

					} else {
						JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다.");
						// M_id_tf.setText(M_id_tf.getText());
						ManagerLogInPanel.setVisible(false);

						Manager();
					}
				}
			}

		});

	}

	// manager 작품 등록---------------------------------------------------------

	public void Manager() {

		JPanel RegisterPanel = new JPanel();
		RegisterPanel.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(RegisterPanel);
		RegisterPanel.setLayout(null);
		RegisterPanel.setBackground(Color.WHITE);

		JLabel insert = new JLabel("작품 등록하기");
		insert.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		insert.setBounds(223, 70, 197, 52);
		RegisterPanel.add(insert);

		JButton HomeBtn = new JButton(new ImageIcon("homebtn.png"));
		HomeBtn.setBounds(509, 18, 90, 90);
		RegisterPanel.add(HomeBtn);
		HomeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RegisterPanel.setVisible(false);
				logpanel.setVisible(true);

			}

		});

		JLabel m_title = new JLabel("작품명");
		m_title.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		m_title.setBounds(110, 190, 76, 40);
		RegisterPanel.add(m_title);

		m_titleTF = new JTextField();
		m_titleTF.setBounds(251, 193, 191, 40);
		m_titleTF.setColumns(50);
		RegisterPanel.add(m_titleTF);

		JLabel m_genre = new JLabel("장르");
		m_genre.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		m_genre.setBounds(110, 242, 76, 39);
		RegisterPanel.add(m_genre);

		Choice GENREchoice = new Choice();
		GENREchoice.setBounds(251, 244, 191, 40);
		GENREchoice.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		RegisterPanel.add(GENREchoice);
		String[] genre = { "-------", "판타지", "액션", "로맨스", "개그", "일상", "모험", "순정", "아이돌", "스포츠", "SF", "스릴러", "추리" };
		
		for (int i = 0; i < genre.length; i++) {
			GENREchoice.add(genre[i]);
		}
		
		JLabel m_year = new JLabel("연도");
		m_year.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		m_year.setBounds(110, 293, 76, 40);
		RegisterPanel.add(m_year);

		// year choice박스--------------------------------------------------------

		yearChoice = new Choice();
		yearChoice.setBounds(251, 296, 122, 37);
		yearChoice.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		for (int i = 0; i < year.length; i++) {
			yearChoice.add(year[i]);
		}
		RegisterPanel.add(yearChoice);

		// 분기 choicebox생성---------------------------------------------------

		SectionChoice = new Choice();
		SectionChoice.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		SectionChoice.setBounds(405, 296, 122, 37);
		for (int i = 0; i < Section.length; i++) {
			SectionChoice.add(Section[i]);
		}
		RegisterPanel.add(SectionChoice);

		JLabel m_star = new JLabel("평가(별점)");
		m_star.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		m_star.setBounds(110, 345, 105, 40);
		RegisterPanel.add(m_star);

		m_starTF = new JTextField();
		m_starTF.setColumns(10);
		m_starTF.setBounds(251, 347, 191, 43);
		RegisterPanel.add(m_starTF);

		JButton InsertBtn = new JButton("등록하기");
		InsertBtn.setBounds(223, 442, 197, 90);
		RegisterPanel.add(InsertBtn);

		InsertBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (m_titleTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "작품명을 입력해주세요");
				} else if (GENREchoice.getSelectedItem().toString().equals("-------")) {
					JOptionPane.showMessageDialog(null, "장르를 선택해주세요");
				} else if (m_starTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "평가 점수를 입력해주세요");
				} else if (yearChoice.getSelectedItem().toString().equals("-------")
						|| SectionChoice.getSelectedItem().toString().equals("-------")) {
					JOptionPane.showMessageDialog(null, "연도 및 분기를 선택해주세요");
				} else {
					ArrayList<MemberVo> list_8 = dao.list_8(m_titleTF.getText(), GENREchoice.getSelectedItem(),
							yearChoice.getSelectedItem().toString() + " " + SectionChoice.getSelectedItem().toString(), m_starTF.getText());
					for (int i = 0; i < list_8.size(); i++) {
						MemberVo data = (MemberVo) list_8.get(i);
						String TITLE = m_titleTF.getText();
						String GENRE = GENREchoice.getSelectedItem();
						String L_YEAR = yearChoice.getSelectedItem().toString() + " " + SectionChoice.getSelectedItem().toString();
						String STAR = m_starTF.getText();
						// String GENRE = ();
						System.out.println(TITLE + " : " + GENRE + " : " + L_YEAR + " : " + STAR);
					}
				}
			}

		});

	}

	// 로그인 성공 화면----------------------------------------------------------------
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
		
		JButton HomeBtn = new JButton(new ImageIcon("homebtn.png"));
		HomeBtn.setBounds(509, 18, 90, 90);
		nextpanel_1.add(HomeBtn);
		HomeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nextpanel_1.setVisible(false);
				logpanel.setVisible(true);

			}

		});
		

		tablelist();

		JButton searchothers = new JButton(new ImageIcon("meet.png"));
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

	// 반가워요페이지(작품추천)테이블------------------------------------------------------
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

	// 장르별 or 시대별 선택 화면-------------------------------------------------

	public void nextpanel_2() {

		JPanel nextpanel_2 = new JPanel();
		nextpanel_2.setBackground(Color.WHITE);
		nextpanel_2.setBounds(0, 0, 632, 853);
		frame.getContentPane().add(nextpanel_2);
		nextpanel_2.setLayout(null);

		JLabel GenreTag = new JLabel("장르별 인기작 만나보기");
		GenreTag.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		GenreTag.setBounds(186, 12, 251, 39);
		nextpanel_2.add(GenreTag);

		JLabel YearTag = new JLabel("시대별 인기작 만나보기");
		YearTag.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		YearTag.setBounds(186, 435, 251, 39);
		nextpanel_2.add(YearTag);

		JButton GenreGo = new JButton("GO!!");
		GenreGo.setFont(new Font("Arial Black", Font.PLAIN, 19));
		GenreGo.setBounds(500, 16, 105, 35);
		nextpanel_2.add(GenreGo);

		JButton YearGo = new JButton("GO!!");
		YearGo.setFont(new Font("Arial Black", Font.PLAIN, 19));
		YearGo.setBounds(500, 438, 105, 35);
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

		// 장르 이미지 출력--------------------------------------------------------------

		for (int i = 0; i < GenreImage.length; i++) {
			GenreImage[i] = new ImageIcon("00" + i + ".png");
			GenreMeet.setIcon(GenreImage[i]);
		}

		GenreMeet.setBounds(14, 61, 604, 355);
		nextpanel_2.add(GenreMeet);

		JButton GenreMeetBtn = new JButton("");
		GenreMeetBtn.setBounds(14, 61, 604, 355);
		GenreMeetBtn.setBackground(Color.WHITE);
		GenreMeetBtn.setOpaque(false);
		nextpanel_2.add(GenreMeetBtn);

		GenreMeetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("클릭");
				if (index_g < GenreImage.length) {
					GenreMeet.setIcon(GenreImage[index_g++]);
				} else {
					index_g = 0;
				}
			}

		});

		// 연도 이미지 출력--------------------------------------------------------------
		for (int i = 0; i < YearImage.length; i++) {
			YearImage[i] = new ImageIcon("0" + i + ".png");
			YearMeet.setIcon(YearImage[i]);
		}

		YearMeet.setBounds(14, 486, 604, 355);
		nextpanel_2.add(YearMeet);

		JButton YearMeetBtn = new JButton("");
		YearMeetBtn.setBounds(14, 486, 604, 355);
		YearMeetBtn.setBackground(Color.WHITE);
		YearMeetBtn.setOpaque(false);
		nextpanel_2.add(YearMeetBtn);

		YearMeetBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("클릭");
				if (index_y < YearImage.length) {
					YearMeet.setIcon(YearImage[index_y++]);
				} else {
					index_y = 0;
				}
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

	// 장르별 인기작 만나보기 화면-------------------------------------------------------

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

	// 테이블 세팅 메서드---------------------------------------------------------------
	private void tablelist_genreselect(String checked) {

		// System.out.println(checkbox[i]);

		ArrayList<MemberVo> list_3 = dao.list_3();

		tabledata_genreselect = new String[list_3.size()][2];

		for (int i = 0; i < list_3.size(); i++) {
			MemberVo data = (MemberVo) list_3.get(i);
			System.out.println(checked);
//			if(checked.equals(data.getGENRE())) {
//				System.out.println(data.getGENRE());
//			}
			String TITLE = data.getTITLE();
			String STAR = data.getSTAR();

			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					tabledata_genreselect[i][j] = TITLE;
				} else if (j == 1) {
					tabledata_genreselect[i][j] = STAR;
				}

				System.out.println(TITLE + " : " + STAR);
			}
			String col[] = { "작품명", "별점" };
			model_genreselect = new DefaultTableModel(tabledata_genreselect, col);
			table_genreselect = new JTable(model_genreselect);

			//table_genreselect.setBounds(14, 91, 454, 428);

			scroll_genreselect = new JScrollPane(table_genreselect);
			scroll_genreselect.setBounds(10, 199, 608, 642);

			// table_genreselect.getTableHeader().setReorderingAllowed(false);
			// scroll_genreselect.setViewportView(table_genreselect);

			nextpanel_genre.add(scroll_genreselect);
		}

//		tablelist_genreselect();
	}

	// 장르 checkbox 이벤트--------------------------------------------------------------
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

		Object obj = e.getSource();
		// System.out.println(obj + "@@@@@");

		for (int i = 0; i < checkbox.length; i++) {
			if (obj == checkbox[i]) {

				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("선택ok");
					checked = checkbox[i].getText();

					tablelist_genreselect(checked);

				} else {
					System.out.println("선택해제");
					checked = checkbox[i].getText();
					tablelist_genreselect(checked);
					
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

	// 시대별 인기작 만나보기 화면--------------------------------------------------------
	public void nextpanel_year() {
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

		JButton beforebtn = new JButton("뒤로가기");
		beforebtn.setBounds(14, 12, 89, 36);
		nextpanel_year.add(beforebtn);

		beforebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextpanel_year.setVisible(false);
				nextpanel_2();
			}
		});

		// year choice박스--------------------------------------------------------

		yearChoice = new Choice();
		yearChoice.setBounds(124, 115, 144, 36);
		yearChoice.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		for (int i = 0; i < year.length; i++) {
			yearChoice.add(year[i]);

		}
		yearChoice.addItemListener(new MyItemListenr_year_choice());
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

//			System.out.println(TITLE + " : " + STAR);
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
			System.out.println(e);
			if (e.getStateChange() == ItemEvent.SELECTED) {
//				System.out.println(yearChoice.getSelectedItem());
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
