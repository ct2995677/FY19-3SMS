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

public class StudentManager {
	
	static StudentManager sm = new StudentManager();
	//解决栈溢出
	public StudentManager() {
		super();
	}
	
	//声明StudentClient对象,在调用StudentClient中方法的时候调用
		 Client c;//为什么下面没法用
	//创建参数是StudentClient类型的构造器,防止两边互相调用造成栈溢出
	public StudentManager(Client c) {
		this.c = c;
	}
	//查看学生界面
	public void checkStudentPage(Scanner scanner) {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("+1.增加学生  2.修改学生  3.删除学生  4.查询学生  5.打印学生  6.课程管理   7.返回主界面  8.系统退出 ");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("请选择菜单:");
		int nextInt = scanner.nextInt();	
		switch (nextInt) {
		//增加学生
		case 1:
			addStudentPage(scanner);
			System.out.println("数据添加完毕,系统自动返回目录......");
			checkStudentPage(scanner);
			break;
		//根据id查询学生信息
		case 2:
			//修改学生年龄
			updateStudentPage(scanner);
			System.out.println("数据修改完毕,系统自动返回目录......");
			checkStudentPage(scanner);
			break;
			//根据id查询学生姓名	
		case 3:
			//删除学生
			delectStudentPage(scanner);
			System.out.println("删除成功,返回上一级");
			checkStudentPage(scanner);
			break;
	
		case 4:
			//查询学生
			List<Student> stu = sm.getStudent();
			for (int i = 0; i < stu.size(); i++) {
				System.out.println("学号:" + stu.get(i).getsId() + "  姓名:" + stu.get(i).getsName() + "  年龄:"
						+ stu.get(i).getaAge() + "  性别:" + stu.get(i).getsSex() + "  选修课程号:" + stu.get(i).getcId());
			}
			System.out.println("查询成功,返回上一级");
			checkStudentPage(scanner);
			break;

		case 5:
			//打印学生	
			List<Student> stu1 = sm.getStudent();
			for (int i = 0; i < stu1.size(); i++) {
				System.out.println("学号:" + stu1.get(i).getsId() + "  姓名:" + stu1.get(i).getsName() + "  年龄:"
						+ stu1.get(i).getaAge() + "  性别:" + stu1.get(i).getsSex() + "  选修课程号:" + stu1.get(i).getcId());
			}
			System.out.println("打印学生信息成功,返回上一级");
			checkStudentPage(scanner);
			break;
		case 6:
			//课程管理
			List<Course> cour = sm.getCourse();
			for (int i = 0; i < cour.size(); i++) {
				System.out.println("课程号:" + cour.get(i).getcId() + "  课程名称:" + cour.get(i).getcName());
			}
			System.out.println("打印课程信息成功,返回上一级");
			checkStudentPage(scanner);
			break;
		case 7:
			//返回主界面
			Client c = new Client();
			c.stuManagerIndex();
			break;
		case 8:
			//退出系统
			System.out.println("系统退出成功");
			return;
			
		default:
			System.out.println("输入有误,请重新输入");
			checkStudentPage(scanner);
			break;
		}
	
	}
	
	
	
	private static Properties prop;
	static {
		prop = new Properties();
		try {
			prop.load(Object.class.getResourceAsStream("/setting.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	//添加学生界面
	public void addStudentPage(Scanner scanner) {
		System.out.println("请输入添加学生的ID");
		int id = scanner.nextInt();
		System.out.println("请输入添加学生的姓名");
		String name = scanner.next();
		System.out.println("请输入添加学生的年龄");
		int age = scanner.nextInt();
		System.out.println("请输入添加学生的性别");
		String sex = scanner.next();
		System.out.println("请输入添加学生所选课程");
		int cid = scanner.nextInt();
		Student stu = new Student(id, name, age, sex, cid);
		add(stu);
	}
	//增加学生方法
	public void add(Student stu) {
			PreparedStatement ps =null;
			Connection conn =null;
			try {
				//获得连接
				conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));
				//预编译sql
				String sql = "INSERT INTO student VALUES(?,?,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, stu.getsId());
				ps.setString(2, stu.getsName());
				ps.setDouble(3, stu.getaAge());
				ps.setString(4, stu.getsSex());
				ps.setInt(5, stu.getcId());
				//执行sql语句
				int executeUpdate = ps.executeUpdate();
				if(executeUpdate != 0) {
					System.out.println("添加成功");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(null != ps) {
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(null != conn) {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	
	//查询学生
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

	//删除学生界面
	public void delectStudentPage(Scanner scanner) {
		System.out.println("请输入删除学生的id");
		int id = scanner.nextInt();
		delectStudent(id);
		
	}
	//删除学生
		public void delectStudent(int num) {
			
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("username"),prop.getProperty("password"));
				String sql = "DELETE from student where sId=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1,num);
				int executeUpdate = ps.executeUpdate();
				if(executeUpdate != 0) {
					System.out.println("删除成功");
				}			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(null != ps) {
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(null != conn) {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	//修改学生界面
		public void updateStudentPage(Scanner scanner) {
			
			System.out.println("请输入修改学生的ID");
			int id = scanner.nextInt();
			System.out.println("请输入修改后的年龄");
			int age = scanner.nextInt();
			updateStudent(id, age);
		}
	//修改学生
		public void updateStudent(int alterId,int age) {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("username"),prop.getProperty("password"));
				String sql = "update student set sAge=? where sId=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1,age);
				ps.setInt(2,alterId);
				int executeUpdate = ps.executeUpdate();
				if(executeUpdate != 0) {
					System.out.println("修改成功");
				}			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(null != ps) {
					try {
						ps.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(null != conn) {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	//课程管理
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
}
