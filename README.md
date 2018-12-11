# itsepalveluPOS

itsepalveluPOS on alusta esimerkiksi kerhotiloissa tapahtuvaa myyntiä varten. Sovellus tulee tarjoamaan graafisen ja kosketusnäyttö ystävällisen käyttöliittymän ostosten tekoon. Tämän lisäksi se tarjoaa työkalut varaston ja myynnin seurantaan.

## Dokumentaatio

[Vaatimusmäärittely](dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](dokumentaatio/tuntikirjanpito.md)

[Käyttöohje](dokumentaatio/kayttoohje.md)


## Julkaisut

[Viikko 5](https://github.com/villeheikkila/itsepalveluPOS/releases/tag/viikko5)

[Viikko 6](https://github.com/villeheikkila/itsepalveluPOS/releases/tag/1.0)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _itsepalveluPOS-1.0-SNAPSHOT_

### Checkstyle

Tiedostoon [checkstyle.xml](checkstyle.xml) määritellyt tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_