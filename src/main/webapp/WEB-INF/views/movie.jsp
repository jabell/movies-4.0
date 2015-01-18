<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<body>

<p><a href="/movies">Movies</a></p>

<c:if test="${not empty movie}">
    <h2>Movie number ${movie.getId()}</h2>
    <p>Message: ${fn:escapeXml(movie.getContent())} (<a href="/movies/${movie.getId()}/form">edit</a>)</p>
    <p>By ${movie.getEmail()} on ${movie.getDate()}</p>

    <form:form method="DELETE" action="/movies/${movie.getId()}">
        <p><input type="submit" value="Delete"/></p>
    </form:form>
</c:if>

</body>
</html>
