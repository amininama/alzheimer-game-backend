package io.peyj.alzheimergame.controller;

import io.peyj.alzheimergame.dto.NewGameLevelRequest;
import io.peyj.alzheimergame.entity.GameLevel;
import io.peyj.alzheimergame.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/admin/game/level/add")
    public ResponseEntity<GameLevel> addNewLevel(@RequestBody @Valid NewGameLevelRequest request){
        GameLevel basicLevelInfo = new GameLevel();
        basicLevelInfo.setTitle(request.getTitle());
        GameLevel newLevel = adminService.createNewLevel(basicLevelInfo);
        return ResponseEntity.ok(newLevel);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/admin/game/level/update")
    public ResponseEntity<GameLevel> updateLevel(GameLevel updatedInfo){

        GameLevel updated = adminService.updateLevel(updatedInfo);
        return ResponseEntity.ok(updated);
    }
}
