Eric Blais, 1067982
Celia Ait Ouazzou, 20105239


https://github.com/erbla/qualite_tp1

Instructions:

Inclure comme unique argument le chemin relatif vers le dossier contenant
le code à analyser.

Commande pour le jar:
java -jar MeasureDoc.jar <chemin-relatif>

Commande pour le bytecode:
java MeasureDoc.class <chemin-relatif>

Pour créer le jar:
La classe principale est MeasureDoc.java

Ces commandes peut générer le jar à partir du dossier src:
javac *
jar cfe MeasureDoc.jar MeasureDoc *.class

EXEMPLE DE COMMANDE COMPLÈTE POUR ANALYSER JFREECHART:
java -jar MeasureDoc.jar jfreechart