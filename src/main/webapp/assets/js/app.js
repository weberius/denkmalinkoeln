var map, featureList, boroughSearch = [], museumSearch = [];

$(window).resize(function() {
  sizeLayerControl();
});

$(document).on("click", ".feature-row", function(e) {
  $(document).off("mouseout", ".feature-row", clearHighlight);
});

$(document).on("mouseover", ".feature-row", function(e) {
  highlight.clearLayers().addLayer(L.circleMarker([$(this).attr("lat"), $(this).attr("lng")], highlightStyle));
});

$(document).on("mouseout", ".feature-row", clearHighlight);

$("#about-btn").click(function() {
  $("#aboutModal").modal("show");
  $(".navbar-collapse.in").collapse("hide");
  return false;
});

$("#full-extent-btn").click(function() {
  map.fitBounds(boroughs.getBounds());
  $(".navbar-collapse.in").collapse("hide");
  return false;
});

$("#legend-btn").click(function() {
  $("#legendModal").modal("show");
  $(".navbar-collapse.in").collapse("hide");
  return false;
});

$("#login-btn").click(function() {
  $("#loginModal").modal("show");
  $(".navbar-collapse.in").collapse("hide");
  return false;
});

$("#list-btn").click(function() {
  $('#sidebar').toggle();
  map.invalidateSize();
  return false;
});

$("#nav-btn").click(function() {
  $(".navbar-collapse").collapse("toggle");
  return false;
});

$("#sidebar-toggle-btn").click(function() {
  $("#sidebar").toggle();
  map.invalidateSize();
  return false;
});

$("#sidebar-hide-btn").click(function() {
  $('#sidebar').hide();
  map.invalidateSize();
});

function sizeLayerControl() {
  $(".leaflet-control-layers").css("max-height", $("#map").height() - 50);
}

function clearHighlight() {
  highlight.clearLayers();
}

/* Basemap Layers */
var mapquestOSM = L.tileLayer("http://{s}.mqcdn.com/tiles/1.0.0/osm/{z}/{x}/{y}.png", {
  maxZoom: 18,
  subdomains: ["otile1", "otile2", "otile3", "otile4"],
  attribution: 'Tiles courtesy of <a href="http://www.mapquest.com/" target="_blank">MapQuest</a> <img src="http://developer.mapquest.com/content/osm/mq_logo.png">. Map data (c) <a href="http://www.openstreetmap.org/" target="_blank">OpenStreetMap</a> contributors, CC-BY-SA.'
});

/* Overlay Layers */
var highlight = L.geoJson(null);
var highlightStyle = {
  stroke: false,
  fillColor: "#FF0000",
  fillOpacity: 0.5,
  radius: 20
};

var boroughs = L.geoJson(null, {
  style: function (feature) {
    return {
      color: "grey",
      fill: false,
      opacity: 1,
      clickable: false,
      weight: 1
    };
  },
  onEachFeature: function (feature, layer) {
    boroughSearch.push({
      name: layer.feature.properties.name,
      source: "Stadtteile",
      id: L.stamp(layer),
      bounds: layer.getBounds()
    });
  }
});
$.getJSON("data/stadtteile_100.json", function (data) {
  boroughs.addData(data);
});

/* Single marker cluster layer to hold all clusters */
var markerClusters = new L.MarkerClusterGroup({
  spiderfyOnMaxZoom: true,
  showCoverageOnHover: false,
  zoomToBoundsOnClick: true,
  disableClusteringAtZoom: 18
});


function pointToLayerDenkmal(feature, latlng) {
    return L.marker(latlng, {
      icon: L.icon({
        iconUrl: "assets/img/denkmal.png",
        iconSize: [24, 28],
        iconAnchor: [12, 28],
        popupAnchor: [0, -25]
      }),
      title: feature.properties.kurzbezeichnung,
      riseOnHover: true
    });
}

