package com.ddabadi.web;

import com.ddabadi.domain.User;
import com.ddabadi.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by deddy on 4/30/16.
 */

@RestController
@RequestMapping(value = "api/user", produces = "application/json")
public class UserController {

    @Autowired private UserService userService;
    private Logger logger = Logger.getLogger(UserController.class);

    @RequestMapping(value = "hal/{hal}/jumlah/{jumlah}")
    Page<User> getAllPage(@PathVariable("hal")int hal,
                          @PathVariable("jumlah")int jumlah){

        logger.info("get all page");
        return userService.getAllPage("id", hal, jumlah);
    }

    @RequestMapping(value = "/nama/{nama}/hal/{hal}/jumlah/{jumlah}")
    Page<User> getByNamaPage(@PathVariable("nama")String nama,
                             @PathVariable("hal")int hal,
                             @PathVariable("jumlah")int jumlah){

        logger.info("get by nama page");
        return userService.getByNama(nama, hal, jumlah);
    }

    @RequestMapping(value = "/id/{id}")
    User getById(@PathVariable("id")Long id){

        logger.info("get by id page");
        return userService.getById(id);
    }

    @RequestMapping(value = "/nama/{nama}/user")
    Long getByOneByNama(@PathVariable("nama")String nama){

        Long idUser=null;
        logger.info("get one by nama");
        User user= userService.getOneByNama(nama);
        if(user != null){
            idUser = user.getId();
        }
        return idUser;
    }

    @RequestMapping(value = "/nama/{nama}/pass/{pass}")
    boolean getAuthByNamaPass(@PathVariable("nama")String nama,
                            @PathVariable("pass")String pass){

        return userService.getAuthByUserPass(nama, pass);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    User save(@RequestBody User user){

        logger.info("save");
        return userService.save(user);
    }

    @RequestMapping(method = RequestMethod.PUT,
                    consumes = "application/json",
                    value = "id/{id}")
    public User update(@PathVariable("id")Long id,
                       @RequestBody User user){

        logger.info("update");
        return userService.update(id, user);
    }

}
