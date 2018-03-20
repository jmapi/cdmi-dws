package pw.cdmi.paas.developer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pw.cdmi.paas.developer.model.entities.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, String>{
	/**
	 * 查询
	 * @param userid
	 * @return
	 */
	public List<Voucher> findByUserId(String userid);
	public Voucher findById(String id);
	/**
	 * 根据删除
	 * @param id
	 */
	
	public void deleteById(String id);
	
}
