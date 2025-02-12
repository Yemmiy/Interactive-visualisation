# Interactive-visualisation
Visulaizing Data Scientist Salaries using funnel chart


Aim of the Project
To visualize and compare data scientist salaries across different employment types using a funnel chart, 
implemented with D3.js.

Objectives
Data Visualization: Create a visually appealing and informative funnel chart that shows salary distribution based on job roles for different employment types.

Interactivity: Implement interactivity features such as tooltips and dropdown selection to enhance user experience and facilitate exploration of data.

Integration with HTML/CSS: Ensure seamless integration of D3.js chart within an HTML page with appropriate styling and layout.

Steps Involved
HTML Setup

Structure: Define the basic HTML structure (<!DOCTYPE html>, <html>, <head>, <body>) including necessary meta tags, title, and linking of D3.js library (d3.v7.min.js).
Styling: Use CSS (<style>) to define the layout, colors, and fonts for the entire page, including the header, footer, and chart container.

Header and Footer

Header: Display project title and include a navigation link (Homepage) for easy navigation.
Footer: Include project-specific information (Funnel Chart, D3339558 Opeyemi Ageshin) to provide context and attribution.
Chart Container (<div class="chart-container">):

SVG Element (<svg id="chart-svg">): Create an SVG element to host the funnel chart dynamically generated by D3.js.
Dropdown for Employment Type Selection: Implement a dropdown (<select id="employment-type">) to allow users to select different employment types (Full Time, Part Time, Contract).

D3.js Script

Data Loading (d3.json()): Load data from Datascientistsalaries.json using D3.js's d3.json() function.
Data Processing and Filtering: Filter data based on the selected employment type (Full Time, Part Time, Contract).
Data Aggregation: Aggregate salary data by job role and sort it in descending order to prepare for visualization.
Chart Rendering (renderChart()):
Dimension and Margin Setup: Define margins and dimensions for the SVG-based funnel chart.
Scales: Set up linear scales (xScale, yScale) for positioning and sizing data elements within the chart.
Chart Elements (<rect>, <text>): Use D3.js to create and update rectangles (<rect>) for each job role, representing salary ranges. Add corresponding salary text (<text>) and job role labels (<text>) within the chart.
Color Scale: Implement a color scale (colorScale) to differentiate job roles visually.
Interactivity (mouseover, mouseout): Add event listeners to provide interactive features such as highlighting (mouseover) and tooltip display (mouseout).
Zoom Functionality: Integrate zoom functionality (d3.zoom()) to allow users to zoom in and out of the funnel chart for detailed inspection.
Tooltip (<div class="tooltip">):

Tooltip Implementation: Create a tooltip using D3.js to display detailed information (job role and salary) upon hovering over each job role's salary range in the funnel chart.
Event Handling and Updates:

Dropdown Change Event: Implement an event listener (d3.select("#employment-type").on("change", ...)) to update the chart dynamically when users select a different employment type from the dropdown.
Conclusion

This project aims to provide an insightful visualization of data scientist salaries across various employment types using a funnel chart. By following these steps and objectives, you can effectively implement and enhance your visualization with D3.js, ensuring both functionality and visual appeal in presenting the salary data.






