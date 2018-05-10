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
  `rank` INT NOT NULL,
  `moneyAtTheEnd` INT NOT NULL,
  `moneyWin` INT NOT NULL,
  `moneyLost` INT NOT NULL,
  `boughtHouses` INT NOT NULL,
  `examVisits` INT NOT NULL,
  `squaresMoved` INT NOT NULL,
  `startWalker` INT NOT NULL,
  `player_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_score_player_idx` (`player_id` ASC),
  CONSTRAINT `fk_score_player`
    FOREIGN KEY (`player_id`)
    REFERENCES `cheseaux-poly`.`player` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
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
    ON UPDATE CASCADE
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
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `cheseaux-poly`.`price`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cheseaux-poly`.`price` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `rent` INT NOT NULL,
  `priceHouse` INT NULL,
  `priceHotel` INT NULL,
  `hypothec` INT NULL,
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `cheseaux-poly`.`square`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cheseaux-poly`.`square` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `price_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_square_price1_idx` (`price_id` ASC),
  CONSTRAINT `fk_square_price1`
    FOREIGN KEY (`price_id`)
    REFERENCES `cheseaux-poly`.`price` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Set the limits of the game
-- -----------------------------------------------------
INSERT INTO `cheseaux-poly`.`parameter`
(`id`, `minDice`, `maxDice`, `minMoneyAtTheStart`, `maxMoneyAtTheStart`, `randomGameGeneration`)
VALUES (1, 1, 2, 1000, 4000, false);