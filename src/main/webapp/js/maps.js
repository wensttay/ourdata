var selected = false;
var map_ref;
var rectangle;
var infoWindow = null;

function validate(event) {
    var maxx = $('input[name=maxx]').val();
    var minx = $('input[name=minx]').val();
    var maxy = $('input[name=maxy]').val();
    var miny = $('input[name=miny]').val();
    
    if(maxx === '' || minx === '' || maxy === '' || miny === '')
        event.preventDefault();
}

//Anima âncoras
$('a').click(function () {
    event.preventDefault();

    $('html, body').animate({
        scrollTop: $($.attr(this, 'href')).offset().top
    }, 700);
});


function initSearchBox() {

    var input = document.getElementById('pac-input');
    var btnSearch = document.getElementById('search-form');
    console.log(input);
    var searchBox = new google.maps.places.SearchBox(input);

    map_ref.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
    map_ref.controls[google.maps.ControlPosition.TOP_RIGHT].push(btnSearch);

    map_ref.addListener('bounds_changed', function () {
        searchBox.setBounds(map_ref.getBounds());
    });

    searchBox.addListener('places_changed', function () {
        var places = searchBox.getPlaces();

        if (places.length === 0) {
            return;
        }

        var bounds = new google.maps.LatLngBounds();

        // alert(places[0].geometry.viewport);

        // places.forEach(function (place) {

        //     if (place.geometry.viewport) {

        //         bounds.union(place.geometry.viewport);
        //     }
        //     else {
        //         bounds.extend(place.geometry.location);
        //     }

        // });

        var viewport = places[0].geometry.viewport;
        var location = places[0].geometry.location;

        map_ref.fitBounds(viewport);

        drawRectangleByViewport(viewport);
    });
}

//desenha o retângulo no mapa de acordo com o viewbox passado por parâmetro.
function drawRectangleByViewport(viewport) {

    var maxx = viewport.getNorthEast().lat();
    var minx = viewport.getNorthEast().lng();
    var maxy = viewport.getSouthWest().lat();
    var miny = viewport.getSouthWest().lng();

    updateHiddenInputs(maxx, minx, maxy, miny);

    // Verifica se já existe um infoWindow, se sim, vai fechar. 
    if (infoWindow !== null)
        infoWindow.close();
    // Instancia um infoWindow
    infoWindow = new google.maps.InfoWindow();

    if (rectangle !== null)
        rectangle.setMap(null);

    drawRectangleByRect(maxx, minx, maxy, miny);

    var contentString = '<b>Bounding Box</b><br>' +
            'North-east corner: ' + maxx + ', ' + minx + '<br>' +
            'South-west corner: ' + maxy + ', ' + miny;

    drawInfoWindow(contentString, viewport.getNorthEast());
}

// function drawRectangleOnClick(event,raio){
//     var location = event.latLng;
//     var maxx = event.latLng.lat();
//     var minx = event.latLng.lng();
//     var maxy = event.latLng.lat();
//     var miny = event.latLng.lng();

//     drawRectangleByRect(maxx,minx,maxy,miny);
// }

function drawRectangleByRect(maxx, minx, maxy, miny) {
    //Define o bounds do retângulo de acordo com os valores passado por parâmetro
    var bounds = {
        north: maxx,
        south: maxy,
        east: minx,
        west: miny
    };

    // strokeColor: '#FF0000',
    // strokeOpacity: 0.8,
    // strokeWeight: 2,
    // fillColor: '#FF0000',
    // fillOpacity: 0.35,

    //Cria um novo retângulo
    rectangle = new google.maps.Rectangle({
        bounds: bounds,
        strokeWeight: 1,
        editable: true,
        draggable: true
    });

    rectangle.setMap(map_ref);

    // Adicionar um listener que é disparado quando o retângulo muda seus bounds.
    rectangle.addListener('bounds_changed', showNewRect);
}

//Mostra o infoWindow assim que o retângulo muda de posição
function showNewRect(event) {

    var ne = rectangle.getBounds().getNorthEast();
    var sw = rectangle.getBounds().getSouthWest();

    var maxx = ne.lat();
    var minx = ne.lng();
    var maxy = sw.lat();
    var miny = sw.lng();

    updateHiddenInputs(maxx, minx, maxy, miny);

    var contentString = '<b>Bounding Box has changed!</b><br>' +
            'New north-east corner: ' + maxx + ', ' + minx + '<br>' +
            'New south-west corner: ' + maxy + ', ' + miny;

    drawInfoWindow(contentString, ne);
}

function drawInfoWindow(content, position) {

    infoWindow.setContent(content);
    infoWindow.setPosition(position);
    infoWindow.open(map_ref);
}

function updateHiddenInputs(maxx, minx, maxy, miny) {
    $('input[name=maxx]').val(maxx);
    $('input[name=minx]').val(minx);
    $('input[name=maxy]').val(maxy);
    $('input[name=miny]').val(miny);
}

//função que constrói o mapa
function initialize() {
    var maps = google.maps;
    google_ref = google;
    // console.log(maps);

    //Instancia uma variavel do tipo Latlng com as coordenadas de cajazeiras
    var myLatlng = new google.maps.LatLng(-6.889797, -38.561197);
    //insancia um objeto json com as configurações do mapa
    var mapOptions = {
        zoom: 5,
        center: {
            lat: -10.699511,
            lng: -52.072589
        },
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        backgroundColor: "lightgrey",
        mapTypeControlOptions: {
            style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
            position: google.maps.ControlPosition.BOTTOM_LEFT
        }

    }
    /*Instancia o mapa passando o objeto DOM que vai printar o mapa do google juntamente com as 
     Configurações que criamos anteriormente.*/
    var map = new google.maps.Map(document.getElementById("map"), mapOptions);

    // map.addListener('click',function(e){
    //     drawRectangleOnClick(e,0.1);
    // });


    map_ref = map;

    initSearchBox();
}



//carrega o script do google maps
function loadScript() {
    var myKey = "AIzaSyDgesfzerpawSKDeYXdMBNRudZMKrVe5zE";
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "https://maps.googleapis.com/maps/api/js?key=" + myKey + "&sensor=false&callback=initialize&libraries=places";
    document.body.appendChild(script);
}

window.onload = loadScript;