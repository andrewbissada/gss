/*
 * Copyright 2007, 2008, 2009 Electronic Business Systems Ltd.
 *
 * This file is part of GSS.
 *
 * GSS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GSS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GSS.  If not, see <http://www.gnu.org/licenses/>.
 */
package gr.ebs.gss.server.domain.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author chstath
 */
public class UserDTO implements Serializable {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The persistence ID of the object.
	 */
	private Long id;

	/**
	 * The first name of the user.
	 */
	private String firstname;

	/**
	 * The last name of the user.
	 */
	private String lastname;

	/**
	 * The full name of the user.
	 */
	private String name;

	/**
	 * The username of the user.
	 */
	private String username;

	/**
	 * The e-mail address of the user.
	 */
	private String email;

	/**
	 * The list of groups that have been specified by this user.
	 *
	 */
	private List<GroupDTO> groupsSpecified = new ArrayList<GroupDTO>();

	/**
	 * The set of groups of which this user is member.
	 *
	 */
	private Set<GroupDTO> groupsMember;

	/**
	 * The list of all tags this user has specified on all files.
	 *
	 */
	private List<FileTagDTO> fileTags;

	/**
	 * The user class to which this user belongs.
	 */
	private UserClassDTO userClass;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param newId the id to set
	 */
	public void setId(final Long newId) {
		id = newId;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param newFirstname the firstname to set
	 */
	public void setFirstname(final String newFirstname) {
		firstname = newFirstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param newLastname the lastname to set
	 */
	public void setLastname(final String newLastname) {
		lastname = newLastname;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param newName the name to set
	 */
	public void setName(final String newName) {
		name = newName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param newEmail the email to set
	 */
	public void setEmail(final String newEmail) {
		email = newEmail;
	}


	/**
	 * Retrieve the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * Modify the username.
	 *
	 * @param newUsername the username to set
	 */
	public void setUsername(String newUsername) {
		username = newUsername;
	}
}