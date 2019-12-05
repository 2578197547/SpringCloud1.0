package cn.springcloud.book.auth.entity;

import javax.persistence.*;

/**
 * 系统角色
 * @author xielijie.93
 * 2019年12月5日上午11:27:40
 */
@Entity
@Table(name = "sys_role")
public class SysRole {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

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
}
