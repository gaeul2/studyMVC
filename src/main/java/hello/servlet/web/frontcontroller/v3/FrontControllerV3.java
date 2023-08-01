package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerV3 extends HttpServlet {

    //매핑을 위한 정보 , url과 ControllerV1을 넣을것. 즉 key가 url이라서 이 url이면 이 컨트롤러 호출 이런방식
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        //부모는 자식을 받을 수 있다.(다형성. Controllerv3이 인터페이스로 부모. controller는 걔를 구현한 자식)
        ControllerV3 controller = controllerMap.get(requestURI);

        //예외처리
        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap을 넘겨줘야함.
        //그러기 위해서 request에서 파라미터를 다 꺼내야함.

        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = controller.process(paramMap);
        mv.getViewName(); //논리이름 new-form
        //뷰리졸버 부터 실습.
//        mv.getModel().
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
