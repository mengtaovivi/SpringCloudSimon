package com.cloud.mt.base.page;

import com.cloud.mt.base.util.ReflectionUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public class JpaPageHelper {

	/**
	 * @param jpa             持久化对象
	 * @param queryMethodName 查询分页方法
	 * @param pageRequest     查询参数
	 * @param args            参数名称数组，一定要按查询方法的参数顺利提供，且参数元素名称要与pageRequest中columnFilters对象中的键保持一致
	 * @return
	 */
	public static PageResult findPage(Object jpa, String queryMethodName, PageRequest pageRequest, String... args) {
		Pageable pageable = getPageable(pageRequest);
		Object[] params = null;
		Map<String, Object> data = pageRequest.getData();
		if (args != null && args.length > 0) {
			params = new Object[args.length + 1];
			int index = 0;
			for (String param : args) {
				if (!data.isEmpty()) {
					params[index++] = data.get(param);
				}
			}
		} else {
			params = new Object[1];
		}
		params[params.length - 1] = pageable;
		// 利用反射调用查询方法
		Object result = ReflectionUtil.invoke(jpa, queryMethodName, params);
		return getPageResult((Page<?>) result);
	}

	/*封装JPA分页对象*/
	public static Pageable getPageable(PageRequest pageRequest) {
		Pageable pageable = org.springframework.data.domain.PageRequest.of(pageRequest.getPageNum() - 1, pageRequest.getPageSize());
		return pageable;
	}

	public static PageResult getPageResult(Page<?> pageInfo) {
		if (pageInfo == null) {
			return null;
		}
		PageResult pageResult = new PageResult();
		pageResult.setPageNum(pageInfo.getNumber());
		pageResult.setPageSize(pageInfo.getSize());
		pageResult.setTotalSize(pageInfo.getTotalElements());
		pageResult.setTotalPages(pageInfo.getTotalPages());
		pageResult.setContent(pageInfo.getContent());
		return pageResult;
	}


	/**
	 * 如果页码大于最后一页，返回最后一页
	 *
	 * @param pageRequest
	 * @param totalNum
	 * @return
	 */
	public static PageRequest fixLastPage(PageRequest pageRequest, int totalNum) {
		//假如显示的数据超过总数据就返回最后一页的数据
		if ((pageRequest.getPageNum() - 1) * pageRequest.getPageSize() >= totalNum) {
			//数据库总数量小于或者等于一页显示的数据，就返回第一页的数据
			if (totalNum <= pageRequest.getPageSize()) {
				pageRequest.setPageNum(1);
			} else {
				//数据库总数量大于一页显示的数据
				int lastPageNum = totalNum / pageRequest.getPageSize();
				pageRequest.setPageNum(lastPageNum);
			}
		}
		return pageRequest;
	}

}
