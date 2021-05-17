package top.zhuzhiwen.pigeonmui.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import top.zhuzhiwen.pigeonmui.dao.basedao.BaseRepository;
import top.zhuzhiwen.pigeonmui.model.Attention;

public interface AttentionRepository extends BaseRepository<Attention,Integer>, JpaSpecificationExecutor<Attention> {


    @Query("select a from Attention a where a.uid = ?1 and a.aid = ?2")
    Attention findByUA(int id, int uid);


    @Modifying
    @Transactional(readOnly = false)
    @Query("delete from Attention a where a.uid = ?1 and a.aid = ?2")
    void delete(int id, int uid);

    @Modifying
    @Transactional(readOnly = false)
    @Query("update Attention a set a.display = false where a.uid = ?1 and a.aid = ?2")
    void updateDisplay(int id, int uid);

    @Modifying
    @Transactional(readOnly = false)
    @Query("update Attention a set a.display = true where a.uid = ?1 and a.aid = ?2")
    void updateDisplayTrue(int id, int uid);
}
