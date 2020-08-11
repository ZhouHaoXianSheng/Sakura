package com.sakura.consumer.vo;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author Mr.Zhou
 */
@Data
public class EmployeesVo {
    private Integer empNo;

    private Date birthDate;

    private String firstName;

    private String lastName;

    private Object gender;

    private Date hireDate;
}
