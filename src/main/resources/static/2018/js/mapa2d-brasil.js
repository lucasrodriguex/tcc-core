var projection = d3.geoEquirectangular().center([-52, -15])
    .scale(600)
    .translate([width / 2, height / 2]);

var path = d3.geoPath()
    .projection(projection);

d3.json("json/world-50m.json", function(error, world) {
    if (error) throw error;

    g.insert("g",":first-child").append("path", ".graticule")
        .datum(topojson.feature(world, world.objects.land))
        .attr("class", "land")
        .attr("d", path).attr('fill-opacity', 0.1).attr("fill", "gray");

});

d3.json("../json/br-states.json", function(error, brasil) {
    if (error) throw error;

    g.insert("g",":first-child").append("path", ".graticule")
    .datum(topojson.mesh(brasil, brasil.objects.estados))
    .attr("class", "boundary-br")
    .attr("d", path)
});


