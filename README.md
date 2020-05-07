# Usury2020
Koronkiskonnan hallintaohjelma

### Määritä tietokannan sijainti
Tietokannan osoite ja käyttäjätunnus määritetään ```src/main/resources/hibernate.cfg.xml``` tiedostossa.
### Määritä testien käyttämän tietokannan sijainti
Testi tietokannan osoite ja käyttäjätunnus määritetään ```src/main/resources/hibernate.cfg.xml``` tiedostossa.
### Aja Usury2020 javafx-sovellus
```mvn clean javafx:run```
### Aja junit-testejä
```mvn test```
### Luo jacoco testikattavuusraportti
1. ```mvn jacoco:report```
2. Avaa raporttti ```target/site/jacoco/index.html```
### Luo .jar
```mvn package```
