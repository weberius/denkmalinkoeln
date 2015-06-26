#Denkmal in Köln

## Ziel

Denkmal in Köln will die Liste der Denkmäler in Köln, die durch [Offene Daten Köln](http://www.offenedaten-koeln.de/) zur Verfügung gestellt werden. Im Rahmen dieses Projektes gilt es folgenden Herausforderungen gerecht zu werden:

1. Umwandlen von CSV-Daten in JSON-Strukturen
2. Zuordnen von Georeferenzierung anhand von Adressdaten und Nominatim
3. Darstellung von Punkten auf einer Karte

## Umwandeln von CSV-Daten in JSON-Strukturen

Für die Umwandlung von CSV-Daten in JSON-Strukturen wird der Service [dankmallistekoeln](https://github.com/weberius/denkmallistekoeln) verwendet. 

## Zuordnen von Georeferenzierung anhand von Adressdaten und Nominatim

Die Daten bzgl. der Denkmäler in Köln sind nicht georeferenziert. Allerdings ist die Adresse angegeben. Mithilfe von Nominatim ist es möglich die Adress-Daten zu georeferenzieren. Der Zugriff auf den Nominatim-Service wird über den Service [denkmalgeocoding](https://github.com/weberius/denkmalgeocoding) durchgeführt.

## Darstellung von Punkten auf einer Karte

Für die Darstellung der Punkte auf einer Karte werden die durch die vorausgehenden Services ermittelten Informationen zu geoJson umgewandelt. Dadurch können die fraglichen Punkte per [Leaflet](http://leafletjs.com/) dargestellt werden.

## Rückgabe

Der Service bietet einerseits eine REST-Schnittstelle an, um die die geoJson formatierten Punkte im JSON Format auszugeben (http://<server:port>/denkmalinkoeln/service). Andererseits gibt es auch eine Weboberfläche, die die Daten anzeigen kann (http://localhost:8090/denkmalinkoeln/);


## Installation 

Es handelt sich bei diesem Projekt um einen Service, der für den Betrieb z.B. einen Tomcat benötigt. Die darzustellenden Daten werden per default aus der Datei [denkmalinkoeln.json](https://github.com/weberius/denkmalinkoeln/blob/master/src/main/resources/denkmalinkoeln.json) gelesen. Das Projekt wird mit maven gebaut.