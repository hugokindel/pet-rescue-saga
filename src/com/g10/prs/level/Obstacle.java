package com.g10.prs.level;

/** Cell that represents an obstacle. */
public class Obstacle extends Cell {
    /** The type of obstacle. */
    private final ObstacleType obstacleType;

    /**
     * Class constructor.
     *
     * @param obstacleType The type of obstacle.
     */
    public Obstacle(ObstacleType obstacleType) {
        super(CellType.Obstacle);
        this.obstacleType = obstacleType;
    }

    /** @return the type of obstacle. */
    public ObstacleType getObstacleType() {
        return obstacleType;
    }

    public Obstacle copy(){
        return new Obstacle(obstacleType);
    }
}
