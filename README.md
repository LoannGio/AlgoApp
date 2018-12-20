# AlgoApp
TP d'algorithme appliquée Fabien JACQUES, Loann GIOVANNANGELI, Jonathan NARBONI

## Utilisation

Pour utiliser l'application, compilez tous les fichiers sources dans _robodef/src/main_ avec les librairies externes (fichiers .jar) présents dans les dossiers _robotdef/lib\_jgrapht_ et _robotdef/lib\_json_. L'application nécessite Java 8 ou supérieure.

Lorsque le programme est lancé, vous devez commencer par choisir un fichier au format JSON décrivant le problème dont vous voulez générer une solution (au moins un exemple pour chaque problème est disponible dans le dossier \textit{robotdef/problems}).  Vous devrez ensuite choisir le type de problème (extension) lié au fichier que vous venez de sélectionner.

Le programme utilisera l'algorithme correspondant au type de problème que vous aurez renseigné, sans vérifier la structure du fichier JSON décrivant ce problème. L'utilisation d'un fichier non adapté au problème sélectionné pourrait ne pas donner d'erreur apparente, mais le résultat, s'il en sort un, n'aurait pas de sens.

## Utilisation de SAT

L'utilisation de l'algorithme de réduction vers SAT diffère des autres. Pour l'utiliser, il faut tout compiler glucose dans _robotdef/glucose-syrup-4.1/simp_ avec la commande _make_.

Une fois l'exécutable de glucose généré, vous pouvez lancer le programme JAVA. La réduction vers SAT ne fonctionne que pour les problèmes normaux sans extension (basic problems).

Sélectionnez ensuite SAT comme type de problème. Notre algorithme SAT ne gère pas les collisions. Que vous cochiez ou non "Collision" n'a pas d'importance.
