# Monopoly

## Contributeurs

* **Bryan Curchod** - *Server-side* - [BryCur](https://github.com/BryCur)
* **Daniel Gonzalez Lopez** - *Client-side / Server-side* - [Angorance](https://github.com/Angorance)
* **François Burgener** - *Frontend* - [francoisburgener](https://github.com/francoisburgener)
* **Héléna Reymond** - *Client-side / Database* -[LNAline](https://github.com/LNAline)

## Description 
Dans le cadre du module de Génie Logiciel (GEN), il nous a été demandé de développer un "mini-projet"
implémentant une architecture client-serveur pouvant communiquer par le réseau. Après discussion nous
sommes tombés d'accord sur l'idée d'un Monopoly sur le thème de la HEIG-VD.

Le client peut s'enregistrer et se connecter à l'aide d'un nom d'utilisateur et un mot de passe. Une fois
authentifié, il a accès à la liste des parties en attente de joueurs. D'ici il peut rejoindre un "salon" de jeu
ou en créer un. Une fois qu'il y a assez de joueurs dans le salon, et que ces derniers sont tous prêt, la partie
commence.

Le serveur, lui, s'occupe de la gestion de ses clients lorsqu'ils ne sont pas en jeu, et de la gestion des parties
en cours. C'est à dire que toute la logique du jeu, et l'évolution de la partie sont implémentées du côté
serveur. Les clients se contentent d'interpréter les informations reçues et d'afficher l'état de la partie.

Derrière le serveur nous utilisons une base de données afin de pouvoir enregistrer les joueurs, fixer des
limites de jeu (tel que le nombre de dés, ou encore le capital de départ), définir les objets inhérents au jeu
(comme les cases et les cartes), et enfin nous avons également pensé à un suivi des statistiques de jeu
pour les joueurs.

## Technologies utilisées
- Langage utilisé : **Java**
- Interface Utilisateur : **JavaFX**
- Base de données : **MySQL**
- Planification et organisation : **IceScrum**
- Gestion des dépendance : **Maven**

## Maquettes
![Liste des salons de jeu](/Doc/Maquette/salondejeu.PNG)
![Plateau de jeu](/Doc/Maquette/jeu.PNG)
