package com.g10.prs.level;

public class Obstacle extends Cell {
    private ObstacleType obstacleType;

    public Obstacle(ObstacleType obstacleType) {
        super(CellType.Obstacle);
        this.obstacleType = obstacleType;
    }

    public ObstacleType getObstacleType() {
        return obstacleType;
    }
}
