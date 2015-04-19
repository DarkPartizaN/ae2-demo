#version 120

varying vec2 tex_coords;

void main() {
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	tex_coords = gl_MultiTexCoord0.xy;
}