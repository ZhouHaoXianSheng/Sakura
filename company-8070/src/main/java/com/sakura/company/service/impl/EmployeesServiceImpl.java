package com.sakura.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sakura.company.dao.EmployeesMapper;
import com.sakura.company.model.Employees;
import com.sakura.company.service.EmployeesService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Mr.Zhou
 */
@Service
@CacheConfig(cacheNames = "employees")
public class EmployeesServiceImpl implements EmployeesService {

    /**
     * @Resource和@Autowired的区别：
     * Resource是首先按照名字去匹配，如果没有匹配到，则按照类型去匹配
     * Autowired则是按照类型去匹配
     * 所以 @Resource(name = "redisTemplate")可以注入ListOperations类型成功
     * （Spring在内部做了转化redisTemplate->ListOperations），但是@Autowired不行的原因
     */
    @Resource
    private EmployeesMapper employeesMapper;

    @Autowired
    private RedisTemplate<String, Object> template;
    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    /**
     * 由于redis的键值基本是存储为String，所以上述两个注入就
     * 简化为stringRedisTemplate.opsForList()
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "redisTemplate")
    HashOperations<String, String, Object> hashOperations;


    /**
     * 简单方法示例
     * @return
     */
    @Override
    @Cacheable(key = "#key",condition = "#key.length()>2",unless = "#result==\"yangli\"")
    public List<Employees> listEmployees(String key) {
        System.out.println("数据库方法被调用了");
        List<Employees> employees = employeesMapper.selectByRowBounds(new Employees(), new RowBounds(0, 10));
        return employees;
    }

    /**
     * list读取与写入
     * @return
     */
    @Override
    public List<Employees> employeesListOps() {
        List<Employees> employees = employeesMapper.selectByRowBounds(new Employees(), new RowBounds(0, 10));
        String s = JSON.toJSONString(employees);
        listOps.leftPush("employeesListOps", s);
        String employeeList = listOps.leftPop("employeesListOps");
        List<Employees> list = JSON.parseObject(employeeList, new TypeReference<List<Employees>>() {
        });
        return list;
    }

    /**
     * Hash写入操作
     * @param key
     * @param employees
     */
    @Override
    public void writeHash(String key, Employees employees) {
        //使用Jackson2HashMapper简化Map和实体类的转换
        Jackson2HashMapper mapper = new Jackson2HashMapper(true);
        Map<String, Object> mappedHash = mapper.toHash(employees);

        //StringRedisTemplate使用的是默认的序列化器所以会失败
        //stringRedisTemplate.opsForHash().putAll("test", mappedHash);
        /**
         * 使用自定义的就不会，下面两个操作效果是一样的
         */
        hashOperations.putAll(key, mappedHash);
        template.opsForHash().putAll("test", mappedHash);
    }

    /**
     * Hash读取操作
     * @param key
     * @return
     */
    @Override
    public Employees loadHash(String key) {
        //使用Jackson2HashMapper简化Map和实体类的转换
        Jackson2HashMapper mapper = new Jackson2HashMapper(true);
        Map<String, Object> loadedHash = hashOperations.entries(key);
        Employees employees = (Employees) mapper.fromHash(loadedHash);
        return employees;
    }

    @Override
    public void hyperloglog() {
        HyperLogLogOperations<String, Object> opsForHyperLogLog = template.opsForHyperLogLog();
        for (int i = 0; i < 100000; i++) {
            opsForHyperLogLog.add("test", "user"+i);
        }
        Long total = opsForHyperLogLog.size("test");
        System.out.println(total);
    }


}
