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

public class Query {
	//功能:
	//1.通过学生号,查询该学生选课信息
	//2.通过课程号,打印当前课程下的学生信息
	private static Properties prop;
	static {
		prop = new Properties();
		try {
			prop.load(Object.class.getResourceAsStream("/setting.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void query(Scanner scanner) {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("+1.通过学生号,查询该学生选课信息  2.通过课程号,打印当前课程下的学生信息   3.返回主界面  4.系统退出 ");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("请选择操作:");
		int nextInt = scanner.nextInt();
		switch (nextInt) {
		case 1:
			// 通过学生号,查询该学生选课信息
			queryCousePage(scanner);
			System.out.println("数据查询完毕,系统自动返回目录......");
			query(scanner);
			break;
		case 2:
			// 2.通过课程号,打印当前课程下的学生信息
			queryStudentPage(scanner);
			System.out.println("数据修改完毕,系统自动返回目录......");
			query(scanner);
			break;
		
		case 3:
			Client c = new Client();
			c.stuManagerIndex();
			return;
		case 4:
			System.out.println("系统退出成功");
			return;
		default:
			System.out.println("输入有误,请重新输入");
			query(scanner);
			break;
		}

	}
	
	//1.通过学生号,查询该学生选课信息 界面
	public void queryCousePage(Scanner scanner) {
		System.out.println("请输入要查询信息的学生ID");
		int id = scanner.nextInt();
		List<Course> queryCouse = queryCouse(id);
		for(int i = 0; i < queryCouse.size();i++) {
			System.out.println("学生ID:"+id+"  课程ID:"+queryCouse.get(i).getcId()+"  课程名:"+queryCouse.get(i).getcName());
		}
	}
	
	//1.通过学生号,查询该学生选课信息 方法
	public List<Course> queryCouse(int num){
		ArrayList<Course> getAll = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.注册驱动(当驱动包较多时需要表明是
			Class.forName("com.mysql.jdbc.Driver");
			//2.获得连接对象
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));	
			//3.预处理sql语句
			String sql = "SELECT * FROM course WHERE cid in (SELECT cId FROM student WHERE cid = ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			//4.执行sql语句
			rs = ps.executeQuery();
			//5.处理结果循环拿到数据
			while(rs.next()) {
				int id = rs.getInt("cId");
				String name = rs.getString("cName");
				Course account = new Course(id,name);
				getAll.add(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			if(null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return getAll;
		
	}

	//2.通过课程号,打印当前课程下的学生信息 界面
	public void queryStudentPage(Scanner sca) {
		System.out.println("请输入查询的课程号");
		int nextInt = sca.nextInt();
		List<Student> stu1 = queryStudent(nextInt);
		for (int i = 0; i < stu1.size(); i++) {
			System.out.println("学号:" + stu1.get(i).getsId() + "  姓名:" + stu1.get(i).getsName() + "  年龄:"
					+ stu1.get(i).getaAge() + "  性别:" + stu1.get(i).getsSex() + "  选修课程号:" + stu1.get(i).getcId());
		}
	}
	//2.通过课程号,打印当前课程下的学生信息 方法
	public List<Student> queryStudent(int num){
		ArrayList<Student> getAll = new ArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.注册驱动(当驱动包较多时需要表明是
			Class.forName("com.mysql.jdbc.Driver");
			//2.获得连接对象
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));	
			//3.预处理sql语句
			String sql = "SELECT * FROM student WHERE cid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			//4.执行sql语句
			rs = ps.executeQuery();
			//5.处理结果循环拿到数据
			while(rs.next()) {
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
		}finally {
			if(null != rs) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return getAll;
	}

}
