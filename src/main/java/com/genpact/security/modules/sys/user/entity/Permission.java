package com.genpact.security.modules.sys.user.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "security_permission")
public class Permission implements Serializable {

	private static final long serialVersionUID = 8168491333970695934L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long permissionId;

	// 权限名称
	private String name;

	// 权限描述
	private String description;

	// 授权链接
	private String url;

	// 父节点id
	private Long pid;
	
	private String method;

	/**
	 * 角色与权限的关联关系 mappedBy: 就是 Role.class 里的 Set<Permission> 的对象名
	 */
	@ManyToMany(mappedBy = "permissions")
	private Set<Role> roles = new HashSet<>();

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
