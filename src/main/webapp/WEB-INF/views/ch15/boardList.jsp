<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table table-sm table-bordered">  
   <tr class="thead-dark">
      <th class="col-sm-2">번호</th>
      <th class="col-sm-5">제목</th>
      <th class="col-sm-3">글쓴이</th>
      <th class="col-sm-2">날짜</th>
   </tr>
       
   <c:forEach var="board" items="${boards}">
    <tr>
       <td>${board.bno}</td>
       <td><a href="boardDetail?bno=${board.bno}">${board.btitle}</a></td>
       <td>${board.mid}</td>
       <td><fmt:formatDate value="${board.bdate}" pattern="yyyy-MM-dd"/></td>
    </tr>
   </c:forEach>
</table>