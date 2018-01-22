var width = window.innerWidth, height = width/2;
var projection = d3.geoEquirectangular().scale(width / (2*Math.PI) )
var path = d3.geoPath().projection(projection);
//var color = ["black","gray","red","blue","orange","green", "white", "gray", "black"]; // Usado para cores específicas.
var color = d3.schemeCategory20c; // Usando esquema de cores do D3.
var active = d3.select(null);

var scale = d3.scaleLinear();
scale.domain ([0,7000000000]); // Valor máximo é população total.
scale.range([50,Math.pow(width,2)/16]); // Valor mínimo exibido em dados com valor nulo ou 0. Valor máximo equivale a 1/8 da área total.

var transform = d3.zoomTransform(this);

var svg = d3.select("#map").append("svg")
    .attr("width", width)
    .attr("height", height + 100);

var g = svg.append("g");

var div = d3.select("body").append("div")
    .attr("class", "tooltip")
    .style("opacity", 0);

var selected = "";

d3.json("https://tcc-lucas-diogo.herokuapp.com/allCountries", function(error, mapa) {
	render(error,mapa)
});

function render(error,mapa) {
		if (error) throw error;

		mapa.forEach(function(d) {
		    d.cx = projection([d.longitude,d.latitude])[0];
	        d.cy = projection([d.longitude,d.latitude])[1];

	        d.x = d.cx;
	        d.y = d.cy;

			if(d.population != null) {
                d.radius = Math.sqrt(scale(d.population.total)/Math.PI); // Calcula escala baseado no valor total.
			} else {
                d.radius = Math.sqrt(scale(0)/Math.PI); // Exibe valores nulos com tamanho mínimo na escala.
			}
		});

		pies = g.selectAll(".pie")
			.data(mapa)
			.enter()
			.append('g')
			.attr('class','pie')
			.style("cursor", "pointer");

		pies.each(function(d,i){
			var pieG = d3.select(this).attr("cx", d.cx).attr("cy", d.cy);

			var arc = d3.arc()
						.outerRadius(d.radius)
						.innerRadius(0);

			var pie = d3.pie()
						.sort(null)
						.value(function(d) { return d; });

			var data;
			if(d.population == null) {
				data = [1,0,0,0,0,0,0,0,0,0];
				data.total = 0
			} else {
				data = [0,d.population.from0to9, d.population.from10to19, d.population.from20to29, d.population.from30to39,
				d.population.from40to49, d.population.from50to59, d.population.from60to69, d.population.from70to79, d.population.from80up];
				data.total = d.population.total;
			}

			var slice = pieG.selectAll(".arc").data(pie(data)).enter()
			.append("g").attr("class", "arc")

			slice.append("path").attr('d',arc).attr("fill",function(d,i){
				return color[i+1];
			});

			slice.append("text")
			.style("opacity",0)
			.attr("class", "porcentagem")
			.attr("transform", function(d) {
                var _d = arc.centroid(d);
                _d[0] *= 2.2;	//multiply by a constant factor
                _d[1] *= 2.2;	//multiply by a constant factor
                return "translate(" + _d + ")";
            }).attr("text-anchor", "middle").text(function(d, i) {
//                if( (i+1) > 9 ) return "";
                if(data.total == 0) return "";

                if(i == 0) return "";

                var porcentagem = (data[i] / data.total) * 100;

                return parseFloat(porcentagem.toFixed(2)) + "%";
            });
		});
		var simulation = d3.forceSimulation()
				.force('x', d3.forceX().x( function(d) { return d.cx} ))
				.force('y', d3.forceY().y( function(d) { return d.cy} ))
				.force("collide", d3.forceCollide().strength(1).radius(function(d){return d.radius + 2; }).iterations(2))
				.on("tick", function(d){
					pies.attr("transform", function(d) {return "translate(" + d.x + ", " + d.y  + ")";});

                    pies.attr("cx", function(d) { return d.cx; }).attr("cy", function(d) { return d.cy; });

				})
                .nodes(mapa)

        pies.on("mouseover", function(d) {
            div.transition()
                .duration(200)
                .style("opacity", .9);
            div.html("<span>"+d.commonName+"</span>")
                .style("left", (d3.event.pageX) + "px")
                .style("top", (d3.event.pageY - 28) + "px");
        })
        .on("mouseout", function(d) {
            div.transition()
	            .duration(500)
	            .style("opacity", 0);
        })
        .on("click", function(d) {
            if(selected!=this){
            	selected = this;
				d3.select(this.parentNode).selectAll(".pie").style("opacity", .2);
		        d3.select(this).style("opacity", 1);
		        d3.select(this).selectAll(".arc > .porcentagem").style("opacity", 1);
	            zoomscale=0.8*width/(d.radius*4);
	            zoomx=(d.x*(-zoomscale)+ width/2);
	            zoomy=(d.y*(-zoomscale)+ width/4);
	            svg.transition().duration(500).call(zoom.transform, d3.zoomIdentity.translate(zoomx, zoomy).scale(zoomscale));
	        } else {
	         	selected = "";
				d3.select(this.parentNode).selectAll(".pie").style("opacity", 1);
				d3.select(this).selectAll(".arc > .porcentagem").style("opacity", 0);
	            svg.transition().duration(500).call(zoom.transform, d3.zoomIdentity);
	        }
        });
}

var zoom = d3.zoom()
  .on("zoom",function() {
     g.attr("transform",d3.event.transform);
});

svg.call(zoom)

function clicked(d) {
	g.transition()
      .delay(100)
      .duration(1000)
      .attr("transform", transform);
}

function reset() {
	  svg.transition()
      .delay(100)
      .duration(1000)
      .call(zoom.transform, d3.zoomIdentity);
}