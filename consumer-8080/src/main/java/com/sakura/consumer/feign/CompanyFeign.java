package com.sakura.consumer.feign;

import com.sakura.consumer.vo.EmployeesVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Mr.Zhou
 */
@FeignClient("company")
public interface CompanyFeign {
    /**
     * 查询十条员工记录
     * @return
     */
    @GetMapping("/employees")
    List<EmployeesVo> employeesList();
}
