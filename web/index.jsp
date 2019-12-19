<%--
  Created by IntelliJ IDEA.
  User: jerry
  Date: 2019/12/18
  Time: 11:36 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>分页显示</title>
  </head>
  <body>
<%--如果是第一次请求,没有数据则自动请求Servlet获取数据 注意如果对于的页码没有数据了也不能返回NULL会造成死循环--%>
  <c:if test="${foods==null}">
    <jsp:forward page="/getFoods"></jsp:forward>
  </c:if>

  <table border="1">
    <tr>
      <th>编号</th>
      <th>名称</th>
      <th>单价</th>
      <th>单位</th>
    </tr>
<%--    遍历列表展示数据--%>
    <c:forEach items="${foods}" var="f">
      <tr>
        <td>${f.id}</td>
        <td>${f.name}</td>
        <td>${f.price}</td>
        <td>${f.unit}</td>
      </tr>
    </c:forEach>
  </table>
<%--    拼接参数请求新的数据--%>
  <a href="/PagingShow/getFoods?page=1&count=${count}">首页</a>
  <a href="/PagingShow/getFoods?page=${page-1>0?page-1:1}&count=${count}">上一页</a>
  <a>${page}/${ttPage}</a>
  <a href="/PagingShow/getFoods?page=${page+1<ttPage?page+1:ttPage}&count=${count}">下一页</a>
  <a href="/PagingShow/getFoods?page=${ttPage}&count=${count}">末页</a>
  </body>
</html>
