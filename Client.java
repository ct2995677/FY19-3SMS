package com.neuedu.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Client {
	//创建学生管理类对象
	StudentManager sm = new StudentManager();
	//创建课程管理类对象
	CourseManager cm = new CourseManager();
	//创建查询对象
	Query query = new Query();
	//创建修改对象
	Alter alter = new Alter();
	
	
	
	Scanner sca = new Scanner(System.in);
	//静态代码块,获取配置文件变量
	private static Properties prop;
	static {
		prop = new Properties();
		try {
			prop.load(Object.class.getResourceAsStream("/setting.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		System.out.println("系统正在初始化.....");
		System.out.println("学校现有学生");
		Client client = new Client();
		// 初始化学生
		List<Student> stu = client.getStudent();
		for (int i = 0; i < stu.size(); i++) {
			System.out.println("学号:" + stu.get(i).getsId() + "  姓名:" + stu.get(i).getsName() + "  年龄:"
					+ stu.get(i).getaAge() + "  性别:" + stu.get(i).getsSex() + "  选修课程号:" + stu.get(i).getcId());
		}
		// 初始化课程
		System.out.println("学校现有课程");
		List<Course> cour = client.getCourse();
		for (int i = 0; i < cour.size(); i++) {
			System.out.println("课程号:" + cour.get(i).getcId() + "  课程名称:" + cour.get(i).getcName());
		}

		System.out.println("学校创建成功");
		//选择操作
		client.stuManagerIndex();
	}

	//退出系统方法
		public void exitSystem() {
			System.exit(0);
		}
	//查询学生信息
	// 查询学生
public List<Student> getStudent() {
		ArrayList<Student> getAll = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 1.注册驱动(当驱动包较多时需要表明是
			Class.forName("com.mysql.jdbc.Driver");
			// 2.获得连接对象
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));
			// 3.预处理sql语句
			String sql = "select * from student";
			ps = conn.prepareStatement(sql);
			// 4.执行sql语句
			rs = ps.executeQuery();
			// 5.处理结果循环拿到数据
			while (rs.next()) {
				int id = rs.getInt("sId");
				String name = rs.getString("sName");
				int age = rs.getInt("sAge");
				String sex = rs.getString("sSex");
				int cid = rs.getInt("cId");
				Student account = new Student(id, name, age, sex, cid);
				getAll.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return getAll;
	}

	// 查询课程
	public List<Course> getCourse() {
		ArrayList<Course> getAll = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 1.注册驱动(当驱动包较多时需要表明是
			Class.forName("com.mysql.jdbc.Driver");
			// 2.获得连接对象
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));
			// 3.预处理sql语句
			String sql = "select * from course";
			ps = conn.prepareStatement(sql);
			// 4.执行sql语句
			rs = ps.executeQuery();
			// 5.处理结果循环拿到数据
			while (rs.next()) {
				int id = rs.getInt("cId");
				String name = rs.getString("cName");

				Course course = new Course(id, name);
				getAll.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return getAll;
	}

	//学生管理首页
	
	public void stuManagerIndex() {
		System.out.println("****************请选择要操作的信息对应的数字****************");
		System.out.println("1.学生信息管理  2.课程信息管理  3.查询信息  4.修改信息  5.退出");
		System.out.println("*****************************************************");
		System.out.println("请选择:");
		int nextInt = sca.nextInt();
		switch (nextInt) {
		//学生信息管理
		case 1:
			sm.checkStudentPage(sca);
			break;
		//课程信息管理
		case 2:
			cm.checkCourse(sca);
			break;
		//查询信息
		case 3:
			//通过学生学号查询该学生选课情况
			query.query(sca);
			break;
		//修改信息	
		case 4:
			//修改信息
			alter.alter(sca);
			break;
		//退出	
		case 5:
			System.out.println("已退出");
			exitSystem();
			break;
		default:
			System.out.println("输入有误,请重新输入");
			stuManagerIndex();
			break;
		}
	}

}
