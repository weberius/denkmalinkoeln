#Denkmal in Köln

## Ziel

Das Projekt "Denkmal in Köln" will die Liste der Denkmäler in Köln, die durch [Offene Daten Köln](http://www.offenedaten-koeln.de/) zur Verfügung gestellt werden, auf einer Karte positionieren und dem Benutzer die Möglichkeit geben, sofern er den Standort für diese Webanwendung in seinem Browser freigibt, die nächsten als Denkmal eingeordneten Stellen anzuzeigen. Im Rahmen dieses Projektes gilt es folgenden Herausforderungen gerecht zu werden:

1. Umwandlen von CSV-Daten in JSON-Strukturen
2. Zuordnen von Georeferenzierung anhand von Adressdaten und Nominatim
3. Darstellung von Punkten auf einer Karte mit Clustering Funktion
4. Standort in der Webapplikation bestimmen.

## Umwandeln von CSV-Daten in JSON-Strukturen

Für die Umwandlung von CSV-Daten in JSON-Strukturen wird der Service [dankmallistekoeln](https://github.com/weberius/denkmallistekoeln) verwendet. 

## Zuordnen von Georeferenzierung anhand von Adressdaten und Nominatim

Die Daten bzgl. der Denkmäler in Köln sind nicht georeferenziert. Allerdings ist die Adresse angegeben. Mithilfe von Nominatim ist es möglich die Adress-Daten zu georeferenzieren. Der Zugriff auf den Nominatim-Service wird über den Service [denkmalgeocoding](https://github.com/weberius/denkmalgeocoding) durchgeführt.

## Darstellung von Punkten auf einer Karte

Für die Darstellung der Punkte auf einer Karte werden die durch die vorausgehenden Services ermittelten Informationen zu geoJson umgewandelt. Dadurch können die fraglichen Punkte per [Leaflet](http://leafletjs.com/) dargestellt werden.

## Rückgabe

Der Service bietet einerseits eine REST-Schnittstelle an, um die die geoJson formatierten Punkte im JSON Format auszugeben (http://<server:port>/denkmalinkoeln/service). Andererseits gibt es auch eine Weboberfläche, die die Daten anzeigen kann (http://<server:port>/denkmalinkoeln/);

### REST-Schnittstelle

Die REST-Schnittstelle 

### Weboberfläche

Die Weboberfläche ist mit Leaflet umgesetzt. Sie wird direkt über http://<server:port>/denkmalinkoeln/index.html angezeigt. Zur Darstellung wird das Leaflet Plugn [Leaflet.markercluster](https://github.com/Leaflet/Leaflet.markercluster) eingesetzt. Damit die Karte per Klick auf den Standort des Benutzers eingestellt werden kann, wird das das Leaflet Plugin [Leaflet.Locate](https://github.com/domoritz/leaflet-locatecontrol) verwendet. Um zu verdeutlichen, dass die Daten noch geladen werden, wird das Leaflet Plugin [Leaflet.Spin](https://github.com/makinacorpus/Leaflet.Spin) verwendet.

#### Leaflet.markercluster

#### Leaflet.Locate

#### Leaflet.Spin

## Installation 

Es handelt sich bei diesem Projekt um einen Service, der für den Betrieb z.B. einen Tomcat benötigt. Die darzustellenden Daten werden per default aus der Datei [denkmalinkoeln.json](https://github.com/weberius/denkmalinkoeln/blob/master/src/main/resources/denkmalinkoeln.json) gelesen. Das Projekt wird mit maven gebaut.