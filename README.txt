

.______     ______    __    __   __       _______ .___________.   ____    ____  ______    __          ___      .__   __. .___________.                                                      
|   _  \   /  __  \  |  |  |  | |  |     |   ____||           |   \   \  /   / /  __  \  |  |        /   \     |  \ |  | |           |                                                      
|  |_)  | |  |  |  | |  |  |  | |  |     |  |__   `---|  |----`    \   \/   / |  |  |  | |  |       /  ^  \    |   \|  | `---|  |----`                                                      
|   ___/  |  |  |  | |  |  |  | |  |     |   __|      |  |          \      /  |  |  |  | |  |      /  /_\  \   |  . `  |     |  |                                                           
|  |      |  `--'  | |  `--'  | |  `----.|  |____     |  |           \    /   |  `--'  | |  `----./  _____  \  |  |\   |     |  |                                                           
| _|       \______/   \______/  |_______||_______|    |__|            \__/     \______/  |_______/__/     \__\ |__| \__|     |__|                                                           
                                                                                                                                                                                            
  _______  _______ .__   __.  _______ .______          ___   .___________. _______  __    __  .______          _______   _______         _______.___________.    ___       _______  _______ 
 /  _____||   ____||  \ |  | |   ____||   _  \        /   \  |           ||   ____||  |  |  | |   _  \        |       \ |   ____|       /       |           |   /   \     /  _____||   ____|
|  |  __  |  |__   |   \|  | |  |__   |  |_)  |      /  ^  \ `---|  |----`|  |__   |  |  |  | |  |_)  |       |  .--.  ||  |__         |   (----`---|  |----`  /  ^  \   |  |  __  |  |__   
|  | |_ | |   __|  |  . `  | |   __|  |      /      /  /_\  \    |  |     |   __|  |  |  |  | |      /        |  |  |  ||   __|         \   \       |  |      /  /_\  \  |  | |_ | |   __|  
|  |__| | |  |____ |  |\   | |  |____ |  |\  \----./  _____  \   |  |     |  |____ |  `--'  | |  |\  \----.   |  '--'  ||  |____    .----)   |      |  |     /  _____  \ |  |__| | |  |____ 
 \______| |_______||__| \__| |_______|| _| `._____/__/     \__\  |__|     |_______| \______/  | _| `._____|   |_______/ |_______|   |_______/       |__|    /__/     \__\ \______| |_______|
                                                                                                                                                                                            


README
Poulet Volant
Generateur de stage

INSTALLATION

1) Télécharger les dépendances Maven (Maven Update)
2) Télécharger les dépendances NPM (`npm install` dans /react)
3) Configurer MySQL:
	3.1) Créer le schéma 'poulet_volant'
	3.2) Créer l'utilisateur 'pouletvolant' avec le mot de passe 'poulet'
	3.3) Donner tous les droits sur le schéma poulet_volant à l'utilisateur pouletvolant.
4) Démarrer le serveur Spring Boot
5) Démarrer le serveur React

UTILISATEURS PAR DÉFAUT

Un jeu d'utilisateurs et d'offres par défaut viennent avec le programme. Voici les informations de connections pour les trois comptes principaux par défaut.

Gestionnaire de Stage:
identifiant: gsTeam2@poulet.ca
mdp: secret

Employeur:
identifiant: employeurTeam2@poulet.ca
mdp: secret

Étudiant
identifiant: student@poulet.ca
mdp: secret