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

public class CourseManager {
	// 读取配置文件
	private static Properties prop;
	
	//解决栈溢出
		public CourseManager() {
			super();
		}
		
		//声明StudentClient对象,在调用StudentClient中方法的时候调用
			Client c;
		//创建参数是StudentClient类型的构造器,防止两边互相调用造成栈溢出
		public CourseManager(Client c) {
			this.c = c;
		}
	static CourseManager cm = new CourseManager();
	static {
		prop = new Properties();
		try {
			prop.load(Object.class.getResourceAsStream("/setting.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void checkCourse(Scanner scanner) {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("+1.增加课程  2.修改课程  3.删除课程  4.查询课程    5.返回主界面  6.系统退出 ");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("请选择操作:");
		int nextInt = scanner.nextInt();
		switch (nextInt) {
		case 1:
			// 增加课程
			addCoursePage(scanner);
			System.out.println("数据添加完毕,系统自动返回目录......");
			checkCourse(scanner);
			break;
		case 2:
			// 修改数据
			updateCoursePage(scanner);
			System.out.println("数据修改完毕,系统自动返回目录......");
			checkCourse(scanner);
			break;
		case 3:
			// 删除数据
			delectCoursePage(scanner);
			System.out.println("数据删除完毕,系统自动返回目录......");
			checkCourse(scanner);
			break;
		case 4:
			// 查询数据
			getCourse();
			List<Course> cour = cm.getCourse();
			for (int i = 0; i < cour.size(); i++) {
				System.out.println("课程号:" + cour.get(i).getcId() + "  课程名称:" + cour.get(i).getcName());
			}
			System.out.println("数据查询完毕,系统自动返回目录......");
			checkCourse(scanner);
			break;
		case 5:
			Client c = new Client();
			c.stuManagerIndex();
			return;
		case 6:
			System.out.println("系统退出成功");
			return;
		}

	}

	// 增加课程
	public void addCoursePage(Scanner scanner) {
		System.out.println("请输入添加课程的ID");
		int id = scanner.nextInt();
		System.out.println("请输入添加课程名");
		String name = scanner.next();
		Course cou = new Course(id, name);
		add(cou);
	}

	// 增加课程方法
	public void add(Course cou) {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			// 获得连接
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));
			// 预编译sql
			String sql = "INSERT INTO course VALUES(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cou.getcId());
			ps.setString(2, cou.getcName());
			// 执行sql语句
			int executeUpdate = ps.executeUpdate();
			if (executeUpdate != 0) {
				System.out.println("添加成功");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// 修改课程界面
	public void updateCoursePage(Scanner scanner) {

		System.out.println("请输入修改课程的ID");
		int id = scanner.nextInt();
		System.out.println("请输入修改后的课程名");
		String newName = scanner.next();
		updateCourse(id, newName);
	}

	// 修改课程
	public void updateCourse(int alterId, String name) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));
			String sql = "update course set cName=? where cId=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, alterId);
			int executeUpdate = ps.executeUpdate();
			if (executeUpdate != 0) {
				System.out.println("修改成功");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// 删除课程界面
	public void delectCoursePage(Scanner scanner) {
		System.out.println("请输入删除课程的id");
		int id = scanner.nextInt();
		delectCourse(id);

	}

	// 删除课程
	public void delectCourse(int num) {

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
					prop.getProperty("password"));
			String sql = "DELETE from course where cId=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			int executeUpdate = ps.executeUpdate();
			if (executeUpdate != 0) {
				System.out.println("删除成功");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != ps) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
			
				Course account = new Course(id, name);
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
}