function onEachFeatureDenkmal(feature, layer) {
    if (feature.properties) {
      var content = "<table class='table table-striped table-bordered table-condensed'>" 
    	  + "<tr><th>Kurzbezeichnung</th><td>" + feature.properties.kurzbezeichnung + "</td></tr>" 
    	  + "<tr><th>Denkmalnummer</th><td>" + feature.properties.denkmalnummer + "</td></tr>" 
    	  + "<tr><th>Baujahr</th><td>" + feature.properties.baujahr + "</td></tr>" 
    	  + "<tr><th>Adresse</th><td>" + feature.properties.adresse + "</td></tr>" 
    	  + "<table>";
      layer.on({
        click: function (e) {
          $("#feature-title").html(feature.properties.kurzbezeichnung);
          $("#feature-info").html(content);
          $("#featureModal").modal("show");
          highlight.clearLayers().addLayer(L.circleMarker([feature.geometry.coordinates[1], feature.geometry.coordinates[0]], highlightStyle));
        }
      });
      $("#feature-list tbody").append('<tr class="feature-row" id="' + L.stamp(layer) 
    	      	+ '" lat="' + feature.geometry.coordinates[1] 
    	      	+ '" lng="' + feature.geometry.coordinates[0] 
    	      	+ '"><td style="vertical-align: middle;"><img width="16" height="18" src="assets/img/denkmal.png"></td><td class="feature-name">' 
    	      	+ layer.feature.properties.kurzbezeichnung 
    	      	+ '</td><td style="vertical-align: middle;"><i class="fa fa-chevron-right pull-right"></i></td></tr>');
      museumSearch.push({
        name: layer.feature.properties.kurzbezeichnung,
        address: layer.feature.properties.adresse,
        source: "Denkmal",
        id: L.stamp(layer),
        lat: layer.feature.geometry.coordinates[1],
        lng: layer.feature.geometry.coordinates[0]
      });
    }
}

/* Empty layer placeholder to add to layer control for listening when to add/remove museums to markerClusters layer */
var museumLayer = L.geoJson(null);
var museums1492, museums1789, museums1871, museums1945;
var museums = L.geoJson(null, {
  filter : function (feature, latlng) {
	  return feature.properties.baujahr < 1492;
  },
  pointToLayer : pointToLayerDenkmal,
  onEachFeature : onEachFeatureDenkmal
});
$.getJSON("data/located.json", function (data) {
  museums.addData(data);
  map.addLayer(museumLayer);
});

map = L.map("map", {
  zoom: 15,
  center: [50.94135, 6.95819],
  layers: [mapquestOSM, boroughs, markerClusters, highlight],
  zoomControl: false,
  attributionControl: false
});

/* Layer control listeners that allow for a single markerClusters layer */
map.on("overlayadd", function(e) {
  if (e.layer === museumLayer) {
    markerClusters.addLayer(museums);
  }
});

map.on("overlayremove", function(e) {
  if (e.layer === museumLayer) {
    markerClusters.removeLayer(museums);
  }
});

/* Filter sidebar feature list to only show features in current map bounds */
map.on("moveend", function (e) {
});

/* Clear feature highlight when map is clicked */
map.on("click", function(e) {
  highlight.clearLayers();
});

/* Attribution control */
function updateAttribution(e) {
  $.each(map._layers, function(index, layer) {
    if (layer.getAttribution) {
      $("#attribution").html((layer.getAttribution()));
    }
  });
}
map.on("layeradd", updateAttribution);
map.on("layerremove", updateAttribution);

var attributionControl = L.control({
  position: "bottomright"
});
attributionControl.onAdd = function (map) {
  var div = L.DomUtil.create("div", "leaflet-control-attribution");
  div.innerHTML = "<span class='hidden-xs'>Developed by <a href='http://bryanmcbride.com'>bryanmcbride.com</a> | </span><a href='#' onclick='$(\"#attributionModal\").modal(\"show\"); return false;'>Attribution</a>";
  return div;
};
map.addControl(attributionControl);

var zoomControl = L.control.zoom({
  position: "bottomright"
}).addTo(map);

/* GPS enabled geolocation control set to follow the user's location */
var locateControl = L.control.locate({
  position: "bottomright",
  drawCircle: true,
  follow: true,
  setView: true,
  keepCurrentZoomLevel: true,
  markerStyle: {
    weight: 1,
    opacity: 0.8,
    fillOpacity: 0.8
  },
  circleStyle: {
    weight: 1,
    clickable: false
  },
  icon: "fa fa-location-arrow",
  metric: false,
  strings: {
    title: "Hier bin ich.",
    popup: "Sie befinden sich in einem Radius von {distance} {unit} von diesem Punkt.",
    outsideMapBoundsMsg: "Offensichtlich sind Sie ausserhalb der Karte."
  },
  locateOptions: {
    maxZoom: 18,
    watch: true,
    enableHighAccuracy: true,
    maximumAge: 10000,
    timeout: 10000
  }
}).addTo(map);

