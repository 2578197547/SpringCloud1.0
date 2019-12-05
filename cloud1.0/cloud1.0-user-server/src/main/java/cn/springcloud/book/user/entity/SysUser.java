package cn.springcloud.book.user.entity;

import java.util.List;

import javax.persistence.*;
//系统用户
@Entity
@Table(name = "sys_user")
public class SysUser {
 
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
 
    @Transient
    private List<SysRole> roles;
 
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public List<SysRole> getRoles() {
        return roles;
    }
 
    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }
 
    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
    
    public SysUser() {
		super();
	}
}
