#version 120

varying vec2 v_texcoords;

void main() {
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	v_texcoords = gl_MultiTexCoord0.xy;
}