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

import org.openwms.common.service.management.UserService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter objOut = null;
        try {
            objOut = res.getWriter();
            res.setContentType("text/html");
            String selectedUser = request.getParameter("selectedUser");
            System.out.println("Selected User:" + selectedUser);

            MultipartRequest parser = new ServletMultipartRequest(request, ".", MultipartRequest.MAX_READ_BYTES);
            WebApplicationContext context = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(getServletContext());
            UserService userService = (UserService) context.getBean("userService");
            for (Enumeration e = parser.getFileParameterNames(); e.hasMoreElements();) {
                String name = (String) e.nextElement();
                System.out.println("Name:" + name);
                if (name.equals("Filedata")) {
                    File in = null;
                    in = parser.getFile(name);
                    byte[] image = new byte[(int) in.length()];
                    InputStream fis = new FileInputStream(in);
                    fis.read(image);
                    fis.close();
                    System.out.println("File found");
                    userService.uploadImageFile(selectedUser, image);
                }
            }
            objOut.println(parser.getHtmlTable());
        } catch (Exception e) {
            try {
                InputStream in = request.getInputStream();
                while (in.read() != -1) {
                    ;
                }
            } catch (Exception e2) {}
            e.printStackTrace(objOut);
        }
        objOut.flush();
    }

}