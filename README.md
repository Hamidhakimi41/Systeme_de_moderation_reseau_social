<!-- LTeX: language=fr -->
<!-- dates :
grep '^## .. ' README.md | sed 's/[^(]*(//; s/,.*//' | sed 's@\(.*\)/\(.*\)/\(.*\)@\2/\1/\3@' | date -f -
-->
# Gestion de Projet et Génie Logiciel, M1, département informatique, Lyon 1, 2024-2025

## Dates importantes

<!-- [Cf. ADE](http://adelb.univ-lyon1.fr/direct/index.jsp?projectId=4&days=0,1,2,3,4&resources=33140&weeks=4,5). -->

* Emploi du temps : Cf. ADE.
  Utilisez la ressource "M1 informatique" **plus les groupes de TD** pour voir l'emploi du temps. Contrairement à la licence vous n'avez pas d'emploi du temps personnalisé il faut regarder celui de votre groupe.

* Rendu du TP noté : vous devez avoir rempli le champ URL de TOMUSS avant le 13 septembre 2024 (malus sur la note si ce n'est pas fait sans une raison valable). Le rendu final est le 8/12/2024 à 23h59. Voir
  [projet-note.md](projet-note.md) pour un récapitulatif des consignes.

* Examen : TODO: date à décider, voir amphi sur TOMUSS. 1h30 (sauf tiers-temps) Consignes : Seules 5
  feuilles A4 recto verso (donc 10 pages au total) sont autorisées à
  l’examen. Leur contenu est libre. Elles peuvent être une sélection de
  transparents ou manuscrites, avec une taille de caractère de votre
  choix. Les annales de l'examen sont dans le répertoire [exam/](http://matthieu-moy.fr/cours/mif01/exam/)
  et [sur l'ancienne page du
  cours](http://www.tabard.fr/cours/2017/mif01/). Prévoyez un stylo
  bleu foncé ou noir et un blanc correcteur.

* Session 2 : mêmes consignes que pour la session 1.

Barème : 40% examen / 60% TP (une note pour le rapport, une note pour le code). Seul l'examen est rattrapable en session 2.

## Enseignants et contacts

* [Matthieu Moy](https://matthieu-moy.fr/) (responsable du cours)
* [Lionel Medini](https://perso.liris.cnrs.fr/lionel.medini/)

## Enseignement présentiel, vidéos et messagerie instantanée

En complément des séances en présentiel, les étudiants qui le souhaitent peuvent utiliser les vidéos préparées en 2020 pour les cours magistraux (disponibles sur la plateforme My Video Lyon 1, et sur la [playlist youtube](https://www.youtube.com/playlist?list=PL6-YbcqXawf5ED3NHDZYejWJaAschnrO0)). Attention, le contenu du cours a un peu évolué depuis, regarder les vidéos ne dispense pas de venir en CM !

Vous pouvez interagir avec les enseignants via le système d'issues de GitLab (qui n'est pas vraiment prévu pour cela mais peut être détourné pour en faire un petit forum) : https://forge.univ-lyon1.fr/matthieu.moy/mif01/-/issues. Pour poser une question, créez une nouvelle issue (une issue par question). Il y a une entrée dédiée « cherche binôme » si vous êtes à la recherche d'un binôme. Pour être informé de l'activité (nouveaux commentaires, etc), réglez les notifications sur « watch » en cliquant sur la cloche sur la page d'accueil du projet :

![Notifications sur Gitlab](fig/gitlab-watch.png)

## Nouvelles du cours

Les informations de dernière minute sont disponibles ici :
[NEWS.md](NEWS.md). Les informations importantes seront envoyées par
email, ce fichier en contient une copie.

## CM 1 (3/9/2024, 14h) : Introduction

### Intro du cours

* Vidéo : <a target="_blank" title="Introduction du cours" href="https://myvideo.univ-lyon1.fr/permalink/v126648321336prac6d7/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f63c7665cu1ddhhdumjvjfkrdr/thumb_link.png" alt="Introduction du cours"/></a>

* Transparents : [00-intro-cours-slides.pdf](http://matthieu-moy.fr/cours/mif01/00-intro-cours-slides.pdf) (version imprimable : [00-intro-cours-handout.pdf](http://matthieu-moy.fr/cours/mif01/00-intro-cours-handout.pdf))

### Introduction au génie logiciel

* Vidéo : <a target="_blank" title="Introduction au génie logiciel" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fbadfanih3/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e3b1081xmi26ty70pk23mhvs/thumb_link.png" alt="Introduction au génie logiciel"/></a>

* Transparents : [01-introduction-slides.pdf](http://matthieu-moy.fr/cours/mif01/01-introduction-slides.pdf) (version imprimable : [01-introduction-handout.pdf](http://matthieu-moy.fr/cours/mif01/01-introduction-handout.pdf))
  
## TP 1 (3/9/2024, 15h45) : Mise en route Java

* Énoncé : [TP1-java/README.md](TP1-java/README.md)

<!-- * Salles :
  - Nautibus TP 1, Nautibus TP 2: Matthieu Moy
  - Nautibus TP 3, Nautibus TP 4: Joel Felderhoff
  - Nautibus TP 5, Nautibus TP 6: Lionel Medini
  - Nautibus TP 9, Nautibus TP10: Mathieu Hilaire
  -->

## CM 2 (Vendredi 6/9/2024, 9h45) : Outillage : Maven, la forge Gitlab, intégration continue, coding style ... 

### Maven, Forge, Intégration Continue

* Vidéo : <a target="_blank" title="Maven, Forge, Intégration Continue" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fb8hrl6hos/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e3a7f0fkr7ooladan4vxrmp1/thumb_link.png" alt="Maven, Forge, Intégration Continue"/></a>

* Transparents : [02-cm-maven-forge-ic-slides.pdf](http://matthieu-moy.fr/cours/mif01/02-cm-maven-forge-ic-slides.pdf)
  (Version imprimable : [02-cm-maven-forge-ic-handout.pdf](http://matthieu-moy.fr/cours/mif01/02-cm-maven-forge-ic-handout.pdf))

## TP 2 (Vendredi 6/9/2024, 11h30) : Outillage

* Énoncé : [TP2-outils/README.md](TP2-outils/README.md)

* Salles : comme le matin.

## TP 3 (10/9/2024, 14h00) : Outillage (suite)

* Énoncé : continuer [TP2-outils/README.md](TP2-outils/README.md)

<!-- * Salles :
  - Nautibus TP 1, Nautibus TP 2 : Matthieu Moy
  - Nautibus TP 3, Nautibus TP 4 : Joel Felderhoff
  - Nautibus TP 5, Nautibus TP 6 : Lionel Medini
  - Nautibus TP 9, Nautibus TP10 : Mathieu Hilaire
  -->

## CM 3 (10/9/2024, 15h45, par Lionel Medini) : Coding style, Design patterns (début)

### Coding style

* Vidéo : <a target="_blank" title="Coding Style" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fbdwej3119/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e3b82e5qhb6vdjbfdziizhq7/thumb_link.png" alt="Coding Style"/></a>

* Transparents : [03-coding-style-slides.pdf](http://matthieu-moy.fr/cours/mif01/03-coding-style-slides.pdf)
  (version imprimable : [03-coding-style-handout.pdf](http://matthieu-moy.fr/cours/mif01/03-coding-style-handout.pdf))

### Design patterns

* Slides : [CM-patterns.pdf](https://perso.liris.cnrs.fr/lionel.medini/enseignement/M1IF01/CM-patterns.pdf)

* Vidéo du mardi 8/9/2020 sur Youtube : [https://youtu.be/oal57dd73nY](https://youtu.be/oal57dd73nY)

* Vidéo du mercredi 9/9/2020 sur Youtube : [https://youtu.be/9oV0xDSrXes](https://youtu.be/9oV0xDSrXes)

## CM 4 (Mercredi 11/9/2024, 11h30, par Lionel Medini) : Design patterns (suite)

## TD 1 (Vendredi 13/9/2024, 11h30) : Design patterns

* [TD1-design-patterns](http://matthieu-moy.fr/cours/mif01/TD1-patterns/TD-design-patterns.html)

<!-- * Salles : cf. TOMUSS et mail envoyé pour les étudiants.
  - Nautibus TD 2 : Matthieu Moy
  - Nautibus TD 3 : Joel Felderhoff
  - Nautibus TD12 : Lionel Medini
  - Nautibus TD13 : Mathieu Hilaire
  -->

## CM 5 (1/10/2024, 14h) : Métaprogrammation, spécifications et cas d'utilisation

### Métaprogrammation

* Vidéo : <a target="_blank" title="Métaprogrammation" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fc5hrmy3g1/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e59e89b1kkvlmtrymwi6ngkv/thumb_link.png" alt="Métaprogrammation"/></a>

* Transparents [04-metaprogramming-slides.pdf](http://matthieu-moy.fr/cours/mif01/04-metaprogramming-slides.pdf)
  (version imprimable : [04-metaprogramming-handout.pdf](http://matthieu-moy.fr/cours/mif01/04-metaprogramming-handout.pdf))

### Spécifications

* Vidéo : <a target="_blank" title="Spécifications et cas d'utilisations" href="https://myvideo.univ-lyon1.fr/permalink/v1266483213e2log0vyw/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f64046e89yvikpjhr6bdcklhii/thumb_link.png" alt="Spécifications et cas d'utilisations"/></a>

* [05-UML-CU.pdf](http://matthieu-moy.fr/cours/mif01/05-UML-CU.pdf)

## TP 4 (Vendredi 4/10/2024, 11h30) : Design patterns & refactoring

* [TP3-patterns/README.md](TP3-patterns/README.md) et de l'aide avec [MVC](TP3-patterns/mvc.md)

<!-- * Salles :
  - Nautibus TP 4, Nautibus TP 5 : Matthieu Moy
  - Nautibus TP 6, Nautibus TP10 : Joel Felderhoff
  - Nautibus TP11, Nautibus TP12 : Lionel Medini
  - Nautibus TP13, Nautibus TP14 : Mathieu Hilaire
  -->

## CM 6 (8/10/2024, 14h) : Test

* Transparents de Sandrine Gouraud, lien disponible sur TOMUSS.

* [Software Fail Watch, 5th
  edition](https://www.tricentis.com/wp-content/uploads/2019/01/Software-Fails-Watch-5th-edition.pdf)
  présenté pendant le cours.

## TD 2 (8/10/2024, 15h45) : Coding styles, spécifications et agilité

* [TD2-uc-style/TD-usecase-et-style.pdf](http://matthieu-moy.fr/cours/mif01/TD2-uc-style/TD-usecase-et-style.pdf)
  (corrigé : [TD2-uc-style/TD-usecase-et-style-corrige.pdf](http://matthieu-moy.fr/cours/mif01/TD2-uc-style/TD-usecase-et-style-corrige.pdf))

<!-- * Salles :
  - Groupe A : Nautibus TD 1, 15h45 : Matthieu Moy
  - Groupe B : Nautibus TD 2, 15h45 : Mathieu Hilaire
  - Groupe C : Nautibus TD 3, 15h45 : Lionel Medini
  - Groupe D : Nautibus TD 1, 11h30 : Matthieu Moy
  -->

## CM 7 (29/10/2024, 14h) : introduction à l'agilité

### Introduction à l'Agilité

* Vidéo : <a target="_blank" title="Introduction à l'agilité" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fc19hggacu/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e59e5038qrz03jom0i42z6ey/thumb_link.png" alt="Introduction à l'agilité"/></a>

* Transparents : [07-agilite-slides.pdf](http://matthieu-moy.fr/cours/mif01/07-agilite-slides.pdf)
  (version imprimable : [07-agilite-handout.pdf](http://matthieu-moy.fr/cours/mif01/07-agilite-handout.pdf))

## TP 5 (29/10/2024, 15h45): Design patterns & refactoring, suite

* Continuer avec : [TP3-patterns/README.md](TP3-patterns/README.md) et de l'aide avec [MVC](TP3-patterns/mvc.md)

<!-- * Salles :
  - Nautibus TP 1, Nautibus TP 2 : Matthieu Moy
  - Nautibus TP 3, Nautibus TP 4 : Joel Felderhoff
  - Nautibus TP 5, Nautibus TP 9 : Lionel Medini
  - Nautibus TP10, Nautibus TP11 : Mathieu Hilaire
  -->

## CM 8 (5/11/2024, 14h) : Agilité (suite)

### Fin du cours sur l'agilité

* Vidéo : <a target="_blank" title="Conclusion sur l'agilité" href="https://myvideo.univ-lyon1.fr/permalink/v1266483214d42el9dn3/iframe/#start=2"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f64407b3aqquizvskjxrimpz6n/thumb_link.png" alt="Conclusion sur l'agilité"/></a>

* Transparents : [07-agilite-slides.pdf](http://matthieu-moy.fr/cours/mif01/07-agilite-slides.pdf)
  (version imprimable : [07-agilite-handout.pdf](http://matthieu-moy.fr/cours/mif01/07-agilite-handout.pdf))

### Agilité et passage à l'échelle : la méthode Spotify (transparents de Levent Acar)

* Vidéo : <a target="_blank" title="La méthode spotify" href="https://myvideo.univ-lyon1.fr/permalink/v1266483214d7t0dnl7u/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f64408f14upw2wztzw9kmg6j85/thumb_link.png" alt="La méthode spotify"/></a>

* Transparents : [Présentation_spotify_v2.pdf](http://matthieu-moy.fr/cours/mif01/Présentation_spotify_v2.pdf)

## TP 6 (5/11/2024, 15h45 et 8/11/2024, 11h30) : Tests

Suite à un conflit d'emploi du temps d'un intervenant, le TP est coupé en deux : 5/11/2024, 15h45 et 8/11/2024, 11h30. Vous pouvez venir au choix à l'un, l'autre, ou aux deux créneaux. Matthieu Moy sera là le mardi, Lionel Medini le vendredi.

* Énoncé : [TP4-tests/README.md](TP4-tests/README.md)

<!-- * Salles :
  - Nautibus TP 1, Nautibus TP 2 : Matthieu Moy
  - Nautibus TP 3, Nautibus TP 4 : Joel Felderhoff
  - Nautibus TP 5, Nautibus TP 6 : Lionel Medini
  - Nautibus TP 9, Nautibus TP11 : Mathieu Hilaire
  -->

## CM 9 (26/11/2024, 14h) : Éthique

### Éthique

* Vidéo : <a target="_blank" title="Éthique et numérique" href="https://myvideo.univ-lyon1.fr/permalink/v126648320fc91hpkb0g/iframe/"><img src="https://myvideo.univ-lyon1.fr/public/videos/v125f5e878599ekgr4ire6ac5wv8q0/thumb_link.png" alt="Éthique et numérique"/></a>

* Transparents : [07-ethics-slides.pdf](http://matthieu-moy.fr/cours/mif01/07-ethics-slides.pdf)
  (version imprimable : [07-ethics-handout.pdf](http://matthieu-moy.fr/cours/mif01/07-ethics-handout.pdf))

## TP 7 (Vendredi 29/11/2024, 15h45) : Tests (suite)

* Sujet : continuer avec [TP4-tests/README.md](TP4-tests/README.md)

<!-- * Salles :
  - Nautibus TP 1, Nautibus TP 3 : Matthieu Moy
  - Nautibus TP 4, Nautibus TP 5 : Joel Felderhoff
  - Nautibus TP 6, Nautibus TP 9 : Lionel Medini
  - Nautibus TP10, Nautibus TP11 : Mathieu Hilaire
  -->

## CM 10 (3/12/2024, 14h) : Ethique, suite.

## TP 8 (Vendredi 6/12/2024, 15h45) : fin du projet

Cette dernière séance devrait vous permettre de boucler votre projet, qui est à rendre quelques jours plus tard.

<!-- * Salles :
  - Nautibus TP 1, Nautibus TP 2 : Matthieu Moy
  - Nautibus TP 3, Nautibus TP 4 : Joel Felderhoff
  - Nautibus TP 5, Nautibus TP 6 : Lionel Medini
  - Nautibus TP 9, Nautibus TP10 : Mathieu Hilaire
  -->

## 17/12/2023, 23h59 : date limite pour le rendu de projet.
