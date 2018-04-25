-- -----------------------------------------------------
-- Schema cheseaux-poly
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `cheseaux-poly`;
CREATE SCHEMA `cheseaux-poly` DEFAULT CHARACTER SET utf8;
USE `cheseaux-poly` ;

-- -----------------------------------------------------
-- Table `cheseaux-poly`.`player`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cheseaux-poly`.`player` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `cheseaux-poly`.`score`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cheseaux-poly`.`score` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `startDate` DATETIME NOT NULL,
  `startEnd` DATETIME NOT NULL,
  `abort` TINYINT NOT NULL,
  `moneyAtTheEnd` INT NOT NULL,
  `boughtHouses` INT NOT NULL,
  `examVisits` INT NOT NULL,
  `squaresMoved` INT NOT NULL,
  `player_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_score_player_idx` (`player_id` ASC),
  CONSTRAINT `fk_score_player`
    FOREIGN KEY (`player_id`)
    REFERENCES `cheseaux-poly`.`player` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `cheseaux-poly`.`action`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cheseaux-poly`.`action` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `cheseaux-poly`.`card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cheseaux-poly`.`card` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(250) NOT NULL,
  `action_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_card_action1_idx` (`action_id` ASC),
  CONSTRAINT `fk_card_action1`
    FOREIGN KEY (`action_id`)
    REFERENCES `cheseaux-poly`.`action` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- -----------------------------------------------------
-- Table `cheseaux-poly`.`parameter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cheseaux-poly`.`parameter` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `minDice` INT NOT NULL,
  `maxDice` INT NOT NULL,
  `minMoneyAtTheStart` INT NOT NULL,
  `maxMoneyAtTheStart` INT NOT NULL,
  `randomGameGeneration` TINYINT NOT NULL,
  `examSquares` INT NOT NULL,
  PRIMARY KEY (`id`)
);