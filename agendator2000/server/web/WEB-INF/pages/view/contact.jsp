<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<style type="text/css">
    .contact_form {
        width: 500px;
        margin: auto;
    }
    .contact_form input {
        width: 300px;
    }
    .contact_form td:first-child {
        text-align: right;
        padding-right: 10px;
    }
    .contact_form textarea {
        width: 300px;
        height: 175px;
    }
</style>
<script type="application/javascript">
    $(function() {
        var contentLength = 300;
        var subjectLength = 100;
        $('#message').keydown(function() {
            var content = $(this).val();
            if (content.length > contentLength) {
                $(this).val(content.substr(0, contentLength));
            }
            var len = Math.min(contentLength, content.length);
            $('#charactersCount').html(contentLength - len);
        });
        $('#subject').keydown(function() {
            var content = $(this).val();
            if (content.length > subjectLength) {
                $(this).val(content.substr(0, subjectLength));
            }
            var len = Math.min(subjectLength, content.length);
            $('#sCharactersCount').html(subjectLength - len);
        });
    });
</script>

<div class="rounded_corners">
    <h1>Contacto</h1>

    <form role="form" class="contact_form" method="post" action="sendMessage">
        <table>

            <tr>
                <td>
                    <label for="subject">Asunto:</label>
                </td>
                <td>
                    <input name="subject" type="text" class="form-control" id="subject" placeholder="Asunto ..." maxlength="100">
                </td>
            </tr>
            <tr>
                <td></td>
                <td>Quedan <span id="sCharactersCount">100</span> caracteres.</td>
            </tr>

            <tr>
                <td>
                    <label for="message"><span>Mensaje:</span></label>
                </td>
                <td>
                    <textarea name="message" class="form-control" id="message" placeholder="Mensaje ..." maxlength="300"></textarea>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>Quedan <span id="charactersCount">300</span> caracteres.</td>
            </tr>

        </table>

        <button type="submit" class="btn btn-default">Enviar</button>
    </form>




</div>