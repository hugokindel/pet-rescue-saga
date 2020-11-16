package com.g10.prs.level;

/** Cell that represents an animal. */
public class Animal extends Cell {
    /** The type of animal. */
    private AnimalType animalType;

    /**
     * Class constructor.
     *
     * @param animalType The type of animal.
     */
    public Animal(AnimalType animalType) {
        super(CellType.Animal);
        this.animalType = animalType;
    }

    /** @return the type of animal. */
    public AnimalType getAnimalType() {
        return animalType;
    }
}
