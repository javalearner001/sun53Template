<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
<HEAD>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${pageContext.request.contextPath}/css/Style1.css"
          rel="stylesheet" type="text/css" />
    <script language="javascript"
            src="${pageContext.request.contextPath}/js/public.js"></script>
    <script type="text/javascript">
        function addProduct(){
            window.location.href = "${pageContext.request.contextPath}/admin/product/add.jsp";
        }
    </script>
</HEAD>
<body>
<br>
<form id="Form1" name="Form1"
      action="${pageContext.request.contextPath}/user/list.jsp"
      method="post">
    <table cellSpacing="1" cellPadding="0" width="100%" align="center"
           bgColor="#f5fafe" border="0">
        <TBODY>
        <tr>
            <td class="ta_01" align="center" bgColor="#afd1f3"><strong>操作结果</strong>
            </TD>
        </tr>
        <tr>
            <td class="ta_01" align="right">
                <button type="button" id="add" name="add" value="添加"
                        class="button_add" onclick="addProduct()">
                    &#28155;&#21152;</button>

            </td>
        </tr>
        <tr>
            <h2 style="background-color: #ff5151">
                ${deleteResult}
            </h2>
        </tr>

        </TBODY>
    </table>
</form>
</body>
</HTML>

