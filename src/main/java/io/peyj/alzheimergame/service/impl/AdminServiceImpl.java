package io.peyj.alzheimergame.service.impl;

import io.peyj.alzheimergame.entity.GameLevel;
import io.peyj.alzheimergame.entity.GameStage;
import io.peyj.alzheimergame.repository.GameLevelRepository;
import io.peyj.alzheimergame.repository.GameStageRepository;
import io.peyj.alzheimergame.service.AdminService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private GameLevelRepository gameLevelRepository;
    private GameStageRepository gameStageRepository;

    public AdminServiceImpl(GameLevelRepository gameLevelRepository, GameStageRepository gameStageRepository) {
        this.gameLevelRepository = gameLevelRepository;
        this.gameStageRepository = gameStageRepository;
    }

    @Override
    public GameLevel createNewLevel(GameLevel basicLevelInfo) {
        return gameLevelRepository.saveAndFlush(basicLevelInfo);
    }

    @Transactional
    @Override
    public GameLevel updateLevel(GameLevel updatedLevelData) {
        List<GameStage> stages = updatedLevelData.getStages();
        for (GameStage stage : stages) {
            stage.setLevel(updatedLevelData);
        }
        gameStageRepository.saveAll(stages);
        return gameLevelRepository.saveAndFlush(updatedLevelData);
    }
}
