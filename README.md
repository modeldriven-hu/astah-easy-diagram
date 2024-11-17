# Astah Easy Diagram PlugIn

A plugin to make diagram editing easier and more fun. 

# Screenshot

![image](https://github.com/user-attachments/assets/63d21263-cde8-4736-83cb-7e228acc8bf3)

# Supported features

## Alignment

- Display and change position and size of a component based on numerical input
- Snap to pixel: set coordinates and size so that it aligns to pixels, removing the fraction parts by rounding up)
- Snap diagram to pixel: snap every element on the diagram to pixel
- Straighten line: keep minimal amount of points
- Save and restore position: save the positions of connected edges and ports, really useful for resizing/movement of a block.
- Horizontal and vertical alignment: unlike the built in alignment it also works with pins
- Reset item flow: match item flow line with the corresponding connection

## Field
- Create field for an block with name and type. Can work also with multiple blocks.

## Autosave
- Regularly save the model if there was a modification

## Activity
- Unmarshall: Create a new action in an activity diagram and all the fields of the selected block or value type as pins
- Split object flow: splits an object flow into two parts

## Misch
- Show model statistics: number of model elements and number of diagrams
