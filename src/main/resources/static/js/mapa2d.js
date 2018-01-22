var projection = d3.geoEquirectangular()
    .scale(height / Math.PI )
    .translate([width / 2, height / 2]);

var path = d3.geoPath()
    .projection(projection);

var graticule = d3.geoGraticule();

g.append("path")
    .datum(graticule)
    .attr("class", "graticule")
    .attr("d", path);

d3.json("../json/world-50m.json", function(error, world) {
    if (error) throw error;

    g.append("path", ".graticule")
        .datum(topojson.feature(world, world.objects.land))
        .attr("class", "land")
        .attr("d", path).attr('fill-opacity', 0.1).attr("fill", "gray");
    
});




