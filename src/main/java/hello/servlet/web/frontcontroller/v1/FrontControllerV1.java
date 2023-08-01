package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerV1 extends HttpServlet {

    //매핑을 위한 정보 , url과 ControllerV1을 넣을것. 즉 key가 url이라서 이 url이면 이 컨트롤러 호출 이런방식
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerV1.service");

        String requestURI = request.getRequestURI();
        //부모는 자식을 받을 수 있다.(다형성. ControllerV1이 인터페이스로 부모. controller는 걔를 구현한 자식)
        ControllerV1 controller = controllerMap.get(requestURI);

        //예외처리
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //헐.. 여기서 사용하는거였어. ControllerV1을 인터페이스로 만든 이유!
        controller.process(request, response);
    }
}
