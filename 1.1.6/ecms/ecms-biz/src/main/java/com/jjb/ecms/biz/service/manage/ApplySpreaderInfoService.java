package com.jjb.ecms.biz.service.manage;

import java.util.List;

import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;

/**
 * @description 推广人信息
 * @author J.J
 * @date 2017年7月7日下午1:54:13
 */
public interface ApplySpreaderInfoService {

	/**
	 * 获取推广人信息
	 * 
	 * @param page
	 * @return
	 */
	public Page<TmAppSprePerBank> getPage(Page<TmAppSprePerBank> page, TmAppSprePerBank spreader);

	/**
	 * 获取推广人信息
	 * @param page
	 * @return
	 */
	public Page<TmAppSprePerBank> getPageForTranferUser(Page<TmAppSprePerBank> page);
	/**
	 * 根据参数获取推广人信息
	 * 
	 * @param TmAppSprePerBank
	 * @return List
	 */
	public List<TmAppSprePerBank> getSpreaderByParam(
			TmAppSprePerBank spreader) throws ProcessException;

	/**
	 * 保存推广人信息
	 * 
	 * @param TmAppSprePerBank
	 * @return
	 */
	public void saveSpreader(TmAppSprePerBank spreader) throws ProcessException;

	/**
	 * 根据ID获取推广人信息
	 * 
	 * @param id
	 * @return TmAppSprePerBank
	 */
	public TmAppSprePerBank getSpreaderById(int id);

	/**
	 * 更新推广人信息
	 * 
	 * @param TmAppSprePerBank
	 * @return
	 */
	public void updateSpreader(TmAppSprePerBank spreader) throws ProcessException;

	/**
	 * 删除推广人信息
	 * 
	 * @param id
	 * @return
	 */
	public void deleteSpreaderById(Integer id);
}
