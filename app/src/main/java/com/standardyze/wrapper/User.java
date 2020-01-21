package com.standardyze.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("fname")
@Expose
private String fname;
@SerializedName("lname")
@Expose
private String lname;
@SerializedName("email")
@Expose
private String email;
@SerializedName("role")
@Expose
private String role;
@SerializedName("companyId")
@Expose
private Integer companyId;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getFname() {
return fname;
}

public void setFname(String fname) {
this.fname = fname;
}

public String getLname() {
return lname;
}

public void setLname(String lname) {
this.lname = lname;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getRole() {
return role;
}

public void setRole(String role) {
this.role = role;
}

public Integer getCompanyId() {
return companyId;
}

public void setCompanyId(Integer companyId) {
this.companyId = companyId;
}

}