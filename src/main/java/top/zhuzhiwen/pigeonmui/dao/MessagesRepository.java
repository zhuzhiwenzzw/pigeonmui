package top.zhuzhiwen.pigeonmui.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepository;
import top.zhuzhiwen.pigeonmui.model.Messages;

import java.util.List;

public interface MessagesRepository  extends BaseRepository<Messages,Integer>, JpaSpecificationExecutor<Messages> {


//    SELECT user_id, to_id, content, type, `status`, add_time FROM hengtu_wxapp_im WHERE ( user_id = %d AND to_id = %d ) OR ( user_id = %d AND to_id = %d ) ORDER BY add_time ASC
    @Query("select m from Messages m where (m.uid = ?1 and m.aid = ?2) or (m.aid = ?1 and m.uid = ?2) order by m.sendTime desc ")
    Page<Messages> findByUidAndAid(int id, int uid, Pageable pageable);
}
