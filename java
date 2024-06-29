// Declare svg variable outside the d3.json().then() function
let svg;

// Load JSON data
d3.json("Datascientistsalaries.json").then(function(data) {
    // Initial render function
    function renderChart(employmentType) {
        // Filter data based on employment type
        const filteredData = data.filter(d => d["Employment Type"] === employmentType);

        // Aggregate data by job role and sort in descending order
        const aggregatedData = d3.rollup(filteredData, v => d3.sum(v, d => d["Sum of Salary"]), d => d["Job Role"]);
        const sortedData = Array.from(aggregatedData)
                                .sort((a, b) => b[1] - a[1]);

        // Clear existing chart
        if(svg) svg.selectAll("*").remove();

        // Set chart dimensions and margins
        // Define the margins for the chart
        const margin = { top: 20, right: 20, bottom: 60, left: 80 };
        // Calculate the width of the chart area by subtracting left and right margins from the total width
        const width = 800 - margin.left - margin.right;
        // Calculate the height of the chart area by subtracting top and bottom margins from the total height
        const height = 500 - margin.top - margin.bottom;

        // Create SVG element for the chart, setting width and height attributes
        svg = d3.select("#chart-svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            // Append a 'g' element to apply margins to the inner content of the SVG
            .append("g")
            // Translate the 'g' element to accommodate the left and top margins
            .attr("transform", `translate(${margin.left}, ${margin.top})`);

        // Define color scale
        const colorScale = d3.scaleOrdinal(d3.schemeCategory10);

        // Define the funnel shape
        // Define the width of the top of the funnel
        const funnelWidth = width * 0.7;
        // Define the width of the bottom of the funnel
        const bottomWidth = width * 0.2;
        // Create a linear scale for mapping data points to vertical positions within the funnel
        const yScale = d3.scaleLinear()
            // Set the domain of the scale to be from 0 to the length of the sorted data minus 1
            .domain([0, sortedData.length - 1])
            // Set the range of the scale to map to the available vertical space, adjusting for margin
            .range([10, height - 10]);

        // Create a linear scale for mapping data values to horizontal positions within the funnel
        const xScale = d3.scaleLinear()
            // Set the domain of the scale to be from 0 to the maximum data value in the sorted data
            .domain([0, d3.max(sortedData, d => d[1])])
            // Set the range of the scale to map to the available horizontal space, adjusted for the funnel shape
            .range([bottomWidth / 2, funnelWidth]);


        // Create rectangles for each data point and bind data
        // Select all elements with class "bar" within the SVG and bind data
        svg.selectAll(".bar")
            // Bind the sorted data to the selection
            .data(sortedData)
            // For each data point that doesn't have a corresponding SVG element, append a new "rect" element
            .enter().append("rect")
            // Set the class attribute of the newly appended "rect" elements to "bar"
            .attr("class", "bar")
            // Set the x-coordinate of the rectangles (centering them horizontally)
            .attr("x", d => (width - xScale(d[1])) / 2)
            // Set the y-coordinate of the rectangles (adjusting for spacing between them)
            .attr("y", (d, i) => yScale(i) + i * 3.6)
            // Set the width of the rectangles based on the scaled salary values
            .attr("width", d => xScale(d[1]))
            // Set the height of the rectangles based on the yScale values
            .attr("height", (d, i) => yScale(i + 1) - yScale(i))
            // Set the fill color of the rectangles using a color scale
            .attr("fill", (d, i) => colorScale(i))
            // Set initial opacity of all rectangles
            .attr("opacity", 1)
            // Event listener for mouseover event to show tooltip and highlight the rectangle
            .on("mouseover", function(event, d) {
                // Reduce opacity of all rectangles except the one being hovered over
                svg.selectAll(".bar")
                    .filter(bar => bar !== d)
                    .attr("opacity", 0.5);

                // Highlight the rectangle on mouseover
                d3.select(this)
                    .classed("highlighted", true)
                    .transition().duration(200)
                    .attr("width", d => xScale(d[1]) + 50) // Increase width on hover
                    .attr("filter", "drop-shadow(2px 2px 4px rgba(0,0,0,0.4))"); // Add shadow effect

                // Show tooltip
                tooltip.style("display", "block")
                    .html(`<strong>${d[0]}</strong><br>Salary: ${d3.format(",")(d[1])}`)
                    .style("left", (event.pageX + 10) + "px")
                    .style("top", (event.pageY - 10) + "px");
            })
            // Event listener for mouseout event to remove highlighting and hide the tooltip
            .on("mouseout", function() {
                // Restore opacity of all rectangles
                svg.selectAll(".bar")
                    .attr("opacity", 1);

                // Remove highlighting on mouseout
                d3.select(this)
                    .classed("highlighted", false)
                    .transition().duration(200)
                    .attr("width", d => xScale(d[1]) - 4) // Reset width on mouseout
                    .attr("filter", null); // Remove shadow effect

                // Hide tooltip
                tooltip.style("display", "none");
            });

        // Add salary amount text on each rectangle
        // Select all elements with class "salary-text" within the SVG and bind data
        svg.selectAll(".salary-text")
            .data(sortedData)
            // For each data point that doesn't have a corresponding SVG element, append a new "text" element
            .enter().append("text")
            // Set the class attribute of the newly appended "text" elements to "salary-text"
            .attr("class", "salary-text")
            // Set the x-coordinate of the text elements
            .attr("x", (d, i) => (width - xScale(d[1])) / 2 + xScale(d[1]) / 2)
            // Set the y-coordinate of the text elements
            .attr("y", (d, i) => yScale(i) + (yScale(i + 1) - yScale(i)) / 2)
            // Set the text content of the elements using the salary data, formatted with commas
            .text(d => d3.format(",")(d[1]))
            // Set the fill color of the text elements to black
            .attr("fill", "#000")
            // Set the horizontal alignment of the text to the middle
            .attr("text-anchor", "middle")
            // Set the vertical alignment of the text to the middle
            .attr("alignment-baseline", "middle")
            // Set the font weight of the text elements to bold
            .style("font-weight", "bold");

        // Add job role labels
        // Select all elements with class "job-role" within the SVG and bind data
        svg.selectAll(".job-role")
            // Bind the sorted data to the selection
            .data(sortedData)
            // For each data point that doesn't have a corresponding SVG element, append a new "text" element
            .enter().append("text")
            // Set the class attribute of the newly appended "text" elements to "job-role"
            .attr("class", "job-role")
            // Set the x-coordinate of the text elements (moving it further left)
            .attr("x", -margin.left + 145)
            // Set the y-coordinate of the text elements (centering it vertically)
            .attr("y", (d, i) => yScale(i) + (yScale(i + 1) - yScale(i)) / 2)
            // Set the horizontal offset of the text elements
            .attr("dx", "-0.19em")
            // Set the vertical offset of the text elements
            .attr("dy", "0.35em")
            // Set the horizontal alignment of the text to the end (right)
            .attr("text-anchor", "end")
            // Set the text content of the elements to uppercase job role names
            .text(d => d[0].toUpperCase())
            // Set the fill color of the text elements to black
            .attr("fill", "#000")
            // Set the font weight of the text elements to bold
            .style("font-weight", "bold");

        // Add zoom functionality
        const zoom = d3.zoom()
            .scaleExtent([1, 10])
            .on("zoom", function(event) {
                svg.attr("transform", event.transform);
            });

        svg.call(zoom);
    }

    // Initial render with default employment type
    renderChart("Full Time");

    // Dropdown change event listener
    d3.select("#employment-type").on("change", function() {
        const employmentType = d3.select(this).property("value");
        renderChart(employmentType); // Render chart based on selected employment type
    });

    // Tooltip
    const tooltip = d3.select(".chart-container").append("div")
        .attr("class", "tooltip");
});
