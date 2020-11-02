package com.g10.prs.level;

public class Animal extends Cell {
    private AnimalType animalType;

    public Animal(AnimalType animalType) {
        super(CellType.Animal);
        this.animalType = animalType;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }
}
