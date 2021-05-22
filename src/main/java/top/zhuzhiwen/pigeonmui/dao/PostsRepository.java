package top.zhuzhiwen.pigeonmui.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepository;
import top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto;
import top.zhuzhiwen.pigeonmui.model.Posts;

import java.util.Date;
import java.util.List;

public interface PostsRepository extends BaseRepository<Posts,Integer>, JpaSpecificationExecutor<Posts> {

//    @Query(value="select new com.example.wstest.model.classs.PU(u,p) from users u,posts p where u.uid=p.uid",nativeQuery=true)
//    public List<PU> findByUid();
    // u.nickname,u.authentication,p.uid,p.pid from users u,posts p where u.uid=p.uid",nativeQuery=true



    /*@Query(value="select u from users u,posts p where u.uid=1?",nativeQuery=true)
    public List<String> findNicknameByuid(int uid);*/

//    @Query(value="select u.authentication from users u,posts p where u.uid=1?",nativeQuery=true)
//    public List<String> findAuthenticationByuid(int uid);

    public Posts findByPid(int pid);

    @Query(value="SELECT posts.pid, posts.cid,posts.content, posts.oid, posts.time, users.uid, users.nickname FROM posts , users WHERE posts.uid = users.uid ORDER BY posts.pid ASC",nativeQuery=true)
    public List<String> listUserAndPost();

    @Query(value="SELECT posts.pid,posts.cid,posts.content,posts.oid,posts.time,users.uid,users.authentication,users.information,users.nickname,users.orgid FROM posts ,users WHERE posts.uid=users.uid ORDER BY RAND() LIMIT 3",nativeQuery=true)
    List<String> randUserPost();

//    @Query(value ="select * from posts order by posts.time desc ",nativeQuery=true)
//    Page<Posts> findAllorderby(Pageable pageable);
    @Query("select new top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto(p.pid,p.uid,u.username,u.nickname,p.time,p.oid,p.content,p.imgSrc,u.imgSrc,u.authentication,u.information,p.giveLike) from Posts p left join  User u on p.uid = u.id order by p.time desc")
    Page<UserPostDto> findAllorderby(Pageable pageable);

    @Query("select new top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto(p.pid,p.uid,u.username,u.nickname,p.time,p.oid,p.content,p.imgSrc,u.imgSrc,u.authentication,u.information,p.giveLike) from Posts p , User u , Attention a where a.aid = u.id and u.id = p.uid and a.isattent = true and a.uid = ?1 order by p.time desc")
    Page<UserPostDto> findAllorderbyAttend(int id, Pageable pageable);

    @Query("select new top.zhuzhiwen.pigeonmui.model.DTO.UserPostDto(p.pid,p.uid,u.username,u.nickname,p.time,p.oid,p.content,p.imgSrc,u.imgSrc,u.authentication,u.information,p.giveLike) from Posts p , User u where p.uid = u.id and p.pid = ?1")
    UserPostDto findbyPid(int pid);

    @Query("select sum(p.giveLike) from Posts p where p.uid = ?1")
    int likeHe(int uid);
}
