package pw.cdmi.paas.developer.service;

import java.util.List;

import pw.cdmi.paas.developer.model.entities.Voucher;
import pw.cdmi.paas.developer.rs.VoucherRequest;
public interface VoucherDetailService {
	/**
	 * 生成凭证
	 * @param vocher
	 * @return
	 * @throws Exception 
	 */
	public VoucherRequest voucherObject(String userId) throws Exception;
	/**
	 * 查询该用户所有凭证
	 * @param userId
	 * @return
	 */
	public List<VoucherRequest> findVoucher(String userId);
	/**
	 * 重新生成凭证
	 * @param id
	 * @return
	 */
	public VoucherRequest upDateVoucher(String id);
	/**
	 * 删除指定凭证
	 * @param id
	 */
	public void deleteVoucher(String id);
}
