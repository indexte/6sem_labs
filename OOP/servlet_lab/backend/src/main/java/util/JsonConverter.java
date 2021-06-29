package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonConverter {

    private static final Logger logger = LogManager.getRootLogger();

    public static <T> void makeJsonAnswer(T obj, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        PrintWriter out = response.getWriter();
        out.print(new ObjectMapper().writeValueAsString(obj));
        out.flush();
    }

    public static JSONObject jsonBodyFromRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer jsonBody = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        } catch (Exception e) {
            logger.error("Cannot get json obj: {}", e.getMessage());
            response.sendError(400, "Bad request");
        }
        return new JSONObject(jsonBody.toString());
    }
}
