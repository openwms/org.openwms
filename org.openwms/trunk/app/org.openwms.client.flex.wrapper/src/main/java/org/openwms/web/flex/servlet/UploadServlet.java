/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.web.flex.servlet;

import http.utils.multipartrequest.MultipartRequest;
import http.utils.multipartrequest.ServletMultipartRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.service.UserService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * An UploadServlet.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1841599305520493632L;

    /**
     * Noting is done here. The request is ignored.
     * 
     * @param req
     *            An {@link HttpServletRequest} object that contains the request
     *            the client has made of the servlet
     * @param res
     *            An {@link HttpServletResponse} object that contains the
     *            response the servlet sends to the client
     * @throws ServletException
     *             If the request for the GET could not be handled
     * @throws IOException
     *             If an input or output error is detected when the servlet
     *             handles the GET request
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {}

    /**
     * Try to resolve the post request parameter named <i>Filedata</i> and read
     * the data. That data is interpreted as image data. An {@link UserService}
     * is used to assign the image to an {@link User}. The {@link User}s
     * username must be a part of the request. Append a request parameter with
     * the name <i>selectedUser</i>.
     * 
     * @param req
     *            An {@link HttpServletRequest} object that contains the request
     *            the client has made of the servlet
     * @param res
     *            An {@link HttpServletResponse} object that contains the
     *            response the servlet sends to the client
     * @throws IOException
     *             If an input or output error is detected when the servlet
     *             handles the request
     * @throws ServletException
     *             If the request for the POST could not be handled
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse),
     *      org.openwms.common.service.UserService
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter objOut = null;
        objOut = res.getWriter();
        res.setContentType("text/html");

        MultipartRequest parser = new ServletMultipartRequest(req, ".", MultipartRequest.MAX_READ_BYTES);
        WebApplicationContext context = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());
        UserService userService = (UserService) context.getBean("userService");
        for (Enumeration e = parser.getFileParameterNames(); e.hasMoreElements();) {
            String name = (String) e.nextElement();
            if ("Filedata".equals(name)) {
                File in = parser.getFile(name);
                byte[] image = new byte[(int) in.length()];
                InputStream fis = new FileInputStream(in);
                fis.read(image);
                fis.close();
                String selectedUser = req.getParameter("selectedUser");
                userService.uploadImageFile(selectedUser, image);
                break;
            }
        }
        objOut.println(parser.getHtmlTable());
        objOut.flush();
    }

}