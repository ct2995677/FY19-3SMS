package com.neuedu.student;

public class Student {

	private Integer sId;
	private String sName;
	private Integer aAge;
	private String sSex;
	private Integer cId;
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(Integer sId, String sName, Integer aAge, String sSex, Integer cId) {
		super();
		this.sId = sId;
		this.sName = sName;
		this.aAge = aAge;
		this.sSex = sSex;
		this.cId = cId;
	}
	public Integer getsId() {
		return sId;
	}
	public void setsId(Integer sId) {
		this.sId = sId;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public Integer getaAge() {
		return aAge;
	}
	public void setaAge(Integer aAge) {
		this.aAge = aAge;
	}
	public String getsSex() {
		return sSex;
	}
	public void setsSex(String sSex) {
		this.sSex = sSex;
	}
	public Integer getcId() {
		return cId;
	}
	public void setcId(Integer cId) {
		this.cId = cId;
	}
	@Override
	public String toString() {
		return "Student [sId=" + sId + ", sName=" + sName + ", aAge=" + aAge + ", sSex=" + sSex + ", cId=" + cId + "]";
	}
	
	
	
}
