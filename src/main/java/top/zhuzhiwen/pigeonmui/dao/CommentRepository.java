package top.zhuzhiwen.pigeonmui.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepository;
import top.zhuzhiwen.pigeonmui.model.Comment;
import top.zhuzhiwen.pigeonmui.model.DTO.UserCommentDto;


public interface CommentRepository extends BaseRepository<Comment,Integer>, JpaSpecificationExecutor<Comment> {


//    (int uid, int cid, String unickname, String cnickname, Date time, String content)
//    @Query("select new top.zhuzhiwen.pigeonmui.model.DTO.UserCommentDto(c.id,c.id,u.nickname,user.nickname,c.time,c.content) from Comment c left join User u on c.pid = ?1 and  c.uid = u.id left join User user on c.cid = user.id")
    @Query("select new top.zhuzhiwen.pigeonmui.model.DTO.UserCommentDto(c.id,c.uid,c.id,u.nickname,cu.nickname,c.time,c.content) from Comment c , User u ,User cu where c.pid = ?1 and c.uid = u.id and c.cid = cu.id")
    Page<UserCommentDto> findByPid(int pid, Pageable pageable);


    int countByPid(int pid);
}
