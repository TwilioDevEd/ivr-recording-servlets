<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>IVR Recording</title>
    <link href="css/site.css" rel="stylesheet">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
    <nav class="navbar navbar-default">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">IVR Screening</a>
        </div>
    </nav>
    <div class="container">
        <h2>Agents and Recordings</h2>
        <core:forEach var="agent" items="${agents}">
            <h2>${agent.extension}</h2>

            <core:choose>
                <core:when test="${fn:length(agent.recordings) > 0}">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Caller number</th>
                                <th>Transcription</th>
                                <th>Recording</th>
                            </tr>
                        </thead>
                        <tbody>
                            <core:forEach var="recording" items="${agent.recordings}">
                                <tr>
                                    <td>${recording.phoneNumber}</td>
                                    <td>${recording.transcription}</td>
                                    <td>
                                        <audio controls src="${recording.url}.mp3">
                                        This browser doesn't support the audio element.
                                        </audio>
                                    </td>
                                </tr>
                            </core:forEach>
                        </tbody>
                    </table>
                </core:when>
                <core:otherwise>
                    <p>No recordings</p>
                </core:otherwise>
            </core:choose>
        </core:forEach>
    </div>

    <footer class="container">
        <hr/>
        Made with <i class="fa fa-heart"></i> by your pals
        <a href="http://www.twilio.com">@twilio</a>
    </footer>

    <script type="javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>