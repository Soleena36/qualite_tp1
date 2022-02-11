Eric Blais, 1067982


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

Ces commandes peut générer le jar du fichier src:
javac *
jar cfe MeasureDoc.jar MeasureDoc *.class