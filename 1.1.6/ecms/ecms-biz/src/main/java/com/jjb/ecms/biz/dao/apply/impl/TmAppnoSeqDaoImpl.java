package com.jjb.ecms.biz.dao.apply.impl;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.ecms.biz.dao.apply.TmAppnoSeqDao;
import com.jjb.ecms.infrastructure.TmAppnoSeq;
import com.jjb.unicorn.base.dao.impl.AbstractBaseDao;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
  * @Description: 获取申请件编号appNo
  * @author JYData-R&D-Big T.T
  * @date 2016年9月1日 上午10:45:47
  * @version V1.0
 */
@Repository("tmAppnoSeqDao")
public class TmAppnoSeqDaoImpl extends AbstractBaseDao<TmAppnoSeq> implements TmAppnoSeqDao {
	
	private static final String getSeqBySequence = "com.jjb.ecms.biz.ApplyTmAppnoSeqMapper.getSeqBySequence";
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor= {Exception.class})
	@Override
	public synchronized String getAppNo(String org) {
        String appNo = DateUtils.dateToString(new Date(), DateUtils.DAY_YMDH);;
        String zeroAppno =  "0";
		OrganizationContextHolder.setOrg(org);
		TmAppnoSeq seqNo = queryForOne(getSeqBySequence, new HashMap<>());
        TmAppnoSeq tmAppnoSeq = new TmAppnoSeq();
        tmAppnoSeq.setOrg(org);
        tmAppnoSeq.setSeq(seqNo.getSeq());
//        tmAppnoSeq.setJpaVersion(1);
        save(tmAppnoSeq);
        
		String seq = StringUtils.valueOf(tmAppnoSeq.getSeq());
		if (10 == seq.length())
			return appNo+seq;
		
		for (int i = 1; i < (10 - seq.length()); i++) {
			zeroAppno = "0" + zeroAppno;
		}
		
        return appNo+zeroAppno+seq;
	}
		
}