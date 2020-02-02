package cn.springcloud.book.auth.entity;

import java.util.List;

import javax.persistence.*;

/**
 * 系统用户
 * @author xielijie.93
 * 2019年12月5日上午11:28:12
 */
@Entity
@Table(name = "sys_user")
public class SysUser {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name",nullable = false, unique = true)
	private String name;
	@Column(name = "password")
	private String password;

/*    @ManyToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_user",joinColumns = @JoinColumn(name="sys_user_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "sys_role_id",referencedColumnName = "id"))*/
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
		return "SysUser{" + "id=" + id + ", name='" + name + '\''
				+ ", password='" + password + '\'' + ", roles=" + roles + '}';
	}
}
