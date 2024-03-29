/*
 * Copyright 2009 Electronic Business Systems Ltd.
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
package org.gss_project.gss.server.rest;

import java.net.URLDecoder;
import org.gss_project.gss.common.exceptions.RpcException;
import org.gss_project.gss.server.domain.User;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A class that handles operations on the 'user search' namespace.
 *
 * @author past
 */
public class UserSearchHandler extends RequestHandler {
	/**
	 * The logger.
	 */
	private static Log logger = LogFactory.getLog(UserSearchHandler.class);

	/**
	 * A flag that will force all queries to contain a @ character, mitigating privacy
	 * concerns about user accounts.
	 */
	private static final boolean mustContainsAt = true;

	/**
     * Serve the 'user search' namespace that contains results in queries to find users.
     *
     * @param req The servlet request we are processing
     * @param resp The servlet response we are processing
     * @throws IOException if an input/output error occurs
	 */
	void serveResults(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	String contextPath = getContextPath(req, true);
        String path = getInnerPath(req, PATH_USERS);
        // Chop any trailing slash.
        if (path.endsWith("/"))
        	path = path.substring(0, path.length()-1);
		if (path.equals(""))
			path = "/";
			
    	if (!path.equals("/"))
			try {
	        	JSONArray json = new JSONArray();
	        	path = URLDecoder.decode(path.substring(1), "UTF-8");
	        	if (mustContainsAt && !path.contains("@"))
	        		path += '@';
				List<User> users = getService().getUsersByUserNameLike(path);
		    	for (User u: users) {
					// Build the proper parent URL
					String pathInfo = req.getPathInfo();
					String parentUrl = contextPath.replaceFirst(pathInfo, "");
		    		JSONObject j = new JSONObject();
		    		j.put("username", u.getUsername()).put("name", u.getName()).
		    			put("uri", parentUrl + "/" + u.getUsername());
		    		json.put(j);
		    	}
            	sendJson(req, resp, json.toString());
            	// Workaround for IE's broken caching behavior.
        		resp.setHeader("Expires", "-1");
    		} catch (RpcException e) {
    			logger.error("", e);
    			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    			return;
			} catch (JSONException e) {
				logger.error("", e);
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No search query found");
			return;
    	}
	}

}
