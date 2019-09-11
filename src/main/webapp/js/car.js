////GLOBAL VARIABLES/////
var cars;
var reverse = false;
////FETCH////
function getData() {
    fetch("http://localhost:8080/CA1/api/car/all")
            .then(res => res.json()) //in flow1, just do it
            .then(data => {
                cars = data;
                createTable(cars);
                generateFilterOptions();
            });
}
////SORT METHODS////
function sortByMake() {
    cars.sort((a, b) => (a.make > b.make) ? 1 : -1);
    createTable(cars);
}
function sortById() {
    cars.sort((a, b) => (a.id > b.id) ? 1 : -1);
    createTable(cars);
}
function sortByYear() {
    cars.sort((a, b) => (a.year > b.year) ? 1 : -1);
    createTable(cars);
}
function sortByModel() {
    cars.sort((a, b) => (a.Model > b.Model) ? 1 : -1);
    createTable(cars);
}
function sortByPrice() {
    cars.sort((a, b) => (a.price > b.price) ? 1 : -1);
    createTable(cars);
}
////TABLE FUNCTIONS////
function createTable(data) {
    var checkBox = document.getElementById("myCheck");
    if (checkBox.checked === true) {
        var filteredData = filter();
        var table = "<table>";
        table += "<tr><th><button onclick='sortById()'>Id</button></th><th><button onclick='sortByYear()'>Year</button></th><th><button onclick='sortByMake()'>Make</button></th><th><button onclick='sortByModel()'>Model</button></th><th><button onclick='sortByPrice()'>Price</button></th></tr>";
        for (var i = 0, max = filteredData.length; i < max; i++) {
            table += "<tr><td>" + filteredData[i]["id"] + "</td><td>" + filteredData[i]["year"] + "</td><td>" + filteredData[i]["make"] + "</td><td>" + filteredData[i]["Model"] + "</td><td>" + filteredData[i]["price"] + "</td></tr>"

        }
        table += "</table>";
    } else {

        var table = "<table>";
        table += "<tr><th><button onclick='sortById()'>Id</button></th><th><button onclick='sortByYear()'>Year</button></th><th><button onclick='sortByMake()'>Make</button></th><th><button onclick='sortByModel()'>Model</button></th><th><button onclick='sortByPrice()'>Price</button></th></tr>";
        for (var i = 0, max = data.length; i < max; i++) {
            table += "<tr><td>" + data[i]["id"] + "</td><td>" + data[i]["year"] + "</td><td>" + data[i]["make"] + "</td><td>" + data[i]["Model"] + "</td><td>" + data[i]["price"] + "</td></tr>"

        }
    }
    table += "</table>";
    document.getElementById("table").innerHTML = table;
}

////FILTER FUNCTIONS////
function filter() {
    var price = document.getElementById("filterPrice").value;
    var makeOptions = document.getElementById('makeOptionsSelect');
    var make = makeOptions.options[makeOptions.selectedIndex].value;
    var index = makeOptions.selectedIndex;
    makeOptions.selectedIndex = index;
    //setSelectedValue(index);
    //console.log(price)
    var filteredByMake = [];
    var filteredCars = [];
    for (var i = 0; i < cars.length; i++) {
        if (cars[i]["make"] === make || make === "any") {
            filteredByMake.push(cars[i]);
        }
    }

    for (var i = 0; i < filteredByMake.length; i++) {
        if (filteredByMake[i]["price"] < price || price == 0) {
            filteredCars.push(filteredByMake[i]);
        }
    }
    return filteredCars;
}

function generateFilterOptions() {
    var makeList = [];
    for (var i = 0; i < cars.length; i++) {
        if (makeList[i] === null || !makeList.includes(cars[i]["make"])) {
            makeList.push(cars[i]["make"]);
        }
    }
    var options = "<select id='makeOptionsSelect'>";
    options += "<option value='any'>Any</option>";
    for (var i = 0; i < makeList.length; i++) {
        options += "<option value=" + makeList[i] + ">" + makeList[i] + "</option>";
    }
    options += "</select>";
    document.getElementById("makeOptions").innerHTML = options;
}

function setSelectedValue(index){
    document.getElementById("makeOptionsSelect").selectedIndex = index;
}

