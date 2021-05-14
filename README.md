[![CircleCI](https://circleci.com/gh/MarcSechet/good-smash.svg?style=shield)](https://circleci.com/gh/MarcSechet/good-smash)
[![Created Badge](https://badges.pufler.dev/created/MarcSechet/good-smash)](https://github.com/MarcSechet/good-smash)
[![Created Badge](https://badges.pufler.dev/updated/MarcSechet/good-smash)](https://github.com/MarcSechet/good-smash)
![Lines of code](https://img.shields.io/tokei/lines/github/MarcSechet/good-smash)
![GitHub repo size](https://img.shields.io/github/repo-size/MarcSechet/good-smash)
![GitHub](https://img.shields.io/github/license/MarcSechet/good-smash)

# Good Smash

Projects containing a single BO used for multiple front end :
- Smash 
- Papicolo
- Pépites

Maybe some day this BO will be split into microservices but not sure the free heroku can manage that.

Architecture:
The project has the following modules :
- smash-app (contains config, controllers, mappers and utils)
- smash-service
- smash-persist (contains dao and entities)
- smash-spec (contains openapi specs)

Install notes : 
The generated spec might not be accessible in other modules of the project. To fix this, on IntelliJ, you can open project settings -> modules -> goodsmash-app -> dependencies -> add module dependency goodsmash-spec. If you have an error while saving because content root is already defined in other module project settings -> modules -> goodsmash -> sources -> remove paths in the content root tab not belonging to other modules.

How to fix Spring Boot Multi-Module maven project repackage failed (Spring projects with multi modules) : put the spring-boot-maven-plugin in the pom of the module containing the Application class (https://stackoverflow.com/a/57983802)


# Contributors
[![Contributors Display](https://badges.pufler.dev/contributors/MarcSechet/good-smash?size=50&padding=5&bots=true)](https://github.com/MarcSechet/good-smash)
