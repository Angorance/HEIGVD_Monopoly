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
  `price` INT NULL,
  `priceCouch` INT NULL,
  `priceHomeCinema` INT NULL,
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
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (1, "WIN 10");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (2, "WIN 20");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (3, "WIN 25");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (4, "WIN 50");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (5, "WIN 100");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (6, "WIN 200");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (7, "LOSE 15");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (8, "LOSE 20");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (9, "LOSE 50");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (10, "LOSE 100");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (11, "LOSE 150");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (12, "FREE");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (13, "GOTO 0");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (14, "GOTO 1");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (15, "GOTO 10");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (16, "GOTO 16");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (17, "GOTO 39");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (18, "BACK 3");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (19, "MOVE 7");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (20, "MOVE 11");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (21, "EACH 10");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (22, "CHOICE 10 CARD");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (23, "REP 40 115");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (24, "REP 25 100");
INSERT INTO `cheseaux-poly`.`action` (`id`, `type`) VALUES (25, "CARD");


-- -----------------------------------------------------
-- Create the cards
-- -----------------------------------------------------
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (1, "Le secrétariat s'est trompé et te reverse 10.-", 1, 1);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (2, "La machine à café a planté. Tu gagnes 20.-", 1, 2);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (3, "Ton acharnement te rapport 25.-", 1, 3);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (4, "En te baladant dans les couloirs, tu aperçois un billet de 50.-", 1, 4);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (5, "Tu aides un pote avec son labo. Cela te rapporte 50.-", 1, 4);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (6, "Reçois 100.- pour avoir gagné le concours d'innovation", 1, 5);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (7, "Tu obtiens ton bachelor et gagnes 100.-", 1, 5);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (8, "Tu reçois les subsides du canton. Tu gagnes 100.-", 1, 5);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (9, "Tu as obtenu la bourse : 200.-", 1, 6);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (10, "Tu as cassé ton badge d'imprimante. Tu paies 15.-", 1, 7);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (11, "Tu as été pris en train de plagier… Tu paies 20.-", 1, 8);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (12, "Le menu de la cantine était spécial, tu as payé 50.-", 1, 9);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (13, "L'impression de ton rapport te coûte 50.-", 1, 9);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (14, "Tu n'as pas payé le parking et tu reçois une amende de 100.-", 1, 10);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (15, "Paie tes frais d'écolage : 150.-", 1, 11);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (16, "A force de venir en voiture, faut payer l'essence : 150.-", 1, 11);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (17, "Rends ta copie d'examen et sors de la salle", 2, 12);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (18, "Vas à la case de départ", 2, 13);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (19, "Tu as oublié ta veste en F01, tu y retournes pour la récupérer", 1, 14);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (20, "L'examen commence…", 2, 15);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (21, "Tu dois aller discuter avec un prof. Vas en reprographie", 1, 16);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (22, "Rends-toi immédiatement au Chill Out", 1, 17);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (23, "Tu as oublié ton chargeur. Recule de 3 cases", 1, 18);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (24, "Un de tes cours est annulé. Avance de 7 cases", 1, 19);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (25, "Tu t'es perdu dans l'école et tu te retrouves 11 cases plus loin", 1, 20);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (26, "Ton anniv', ça fait toujours plaisir. Tous les joueurs te donnent 10.-", 1, 21);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (27, "Vraiment pas de bol. Paie 10.- ou retire une carte", 1, 22);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (28, "Répare tes possessions : 40.-/canapé, 115.-/home cinéma", 1, 23);
INSERT INTO `cheseaux-poly`.`card` (`id`, `text`, `quantity`, `action_id`) VALUES (29, "Des andouilles ont saccagé tes possessions. 25.-/canapé, 100.-/home cinéma", 1, 24);


-- -----------------------------------------------------
-- Create the prices
-- -----------------------------------------------------
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 1, 2, 60, 50, 50, 30);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 2, 4, 60, 50, 50, 30);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 3, 6, 100, 50, 50, 50);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 4, 8, 120, 50, 50, 60);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 5, 10, 140, 100, 100, 70);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 6, 12, 160, 100, 100, 80);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 7, 14, 180, 100, 100, 90);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 8, 16, 200, 100, 100, 100);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 9, 18, 200, 150, 150, 110);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 10, 20, 240, 150, 150, 120);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 11, 22, 260, 150, 150, 130);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 12, 24, 280, 150, 150, 140);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 13, 26, 300, 200, 200, 150);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 14, 28, 320, 200, 200, 160);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 15, 35, 350, 200, 200, 175);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 16, 50, 400, 200, 200, 200);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 17, 25, 200, NULL, NULL, 100);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 18, 200, NULL, NULL, NULL, NULL);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 19, 4, 150, NULL, NULL, 75);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 20, 50, NULL, NULL, NULL, NULL);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 21, 18, 220, 150, 150, 110);
INSERT INTO `cheseaux-poly`.`price` (`id`, `rent`, `price`, `priceCouch`, `priceHomeCinema`, `hypothec`) VALUES ( 22, 100, NULL, NULL, NULL, NULL);


-- -----------------------------------------------------
-- Create the squares
-- -----------------------------------------------------
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (1, 0, "START", "Départ", NULL);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (2, 1, "BROWN", "F01", 1);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (3, 2, "CARD", "Carte chance", NULL);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (4, 3, "BROWN", "A01", 2);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (5, 4, "TAX", "Frais d'écolage", 18);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (6, 5, "INSTITUTE", "REDS", 17);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (7, 6, "CYAN", "Zone casier", 3);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (8, 7, "CARD", "Carte chance", NULL);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (9, 8, "CYAN", "Table de ping pong", 3);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (10, 9, "CYAN", "Micro-onde", 4);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (11, 10, "EXAM", "Salle d'examen", 20);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (12, 11, "PINK", "Secrétariat", 5);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (13, 12, "COMPANY", "Selecta", 19);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (14, 13, "PINK", "Biblio", 5);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (15, 14, "PINK", "FabLab", 6);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (16, 15, "INSTITUTE", "SIM", 17);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (17, 16, "ORANGE", "Reprographie", 7);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (18, 17, "CARD", "Carte chance", NULL);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (19, 18, "ORANGE", "Gaps", 7);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (20, 19, "ORANGE", "AGE", 8);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (21, 20, "BREAK", "Terrasse", NULL);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (22, 21, "RED", "Palmeraie", 9);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (23, 22, "CARD", "Carte chance", NULL);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (24, 23, "RED", "Orangeraie", 21);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (25, 24, "RED", "Service informatique", 10);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (26, 25, "INSTITUTE", "MNT", 17);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (27, 26, "YELLOW", "C23", 11);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (28, 27, "YELLOW", "B23", 11);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (29, 28, "COMPANY", "Imprimante", 19);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (30, 29, "YELLOW", "Aula", 12);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (31, 30, "GO_EXAM", "Allez en prison!", NULL);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (32, 31, "GREEN", "J01", 13);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (33, 32, "GREEN", "H01", 13);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (34, 33, "CARD", "Carte chance", NULL);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (35, 34, "GREEN", "G01", 14);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (36, 35, "INSTITUTE", "IICT", 17);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (37, 36, "CARD", "Carte chance", NULL);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (38, 37, "BLUE", "Parking", 15);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (39, 38, "TAX", "Baleinev", 22);
INSERT INTO `cheseaux-poly`.`square` (`id`, `position`, `family`, `name`, `price_id`) VALUES (40, 39, "BLUE", "Chill out", 16);