/* Larger screens get expanded layer control and visible sidebar */
if (document.body.clientWidth <= 767) {
  var isCollapsed = true;
} else {
  var isCollapsed = false;
}

var baseLayers = {
  "Street Map": mapquestOSM
};

var groupedOverlays = {
  "Points of Interest": {
    "<img src='assets/img/denkmal.png' width='24' height='28'>&nbsp;Denkmal": museumLayer
  },
  "Reference": {
	    "<img src='assets/img/wappen.gif' width='24' height='28'>&nbsp;Stadtteile": boroughs
  }
};

var layerControl = L.control.groupedLayers(baseLayers, groupedOverlays, {
  collapsed: isCollapsed
}).addTo(map);

/* Highlight search box text on click */
$("#searchbox").click(function () {
  $(this).select();
});

/* Prevent hitting enter from refreshing the page */
$("#searchbox").keypress(function (e) {
  if (e.which == 13) {
    e.preventDefault();
  }
});

$("#featureModal").on("hidden.bs.modal", function (e) {
  $(document).on("mouseout", ".feature-row", clearHighlight);
});


// Typeahead search functionality 
$(document).one("ajaxStop", function () {
  $("#loading").hide();
  sizeLayerControl();
  // Fit map to boroughs bounds
  // map.fitBounds(boroughs.getBounds());
  featureList = new List("features", {valueNames: ["feature-name"]});
  //featureList.sort("feature-name", {order:"asc"});

  var boroughsBH = new Bloodhound({
    name: "Stadtteile",
    datumTokenizer: function (d) {
      return Bloodhound.tokenizers.whitespace(d.name);
    },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    local: boroughSearch,
    limit: 10
  });

  var museumsBH = new Bloodhound({
    name: "Denkmal",
    datumTokenizer: function (d) {
      return Bloodhound.tokenizers.whitespace(d.address);
    },
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    local: museumSearch,
    limit: 10
  });

  boroughsBH.initialize();
  museumsBH.initialize();

  // instantiate the typeahead UI
  $("#searchbox").typeahead({
    minLength: 3,
    highlight: true,
    hint: false
  }, {
    name: "Stadtteile",
    displayKey: "name",
    source: boroughsBH.ttAdapter(),
    templates: {
      header: "<h4 class='typeahead-header'><img src='assets/img/wappen.gif' width='24' height='28'>&nbsp;Stadtteile</h4>"
    }
  }, {
    name: "Denkmal",
    displayKey: "address",
    source: museumsBH.ttAdapter(),
    templates: {
      header: "<h4 class='typeahead-header'><img src='assets/img/denkmal.png' width='24' height='28'>&nbsp;Denkmal</h4>",
      suggestion: Handlebars.compile(["{{address}}<br>&nbsp;<small>{{name}}</small>"].join(""))
    }
  })
  .on("typeahead:selected", function (obj, datum) {
    if (datum.source === "Stadtteile") {
      map.fitBounds(datum.bounds);
    }
    if (datum.source === "Denkmal") {
      if (!map.hasLayer(museumLayer)) {
        map.addLayer(museumLayer);
      }
      map.setView([datum.lat, datum.lng], 18);
      if (map._layers[datum.id]) {
        map._layers[datum.id].fire("click");
      }
    }
    if ($(".navbar-collapse").height() > 50) {
      $(".navbar-collapse").collapse("hide");
    }
  }).on("typeahead:opened", function () {
    $(".navbar-collapse.in").css("max-height", $(document).height() - $(".navbar-header").height());
    $(".navbar-collapse.in").css("height", $(document).height() - $(".navbar-header").height());
  }).on("typeahead:closed", function () {
    $(".navbar-collapse.in").css("max-height", "");
    $(".navbar-collapse.in").css("height", "");
  });
  $(".twitter-typeahead").css("position", "static");
  $(".twitter-typeahead").css("display", "block");
});

// Leaflet patch to make layer control scrollable on touch browsers
var container = $(".leaflet-control-layers")[0];
if (!L.Browser.touch) {
  L.DomEvent
  .disableClickPropagation(container)
  .disableScrollPropagation(container);
} else {
  L.DomEvent.disableClickPropagation(container);
}
