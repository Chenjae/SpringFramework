<%@ page contentType= "text/html; charset=utf-8"%>

<%
//System.out.println(request.getServletContext().getContextPath());
//System.out.println(application.getContextPath());
response.sendRedirect(application.getContextPath() + "/ch01/content");
%>
