# Projet-POGL
#L'île interdite.
Notre projet consiste à réaliser le jeu de société L'île interdite qui globalement est un jeu de société où il y a 2 à 4 joueurs qui se retrouvent dans une île qui se faite engloutîe petit à petit par des inondations, ils doivent recupérer des artefacts et s'échaper de l'île avant que l'île soit immérgée.

On devait l'implémenter en Java, donc dans les 3 parties qui vont suivre, on détaillera qu'est ce qu'on a réalisé et comment on a réalisé.

## 1 - Répartition des tâches et directive du projet.
Les parties qu'on a pu réaliser:
.- Le View du jeu
.- Le déplacement des personnages
.- Les tours du jeu
.- Les actions(comme déplacer un autre joueur, donner une carte, Assécher une zone, ...)
.- Le niveau d'eau.

Comment on s'est réparti ? :
.-globalement Lia se charge de faire la logique du jeu et Mamisoa se charge de raccorder la logique, les contrôles et l'affichage. Pendant l'implémentation du jeu on était côte à côte, donc on s'est entrâidé mutuellement quand on était coincé sur un bug.

.-Lia a fait la plupart des méthodes dans les classes Zone et Ile
.-Mamisoa a fait la plupart des méthodes dans Vue et FenetreJeu
.-Sinon on a compléter globalement ensemble les autres Classes ensemble.

.- Pour s'échanger nos travaux on a utilisé les commandes git grâce à gitlab comme indiqué dans la fiche du projet. 

## 2 - L'organisation du code.

.- On a pas forcément penser à mettre la vue et les contrôles dans des fichers différents .
.- On a fait la classe Vue qui permet de contrôler l'affichage de l'île avec les Zones et les personnages ainsi que les cartes.
.- On a choisi de mettre le contrôle des joueurs et du jeu en général dans le terminal donc ce n'était pas forcément nécessaire de créer des classes supplémentaire pour ça, tout cela se passe déjà dans la classe Jeu.


## 3 - Les problèmes non résolus et partie non implémentée.
.- On n'a pas implémenté les boutons car tout se contrôle sur le terminal.
.- On n'a pas totalement organisé notre code sous forme MCV(Modele Vue Controleur), car on n'a pas de fichier Modèle et contrôleur.

