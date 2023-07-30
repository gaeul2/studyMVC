package hello.servlet.domain.member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용고려
 * */
@Repository
public class MemberRepository {
    //key는 id, value는 Member
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;


    //싱글톤으로 생성 - 톰캣띄울때만 생성
    //따라서 생성자를 private로 막음
    private static final MemberRepository instance = new MemberRepository();

    //무조건 얘로 조회하도록
    public static MemberRepository getInstance(){
        return instance;
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        //store에 있는 모든 value들을 꺼내서 arrayList에 넣어줌.
        //왜 이렇게 했냐면 store자체를 보호하기 위함.
        //store에 바로 접근하면 값을 변경할 까봐..! 근데 사실 ArrayList에서도 값변경하고자하면 값 변경할 수 있음.
        return new ArrayList<>(store.values());
    }

    //test용임
    public void clearStore(){
        store.clear();
    }

    private MemberRepository(){

    }
}
