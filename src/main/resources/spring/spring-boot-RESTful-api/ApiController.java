import cn.lanqiao.springboot3.common.Result;
import cn.lanqiao.springboot3.common.ResultGenerator;
import cn.lanqiao.springboot3.dao.UserDao;
import cn.lanqiao.springboot3.entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/apis")
public class ApiController {

    @Resource
    UserDao userDao;

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result<User> getOne(@PathVariable("id") Integer id) {
        if (id == null || id < 1) {
            return ResultGenerator.genFailResult("????");
        }
        User user = userDao.getUserById(id);
        if (user == null) {
            return ResultGenerator.genFailResult("????");
        }
        return ResultGenerator.genSuccessResult(user);
    }


    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<User>> queryAll() {
        List<User> users = userDao.findAllUsers();
        return ResultGenerator.genSuccessResult(users);
    }


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> insert(@RequestBody User user) {

        if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
            return ResultGenerator.genFailResult("????");
        }
        return ResultGenerator.genSuccessResult(userDao.insertUser(user) > 0);
    }


    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @ResponseBody
    public Result<Boolean> update(@RequestBody User tempUser) {

        if (tempUser.getId() == null || tempUser.getId() < 1 || StringUtils.isEmpty(tempUser.getName()) || StringUtils.isEmpty(tempUser.getPassword())) {
            return ResultGenerator.genFailResult("????");
        }

        User user = userDao.getUserById(tempUser.getId());
        if (user == null) {
            return ResultGenerator.genFailResult("????");
        }
        user.setName(tempUser.getName());
        user.setPassword(tempUser.getPassword());
        return ResultGenerator.genSuccessResult(userDao.updUser(user) > 0);
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Boolean> delete(@PathVariable("id") Integer id) {
        if (id == null || id < 1) {
            return ResultGenerator.genFailResult("????");
        }
        return ResultGenerator.genSuccessResult(userDao.delUser(id) > 0);
    }
}