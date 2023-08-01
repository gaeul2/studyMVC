package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerV2 extends HttpServlet {

    //매핑을 위한 정보 , url과 ControllerV1을 넣을것. 즉 key가 url이라서 이 url이면 이 컨트롤러 호출 이런방식
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        //부모는 자식을 받을 수 있다.(다형성. Controllerv2이 인터페이스로 부모. controller는 걔를 구현한 자식)
        ControllerV2 controller = controllerMap.get(requestURI);

        //예외처리
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //헐.. 여기서 사용하는거였어. Controllerv2을 인터페이스로 만든 이유!
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
