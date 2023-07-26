package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    //HTTP요청을 통해 매핑된 URL이 호출되면 서블릿 컨테이너는 자동으로 service메서드를 실행함.
    @Override //ctrl+o누르고 자물쇠 잠겨있는 친구 메서드 재정의
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service"); //soutm으로 쉽게 만들수 있다!
        System.out.println("request = " + request); //soutv로 쉽게 만들수 있다!
        System.out.println("response = " + response);

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        //----------------------헤더======================
        response.setContentType("text/plain"); //보내는 방식
        response.setCharacterEncoding("utf-8"); //보내는 인코딩방식 - 이걸 해줘야 한글파라미터도 받을 수 있음
        //--------------------헤더에 들어감--------------------

        //======================바디==========================
        response.getWriter().write("hello " + username); //httpbody에 데이터가 들어감
    }
}
