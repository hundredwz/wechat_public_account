package wechat;

import org.apache.commons.codec.digest.DigestUtils;
import service.CoreService;
import util.SignUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by wzhuo on 2015/10/31.
 */
public class ServletMain extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String respMessage = CoreService.processRequest(request);

        PrintWriter out = response.getWriter();
        out.println(respMessage);
        out.print("感谢使用");
        out.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            pw.print(echostr);
        }
        pw.flush();
        pw.close();
    }


}
