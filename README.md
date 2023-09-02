# Plateforme Pharmaceutique

Ce projet est une plateforme pharmaceutique basée sur Spring Boot, Java 8 et Keycloak (12.0.4 recommandé). Il utilise Docker pour faciliter le déploiement. Assurez-vous d'avoir Docker Desktop installé sur votre système.

## Configuration Préalable
Avant de commencer à utiliser cette plateforme, il est essentiel de configurer correctement votre environnement. Suivez ces étapes :

#### Ajoutez une entrée dans votre fichier hosts :
Pour permettre à votre système de résoudre le nom "keycloak" en utilisant l'adresse IP locale, ajoutez cette ligne à votre fichier hosts :

Copy code
keycloak 127.0.0.1
Cela garantira que votre application puisse se connecter à Keycloak sans problème.

## Installation et Exécution
Pour exécuter cette plateforme, vous devrez construire des images Docker pour chaque microservice, y compris Keycloak. Voici comment procéder :

#### Construisez les images Docker :
Dans chaque répertoire de microservice, exécutez la commande suivante pour construire les images Docker nécessaires. Assurez-vous de remplacer "nom_image" par un nom approprié pour chaque microservice :


Copy code
docker build -t nom_image .
Cela créera des images Docker prêtes à être utilisées.

## Démarrez l'ensemble de l'application :
Utilisez Docker Compose pour lancer tous les microservices en même temps :


Copy code
docker-compose up
Cela orchestrera le déploiement de l'ensemble de la plateforme.


## Configuration de Keycloak
La configuration de Keycloak est cruciale pour gérer l'authentification et l'autorisation de votre plateforme. Suivez ces étapes pour configurer Keycloak correctement :

Accédez à l'interface d'administration de Keycloak en utilisant l'URL suivante : http://localhost:port/auth/.

Créez un nouveau realm appelé "plateforme-pharmaceutique". Vous pouvez suivre la configuration recommandée en utilisant la capture d'écran ci-dessous :

<img width="885" alt="add realm" src="https://github.com/talbijihed12/plateforme-pharmaceutique-personnalisee/assets/83588915/fbcb5116-b3df-4bfe-ad96-a7694dd6e728">


Créez chaque microservice en tant que client dans le realm "plateforme-pharmaceutique". Utilisez les captures d'écran suivantes pour configurer les détails du client :

<img width="892" alt="add client" src="https://github.com/talbijihed12/plateforme-pharmaceutique-personnalisee/assets/83588915/c66c1382-013e-45ab-b646-6aa96deab26c">


<img width="944" alt="add client 2" src="https://github.com/talbijihed12/plateforme-pharmaceutique-personnalisee/assets/83588915/4e3b7129-e0dd-47ce-ae5f-25b7ae059cb8">


Créez un utilisateur administrateur et attribuez-lui le rôle d'administrateur de realm.

## Configuration des Microservices
Pour que chaque microservice fonctionne correctement avec Keycloak, vous devrez ajuster sa configuration. Voici les éléments clés à configurer dans chaque microservice :

Nom du Realm: Configurez le nom du realm en tant que "plateforme-pharmaceutique" pour chaque microservice.

Nom du Client: Chaque microservice doit être configuré en tant que client dans le realm "plateforme-pharmaceutique".

URL de Redirection OAuth2: Assurez-vous que les URL de redirection OAuth2 sont correctement configurées pour chaque microservice, conformément à la configuration recommandée.

Ce fichier README fournit des instructions détaillées pour configurer et exécuter la plateforme pharmaceutique. Assurez-vous de consulter chaque microservice pour des détails spécifiques sur la configuration.
