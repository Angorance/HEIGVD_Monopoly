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
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
);

-- -----------------------------------------------------
-- Table `cheseaux-poly`.`card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cheseaux-poly`.`card` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(250) NOT NULL,
  `quantity` INT NOT NULL,
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
  `minTime` INT NOT NULL,
  `maxTime` INT NOT NULL,
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
  `position` INT NOT NULL,
  `family` VARCHAR(50) NULL,
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
(`id`, `minDice`, `maxDice`, `minMoneyAtTheStart`, `maxMoneyAtTheStart`, `minTime`, `maxTime`, `randomGameGeneration`)
VALUES (1, 2, 4, 1000, 4000, 30, 120, false);

-- -----------------------------------------------------
-- Set the actions of the cards
-- -----------------------------------------------------
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (1, "MOVE");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (2, "EXAM");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (3, "WIN");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (4, "LOSE");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (5, "GOTO");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (6, "CARD");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (7, "FREE");

-- -----------------------------------------------------
-- Create the cards
-- -----------------------------------------------------
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (1, "Félicitation, vous avez réussi tous les challenges de RES.", 1, 1);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (2, "Fantastique, vous avez obtenu plus que 5 au dernier labo de POO.", 1, 1);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (3, "Vous n'avez pas rendu à temps votre dernier labo de PCO.", 1, 2);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (4, "Vous avez oublié d'acheter votre billet de parking.", 1, 2);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (5, "Merci d'avoir rempli toutes les évaluations d'enseignants.", 1, 3);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (6, "Vous avez été élu membre du CoRe.", 1, 3);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (7, "Il se trouve que vous vous êtes planté dans la partie assembleur de votre test de SLO.", 1, 4);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (8, "En rendant votre rapport de PRO, vous vous êtes trompé de casier.", 1, 4);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (9, "Aller, qu'est-ce que tu attends ? C'est l'heure d'aller manger !", 1, 5);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (10, "Fin des cours, tu mérites ta pause.", 1, 5);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (11, "Vous avez été injustement pénalisé à votre dernier examen. Le professeur s'en excuse.", 1, 6);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (12, "Grâce au coup de pouce d'un enseignant, vous passez finalement votre branche.", 1, 6);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (13, "Le temps est écoulé ! Vous pouvez sortir de la salle d'examen.", 2, 7);